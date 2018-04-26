package me.oska.mrp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.oska.mrp.hook.AuthMe;
import me.oska.mrp.hook.Default;
import me.oska.mrp.io.FileReader;
import me.oska.mrp.listener.PacketListener;
import me.oska.mrp.man.DataManager;

public class MRP extends JavaPlugin
{
	private static MRP main;
	public MRP()
	{
		main = this;
	}
	@Override
	public void onEnable()
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("PacketListenerApi") != null)
		{
			PacketListener.loadPacketHandler();
			FileConfiguration fcfg = FileReader.getFileConfig("config");
			
			Listener ls;
			switch (fcfg.getString("login.event"))
			{
				case "authme":
					ls = new AuthMe();
					break;
				default:
					ls = new Default();
					break;
			}
			Bukkit.getServer().getPluginManager().registerEvents(ls, this);
			
			if (fcfg.getBoolean("command.use"))
			{
				Bukkit.getServer().getPluginCommand("mrp").setExecutor(new MRPCommand());
			}
			
			FileReader.runFileInit("config");
			FileReader.runFileInit("resource_pack");
			
			DataManager.ReloadDataManager();
		}
		else
		{
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable()
	{
		PacketListener.unloadPacketHandler();
		HandlerList.unregisterAll(this);
	}
	
	public static MRP getInstance()
	{
		return main;
	}
}
