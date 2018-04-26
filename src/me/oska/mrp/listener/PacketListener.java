package me.oska.mrp.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import me.oska.mrp.MRP;
import me.oska.mrp.io.FileReader;
import me.oska.mrp.man.DataManager;
import me.oska.mrp.rfl.ReflectionUtil;

public class PacketListener
{
	private static FileConfiguration fcfg = FileReader.getFileConfig("config");
	
	public static void sendPacket(Player p, String pack_url, String pack_hash)
	{
		try
		{
			Object packet = ReflectionUtil.getNMSClass("PacketPlayOutResourcePackSend")
					.getConstructor(new Class[] { String.class, String.class }).newInstance(new Object[] { pack_url, pack_hash });
			Method sendPacket = ReflectionUtil.getNMSClass("PlayerConnection").getMethod("sendPacket", ReflectionUtil.getNMSClass("Packet"));
			sendPacket.invoke(ReflectionUtil.getConnection(p), packet);
			
		}
		catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e)
		{
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static PacketHandler packet = new PacketHandler()
		{
			@Override
			public void onReceive(ReceivedPacket packet)
			{
				if (packet.getPacketName().equals("PacketPlayInResourcePackStatus"))
				{
					if (packet.hasPlayer())
					{
						String result = packet.getPacketValue("status").toString();
						if (result.equalsIgnoreCase("ACCEPTED"))
						{
							Bukkit.getScheduler().runTask(MRP.getInstance(), new Runnable()
								{
									@Override
									public void run()
									{
										if (DataManager.getPendingMap().containsKey(packet.getPlayer()))
										{
											DataManager.getPendingMap().get(packet.getPlayer()).cancel();
											DataManager.getPendingMap().remove(packet.getPlayer());
										}
									}
								});
						}
						if (result.equalsIgnoreCase("FAILED_DOWNLOAD"))
						{
							Bukkit.getScheduler().runTask(MRP.getInstance(), new Runnable()
								{
									@Override
									public void run() 
									{
										if (fcfg.getBoolean("download.failed_download.kick"))
										{
											packet.getPlayer().kickPlayer(fcfg.getString("download.failed_download.message").replaceAll("&", "§"));
										}
										if (DataManager.getPendingMap().containsKey(packet.getPlayer()))
										{
											DataManager.getPendingMap().get(packet.getPlayer()).cancel();
											DataManager.getPendingMap().remove(packet.getPlayer());
										}
									}
								});
						}
						if (result.equalsIgnoreCase("DECLINED"))
						{
							Bukkit.getScheduler().runTask(MRP.getInstance(), new Runnable()
							{
								public void run()
								{
									if (fcfg.getBoolean("download.declined.kick"))
									{
										packet.getPlayer().kickPlayer(fcfg.getString("download.declined.message").replaceAll("&", "§"));
									}
									if (DataManager.getPendingMap().containsKey(packet.getPlayer()))
									{
										DataManager.getPendingMap().get(packet.getPlayer()).cancel();
										DataManager.getPendingMap().remove(packet.getPlayer());
									}
								}
							});
						}
					}
				}
			}

			@Override
			public void onSend(SentPacket packet) { }
		};
	
	public static void loadPacketHandler()
	{
		PacketListenerAPI.addPacketHandler(packet);
	}
	
	public static void unloadPacketHandler()
	{
		PacketListenerAPI.removePacketHandler(packet);
	}
	
}
