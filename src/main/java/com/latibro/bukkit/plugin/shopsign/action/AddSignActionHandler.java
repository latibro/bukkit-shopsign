package com.latibro.bukkit.plugin.shopsign.action;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.latibro.bukkit.Location;
import com.latibro.bukkit.plugin.shopsign.ShopSignPlugin;
import com.latibro.bukkit.plugin.shopsign.shop.Shop;

public class AddSignActionHandler implements CommandExecutor, Listener {

	private final ShopSignPlugin plugin;

	public AddSignActionHandler(ShopSignPlugin plugin) {
		this.plugin = plugin;

		plugin.getCommand("shopsignadd").setExecutor(this);

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO maybe canceling active add action on other command

		if (plugin.getCommand("shopsignadd") != command) {
			throw new IllegalArgumentException("Wrong command");
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("Player only command");
			return true;
		}

		Player player = (Player) sender;

		player.setMetadata("com.latibro.bukkit.plugin.shopsign.command.add", new FixedMetadataValue(plugin, true));
		sender.sendMessage("Right click a sign to add is as a shop");
		return true;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		// TODO maybe canceling active add session on use

		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			// not right clicked
			return;
		}

		Block clickedBlock = event.getClickedBlock();

		if (!(clickedBlock.getState() instanceof Sign)) {
			// not a sign
			return;
		}

		Player player = event.getPlayer();

		if (!player.hasMetadata("com.latibro.bukkit.plugin.shopsign.command.add")) {
			// No active add session
			plugin.getLogger().info("No active add session");
			return;
		}

		player.removeMetadata("com.latibro.bukkit.plugin.shopsign.command.add", plugin);
		event.setCancelled(true);

		Location location = Location.convertInstance(clickedBlock.getLocation());

		if (ShopSignPlugin.getInstance().getShopManager().hasShop(location)) {
			player.sendMessage("Shop already exists: " + location);
			return;
		}

		Shop shop = Shop.newInstance(location);

		ShopSignPlugin.getInstance().getShopManager().addShop(shop);

		ShopSignPlugin.getInstance().getShopManager().saveConfig();

		player.sendMessage("Shop added: " + location);

	}

}
