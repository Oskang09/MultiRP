package me.oska.mrp.abs;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ClickAction
{
	public void applyAction(InventoryClickEvent event);
}
