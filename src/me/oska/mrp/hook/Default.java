package me.oska.mrp.hook;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.oska.mrp.MRP;
import me.oska.mrp.abs.DynamicGUI;
import me.oska.mrp.io.FileReader;
import me.oska.mrp.listener.PacketListener;
import me.oska.mrp.man.DataManager;
import me.oska.mrp.rfl.UIComponent;

public class Default implements Listener
{
	@EventHandler
	public void onPL(PlayerJoinEvent e)
	{
		checkLogin(e.getPlayer());
	}
	
	public static void checkLogin(Player player)
	{
		Bukkit.getScheduler().runTask(MRP.getInstance(), new Runnable()
		{
			@SuppressWarnings("deprecation")
			@Override
			public void run()
			{
				FileConfiguration fcfg = FileReader.getFileConfig("config");
				
				if (fcfg.getBoolean("login.use_force_timer"))
				{
					DataManager.getPendingMap().put(player, Bukkit.getScheduler().runTaskLater(MRP.getInstance(), 
							new BukkitRunnable()
							{
								@Override
								public void run()
								{
									player.kickPlayer(fcfg.getString("login.timer.message").replaceAll("&", "§"));
									DataManager.getPendingMap().remove(player);
								}
							} , 20 *fcfg.getInt("login.timer.pending_time")));
				}
				
				if (fcfg.getBoolean("login.use_default"))
				{
					PacketListener.sendPacket(player, fcfg.getString("login.default.default_url"), fcfg.getString("login.default.default_hash"));
				}
				else if (fcfg.getBoolean("login.use_gui"))
				{
					DataManager.getChooser().add(player);
					UIComponent.createGUI(player, 1, fcfg.getBoolean("login.gui.kick_when_close"), new DynamicGUI(player, fcfg.getString("gui.gui_name"), 6));
				}
			}
		});
	}
}
