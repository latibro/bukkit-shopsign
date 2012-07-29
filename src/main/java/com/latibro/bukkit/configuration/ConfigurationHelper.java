package com.latibro.bukkit.configuration;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import com.latibro.bukkit.configuration.serialization.LocationSerializationHelper;


public class ConfigurationHelper {

	private final ConfigurationSection config;
	
	public ConfigurationHelper(ConfigurationSection config) {
		this.config = config;
	}
	
	public ConfigurationSection getConfigurationSection() {
		return config;
	}
	
	public Location getLocation(String path) {
		return LocationSerializationHelper.getInstance().deserialize(config.getValues(true));
	}
	
	public void set(String path, Object value) {
		if (value instanceof Location) {
			value = LocationSerializationHelper.getInstance().serialize((Location) value);
		}
		
		config.set(path, value);
	}
	
}
