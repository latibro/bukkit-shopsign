package com.latibro.bukkit.plugin.shopsign.action;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Sign;

import com.latibro.bukkit.Location;
import com.latibro.bukkit.plugin.shopsign.ShopSignPlugin;
import com.latibro.bukkit.plugin.shopsign.shop.Shop;

public class UseSignActionHandler implements Listener {

	private final ShopSignPlugin plugin;

	public UseSignActionHandler(ShopSignPlugin plugin) {
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getPlayer().isSneaking()) {
			// Player is sneaking
			return;
		}

		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			// not right clicked
			return;
		}

		Block clickedBlock = event.getClickedBlock();

		plugin.getLogger().info("Block clicked: " + clickedBlock);

		if (!(clickedBlock.getState() instanceof Sign)) {
			// not a sign
			return;
		}

		Location location = Location.convertInstance(clickedBlock.getLocation());

		if (!plugin.getShopManager().hasShop(location)) {
			// no shop
			plugin.getLogger().info("No shop: " + location);
			return;
		}

		event.setCancelled(true);

		Shop shop = plugin.getShopManager().getShop(location);

		plugin.getLogger().info("Shop: " + location);

		Player player = event.getPlayer();

		shop.trade(player);

	}

}
