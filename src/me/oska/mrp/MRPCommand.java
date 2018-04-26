package me.oska.mrp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.oska.mrp.abs.DynamicGUI;
import me.oska.mrp.io.FileReader;
import me.oska.mrp.rfl.UIComponent;

public class MRPCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3)
	{
		if (arg0 instanceof Player)
		{
			if (arg2.equals("mrp"))
			{
				Player p = (Player)arg0;
				FileConfiguration fcfg = FileReader.getFileConfig("config");
				if (p.hasPermission(fcfg.getString("command.permission")))
				{
					UIComponent.createGUI(p, 1, false, new DynamicGUI((Player) arg0, fcfg.getString("gui.gui_name"), 6));
				}
			}
		}
		return true;
	}

}
