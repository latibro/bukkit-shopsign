package com.latibro.bukkit.plugin.shopsign.shop;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.latibro.bukkit.Location;


public class BlockBelowShop extends Shop {
	
	public BlockBelowShop(String id, ConfigurationSection config) {
		super(id, config);
	}

	public BlockBelowShop(String id, Location location) {
		super(id, location);
	}

	@Override
	public boolean trade(Player player) {
		if (player.getItemInHand().getType() != Material.AIR) {
			player.sendMessage("You already have something in your hand");
			return false;
		}
		
		Block blockBelow = getBlockBelow();
		ItemStack items = new ItemStack(blockBelow.getType(), 1);
		
		player.setItemInHand(items);
		player.sendMessage("Trade completed");
		
		return true;
	}

	public Block getBlockBelow() {
		Block sign = getLocation().getBlock();
		return sign.getRelative(BlockFace.DOWN);
	}
	
	@Override
	public Map<String, Object> getConfigration() {
		Map<String, Object> map = super.getConfigration();
		
		map.put("hint-item", getBlockBelow().getTypeId() + ":" + getBlockBelow().getData() + " - " + getBlockBelow().getType().getNewData(getBlockBelow().getData()));
		
		return map;
	}

}
