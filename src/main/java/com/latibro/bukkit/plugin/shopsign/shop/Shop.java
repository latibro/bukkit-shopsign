package com.latibro.bukkit.plugin.shopsign.shop;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.latibro.bukkit.Location;
import com.latibro.bukkit.plugin.shopsign.ShopSignPlugin;

public abstract class Shop {

	private final String id;
	private Location location;
	private List<String> signText;

	protected Shop(String id, ConfigurationSection config) {
		this.id = id;
		this.location = Location.deserialize(config.getConfigurationSection("location").getValues(true));
		
		Block block = location.getBlock();
		if (! (block.getState() instanceof Sign)) {
			throw new IllegalArgumentException("Location is not a sign");
		}

		this.signText = config.getStringList("sign-text");
		ShopSignPlugin.getInstance().getLogger().info("SignText: " + signText);
		
		Sign sign = (Sign) block.getState();
		for (int lineNumber=0; lineNumber<4; lineNumber++) {
			String lineText;
			if (signText.size() <= 2 && lineNumber > 0 && (lineNumber-1) < signText.size()) {
				lineText = signText.get(lineNumber - 1);
			} else if (signText.size() > 2 && lineNumber < signText.size()) {
				lineText = signText.get(lineNumber);
			} else {
				lineText = "";
			}
			ShopSignPlugin.getInstance().getLogger().info("Sign line "+lineNumber+": " + lineText);
			sign.setLine(lineNumber, lineText);
		}
		sign.update();
		
	}

	protected Shop(String id, Location location) {
		this.id = id;
		this.location = location;
	}

	public abstract boolean trade(Player player);
	
	public Location getLocation() {
		return location;
	}

	public static Shop newInstance(Location location) {
		String id = location.getWorld().getName() + "_" + location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
		return new BlockBelowShop(id, location);
	}
	
	public static Shop newInstance(String id, ConfigurationSection config) {
		return new BlockBelowShop(id, config);
	}
	
	public Map<String, Object> getConfigration() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("location", location.serialize());
		map.put("sign-text", signText);
		
		return map;
	}

	public String getId() {
		return id;
	}

}
