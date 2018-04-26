package me.oska.mrp.man;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class DataManager
{
	private static HashMap<Player, BukkitTask> pendings;
	public static HashMap<Player, BukkitTask> getPendingMap()
	{
		return pendings;
	}
	
	private static List<Player> choosing;
	public static List<Player> getChooser()
	{
		return choosing;
	}
	
	public static void ReloadDataManager()
	{
		pendings = new HashMap<>();
		choosing = new ArrayList<>();
	}
}
