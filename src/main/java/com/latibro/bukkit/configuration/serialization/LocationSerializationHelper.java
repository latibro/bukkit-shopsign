package com.latibro.bukkit.configuration.serialization;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializationHelper extends AbstractSerializationHelper<Location> {

	private static final LocationSerializationHelper INSTANCE = new LocationSerializationHelper();

	private LocationSerializationHelper() {
		super();
	}

	public static LocationSerializationHelper getInstance() {
		return INSTANCE;
	}

	/* -------------------------- */

	@Override
	public Map<String, Object> serialize(Location location) {
		if (location == null) {
			return null;
		}

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		map.put("world", location.getWorld().getName());

		map.put("x", location.getX());
		map.put("y", location.getY());
		map.put("z", location.getZ());

		if (location.getYaw() != 0) {
			map.put("yaw", location.getYaw());
		}

		if (location.getPitch() != 0) {
			map.put("pitch", location.getPitch());
		}

		return map;
	}

	// TODO maybe some null check on params before Location is created
	@Override
	public Location deserialize(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		World world = Bukkit.getWorld((String) map.get("world"));

		Double x = toDouble(map.get("x"));
		Double y = toDouble(map.get("y"));
		Double z = toDouble(map.get("z"));

		Float yaw = toFloat(map.get("yaw"));
		if (yaw == null) {
			yaw = 0.0F;
		}

		Float pitch = toFloat(map.get("pitch"));
		if (pitch == null) {
			pitch = 0.0F;
		}

		Location location = new Location(world, x, y, z, yaw, pitch);
		return location;
	}

}
