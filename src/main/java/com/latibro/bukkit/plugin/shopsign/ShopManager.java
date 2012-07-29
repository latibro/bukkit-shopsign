package com.latibro.bukkit.plugin.shopsign;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.latibro.bukkit.Location;
import com.latibro.bukkit.plugin.shopsign.shop.Shop;
import com.latibro.util.collection.Collections;
import com.latibro.util.collection.Matcher;

public class ShopManager {

	private final ShopSignPlugin plugin;
	private final Set<Shop> shops = new LinkedHashSet<Shop>();

	public ShopManager(ShopSignPlugin plugin) {
		this.plugin = plugin;
	}

	public void addShop(Shop shop) {
		if (hasShop(shop.getLocation())) {
			throw new IllegalArgumentException("Shop already exist on that location");
		}
		
		shops.add(shop);
	}

	public Set<Shop> getShops() {
		return java.util.Collections.unmodifiableSet(shops);
	}

	public boolean hasShop(org.bukkit.Location location) {
		return hasShop(Location.convertInstance(location));
	}

	public boolean hasShop(Location location) {
		return Collections.any(shops, new ShopLocationMatcher(location));
	}

	public Shop getShop(org.bukkit.Location location) {
		return getShop(Location.convertInstance(location));
	}

	public Shop getShop(Location location) {
		return Collections.first(shops, new ShopLocationMatcher(location));
	}

	public ShopSignPlugin getPlugin() {
		return plugin;
	}

	public void reloadConfig() {
		// Look for defaults in the jar
		File shopsConfigFile = new File(plugin.getDataFolder(), "shops.yml");
		if (!shopsConfigFile.exists()) {
			plugin.getLogger().info("No shops.yml");
			return;
		}

		YamlConfiguration shopsConfig = YamlConfiguration.loadConfiguration(shopsConfigFile);

		for (String shopId : shopsConfig.getKeys(false)) {
			plugin.getLogger().info("Shop: " + shopId);

			ConfigurationSection shopConfig = shopsConfig.getConfigurationSection(shopId);

			Shop shop = Shop.newInstance(shopId, shopConfig);
			// shop.reloadConfig(shopConfig)

			addShop(shop);

			plugin.getLogger().info("Shop loaded: " + shopId);
		}
	}

	public void saveConfig() {
		File shopsConfigFile = new File(plugin.getDataFolder(), "shops.yml");

		YamlConfiguration shopsConfig = new YamlConfiguration();

		for (Shop shop : shops) {
			shopsConfig.set(shop.getId(), shop.getConfigration());
		}
		
		try {
			shopsConfig.save(shopsConfigFile);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + shopsConfigFile, e);
		}
	}

	private static class ShopLocationMatcher implements Matcher<Shop> {

		private final Location location;

		public ShopLocationMatcher(Location location) {
			this.location = location.getBlockLocation();
		}

		public boolean match(Shop shop) {
			return location.equals(shop.getLocation().getBlockLocation());
		}

	}

}
