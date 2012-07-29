package com.latibro.bukkit.configuration.serialization;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldSerializationHelper extends AbstractSerializationHelper<World> {

	private static final WorldSerializationHelper INSTANCE = new WorldSerializationHelper();

	private WorldSerializationHelper() {
		super();
	}

	public static WorldSerializationHelper getInstance() {
		return INSTANCE;
	}

	/* -------------------------- */

	@Override
	public Map<String, Object> serialize(World world) {
		if (world == null) {
			return null;
		}

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		map.put(world.getName(), null);

		return map;
	}

	// TODO maybe some null check on params before Location is created
	@Override
	public World deserialize(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		String name = map.keySet().iterator().next();

		World world = Bukkit.getWorld(name);

		return world;
	}

}
