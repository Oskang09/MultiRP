package me.oska.mrp.hook;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.xephi.authme.events.LoginEvent;

public class AuthMe implements Listener
{
	@EventHandler
	public void onPL(LoginEvent le)
	{
		Default.checkLogin(le.getPlayer());
	}
}
