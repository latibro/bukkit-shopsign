package com.latibro.bukkit;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.util.Vector;

import com.latibro.util.Converter;


public class Location extends org.bukkit.Location implements Comparable<Location>, Cloneable, org.bukkit.configuration.serialization.ConfigurationSerializable {
	
	public Location(World world, double x, double y, double z, float yaw, float pitch) {
		super(world, x, y, z, yaw, pitch);
	}

	public Location(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	private Location(org.bukkit.Location location) {
		super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	/* Methods */
	
	public Location getBlockLocation() {
		return convertInstance(getBlock().getLocation());
	}
	
	/* Wrappers */

	public static Location convertInstance(org.bukkit.Location location) {
		if (location instanceof Location) {
			return (Location) location;
		}
		return new Location(location);
	}
	
	@Override
	public org.bukkit.Location add(double x, double y, double z) {
		return convertInstance(super.add(x, y, z));
	}

	@Override
	public org.bukkit.Location add(org.bukkit.Location vec) {
		return convertInstance(super.add(vec));
	}

	@Override
	public org.bukkit.Location add(Vector vec) {
		return convertInstance(super.add(vec));
	}

	@Override
	public org.bukkit.Location clone() {
		return convertInstance(super.clone());
	}

	@Override
	public org.bukkit.Location multiply(double m) {
		return convertInstance(super.multiply(m));
	}

	@Override
	public org.bukkit.Location subtract(double x, double y, double z) {
		return convertInstance(super.subtract(x, y, z));
	}

	@Override
	public org.bukkit.Location subtract(org.bukkit.Location vec) {
		return convertInstance(super.subtract(vec));
	}

	@Override
	public org.bukkit.Location subtract(Vector vec) {
		return convertInstance(super.subtract(vec));
	}

	@Override
	public org.bukkit.Location zero() {
		return convertInstance(super.zero());
	}

	/* Serialization */
	
	static {
		 ConfigurationSerialization.registerClass(Location.class);
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		map.put("world", getWorld().getName());

		map.put("x", getX());
		map.put("y", getY());
		map.put("z", getZ());

		if (getYaw() != 0) {
			map.put("yaw", this.getYaw());
		}

		if (getPitch() != 0) {
			map.put("pitch", getPitch());
		}

		return map;
	}
	
	public static Location deserialize(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		World world = Bukkit.getWorld((String) map.get("world"));

		Double x = Converter.toDouble(map.get("x"));
		Double y = Converter.toDouble(map.get("y"));
		Double z = Converter.toDouble(map.get("z"));

		Float yaw = Converter.toFloat(map.get("yaw"));
		if (yaw == null) {
			yaw = 0.0F;
		}

		Float pitch = Converter.toFloat(map.get("pitch"));
		if (pitch == null) {
			pitch = 0.0F;
		}

		Location location = new Location(world, x, y, z, yaw, pitch);
		return location;
	}

	/* Comparable */
	
	public int compareTo(Location location) {
		return hashCode() - location.hashCode();
	}

}
