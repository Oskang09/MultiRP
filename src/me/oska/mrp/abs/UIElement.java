package me.oska.mrp.abs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.oska.mrp.io.FileReader;

public class UIElement
{
	private int slot;
	private ItemStack item;
	private List<UIAction> uia;
	
	public UIElement(int slot, ItemStack item, UIAction uia)
	{
		this.slot = slot;
		this.item = item;
		this.uia = new ArrayList<UIAction>();
		if (uia != null)
		{
			this.uia.add(uia);
		}
	}
	public UIElement(int slot, ItemStack item, List<UIAction> uia)
	{
		this.slot = slot;
		this.item = item;
		this.uia = uia;
	}
	public UIElement(int slot, ItemStack item)
	{
		this.slot = slot;
		this.item = item;
		this.uia = new ArrayList<UIAction>();
	}
	
	public int getSlot()
	{
		return this.slot;
	}
	public ItemStack getItem()
	{
		return this.item;
	}
	
	public void Action(ClickType ct, InventoryClickEvent event)
	{
		if (uia.size() > 0)
		{
			uia.forEach(x -> x.RunAction(ct, event));
		}
	}
	
	@SuppressWarnings("deprecation")
	public static UIElement previousElement(int slot, UIAction uia)
	{
		FileConfiguration fcfg = FileReader.getFileConfig("config");
		ItemStack item = new ItemStack(Material.getMaterial(fcfg.getInt("gui.previous_page.id")), 1, (byte)fcfg.getInt("gui.previous_page.data_value"));
		ItemMeta im = item.getItemMeta();;
        
		im.setDisplayName(fcfg.getString("gui.previous_page.name").replaceAll("&", "§"));
        
		List<String> lores = new ArrayList<String>();
		for (String lore : fcfg.getStringList("gui.previous_page.lore"))
		{
			lores.add(lore.replaceAll("&", "§"));
		}
		
		item.setItemMeta(im);
		return new UIElement(slot, item, uia);
	}
	@SuppressWarnings("deprecation")
	public static UIElement pageElement(int slot, int page, UIAction uia)
	{
		FileConfiguration fcfg = FileReader.getFileConfig("config");
		ItemStack item = new ItemStack(Material.getMaterial(fcfg.getInt("gui.paging.id")), 1, (byte)fcfg.getInt("gui.paging.data_value"));
		ItemMeta im = item.getItemMeta();;
        
		im.setDisplayName(fcfg.getString("gui.paging.name").replaceAll("&", "§"));
        
		List<String> lores = new ArrayList<String>();
		for (String lore : fcfg.getStringList("gui.paging.lore"))
		{
			lores.add(lore.replaceAll("&", "§").replaceAll("%page", String.valueOf(page)));
		}
		
		item.setItemMeta(im);
		return new UIElement(slot, item, uia);
	}
	@SuppressWarnings("deprecation")
	public static UIElement nextElement(int slot, UIAction uia)
	{
		FileConfiguration fcfg = FileReader.getFileConfig("config");
		ItemStack item = new ItemStack(Material.getMaterial(fcfg.getInt("gui.next_page.id")), 1, (byte)fcfg.getInt("gui.next_page.data_value"));
		ItemMeta im = item.getItemMeta();;
        
		im.setDisplayName(fcfg.getString("gui.next_page.name").replaceAll("&", "§"));
        
		List<String> lores = new ArrayList<String>();
		for (String lore : fcfg.getStringList("gui.next_page.lore"))
		{
			lores.add(lore.replaceAll("&", "§"));
		}
		
		item.setItemMeta(im);
		return new UIElement(slot, item, uia);
	}
}
