package me.oska.mrp.rfl;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.oska.mrp.abs.ClickAction;
import me.oska.mrp.abs.CloseAction;
import me.oska.mrp.abs.DynamicGUI;
import me.oska.mrp.abs.UIAction;
import me.oska.mrp.abs.UIElement;
import me.oska.mrp.io.FileReader;
import me.oska.mrp.listener.PacketListener;
import me.oska.mrp.man.DataManager;

public class UIComponent
{
	public static void createGUI(Player p, int page, boolean track_close, final DynamicGUI inv)
	{
		FileConfiguration fcfg = FileReader.getFileConfig("resource_pack");
		inv.reset();
		inv.setPage(page);
		String[] lists = fcfg.getConfigurationSection("mrp.list_rp").getKeys(false).toArray(new String[] {});
		int slotcount = 0;
		
		boolean hasNext = false;
		boolean hasPrevious = false;
		
		if (lists.length > 45)
		{
			hasNext = true;
		}
		if (inv.getPage() > 1)
		{
			hasPrevious = true;
		}
		
		for (int i = ( inv.getPage() - 1 ) * 45 ; i < lists.length && i < inv.getPage() * 45; i++)
		{
			final String url = fcfg.getString("mrp.list_rp." + lists[i] + ".url");
			final String hash = fcfg.getString("mrp.list_rp." + lists[i] + ".hash");

			inv.addElement(new UIElement(slotcount, getRPDisplay(lists[i]),
					new UIAction(ClickType.LEFT, new ClickAction()
					{
						@Override
						public void applyAction(InventoryClickEvent event)
						{
							PacketListener.sendPacket( (Player) event.getWhoClicked(), url, hash);
							if (FileReader.getFileConfig("config").getBoolean("login.use_gui"))
							{
								DataManager.getChooser().remove((Player) event.getWhoClicked());
							}
						}
					})));
			slotcount++;
		}
		
		if (hasNext)
		{
		    inv.addElement(UIElement.nextElement(53, new UIAction(ClickType.LEFT, new ClickAction()
		    		{
						@Override
						public void applyAction(InventoryClickEvent event) 
						{
							UIComponent.createGUI(p, page + 1, track_close, inv);
						}
		    		})));
		}
		
		if (hasPrevious)
		{
		    inv.addElement(UIElement.nextElement(45, new UIAction(ClickType.LEFT, new ClickAction()
    		{
				@Override
				public void applyAction(InventoryClickEvent event) 
				{
					UIComponent.createGUI(p, page - 1, track_close, inv);
				}
    		})));
		}
		
		if (track_close)
		{
			inv.setCloseAction(new CloseAction()
					{
						@Override
						public void applyAction(InventoryCloseEvent event)
						{
							FileConfiguration ffg = FileReader.getFileConfig("config");
							if (DataManager.getChooser().contains(event.getPlayer()))
							{
								((Player) event.getPlayer()).kickPlayer(ffg.getString("login.gui.message").replaceAll("&", "§"));
								DataManager.getChooser().remove(event.getPlayer());
							}
						}
					});
		}
		
	    inv.addElement(UIElement.pageElement(49, page, null));
		inv.open();
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getRPDisplay(String id)
	{
		FileConfiguration fcfg = FileReader.getFileConfig("resource_pack");
		ItemStack item = new ItemStack(fcfg.getInt("mrp.list_rp." + id + ".id"), 1, (byte) fcfg.getInt("mrp.list_rp." + id + ".id"));
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(fcfg.getString("mrp.list_rp." + id + ".name").replaceAll("&", "§"));
		List<String> lores = new ArrayList<String>();
		for (String str : fcfg.getStringList("mrp_list_rp." + id + ".lore"))
		{
			lores.add(str.replaceAll("&", "§"));
		}
		im.setLore(lores);
		
		item.setItemMeta(im);
		return item;
	}
}
