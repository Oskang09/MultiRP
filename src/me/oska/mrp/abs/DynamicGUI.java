package me.oska.mrp.abs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import me.oska.mrp.MRP;


public class DynamicGUI implements Listener
{
	private Inventory inv;
	private Player player;
	private boolean isOpen;
	private UIElement[] ui;
	private int page;
	private CloseAction ca;

	public DynamicGUI(Player player, String title, int height)
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, MRP.getInstance());
		this.player = player;
		this.inv = Bukkit.createInventory(null, height * 9, title);
		this.isOpen = false;
		this.ui = new UIElement[height * 9];
		this.page = 1;
	}
	
	public void reset()
	{
		this.inv.clear();
		this.ui = new UIElement[inv.getSize()];
	}
	
	public void setCloseAction(CloseAction cat)
	{
		ca = cat;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public void setPage(int page)
	{
		this.page = page;
	}
	
	public int getPage()
	{
		return this.page;
	}
	
	public void addElement(UIElement uie)
	{
		this.inv.setItem(uie.getSlot(), uie.getItem());
		ui[uie.getSlot()] = uie;
	}

	public void removeElement(int slot)
	{
		ui[slot] = null;
	}

	public void open()
	{
		if (!isOpen)
		{
			player.openInventory(inv);
			isOpen = true;
		}
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent event)
	{
		if (event.getPlayer() == player && isOpen)
		{
			isOpen = false;
			if (ca != null)
			{
				ca.applyAction(event);
			}
			HandlerList.unregisterAll(this);
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent event)
	{
		if (event.getWhoClicked() == player)
		if (event.getClickedInventory() != null && event.getClickedInventory().equals(inv) && isOpen)
		{
			event.setCancelled(true);
			if (ui[event.getSlot()] != null)
			{
				ui[event.getSlot()].Action(event.getClick(), event);
			}	
		}
	}
}
