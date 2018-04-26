package me.oska.mrp.rfl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtil {

	public static Class<?> getNMSClass(String classStr) {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String name = "net.minecraft.server." + version + classStr;
		Class<?> nmsClass = null;
		try {
			nmsClass = Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return nmsClass;
	}

	public static Object getConnection(Player player) {
		Method getHandle = null;
		Object nmsPlayer = null;
		Field field = null;
		Object con = null;
		try {
			getHandle = player.getClass().getMethod("getHandle");
			nmsPlayer = getHandle.invoke(player);
			field = nmsPlayer.getClass().getField("playerConnection");
			con = field.get(nmsPlayer);
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return con;
	}
}
