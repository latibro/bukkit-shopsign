package com.latibro.bukkit.plugin.shopsign;

import org.bukkit.plugin.java.JavaPlugin;

import com.latibro.bukkit.plugin.shopsign.action.AddSignActionHandler;
import com.latibro.bukkit.plugin.shopsign.action.UseSignActionHandler;


public class ShopSignPlugin extends JavaPlugin {
	
	private static ShopSignPlugin INSTANCE;
	
	public static ShopSignPlugin getInstance() {
		return INSTANCE;
	}
	
	public ShopSignPlugin() {
		INSTANCE = this;
	}
	
	private ShopManager shopManager;
	
	@Override
	public void onEnable() {
		shopManager = new ShopManager(this);
		shopManager.reloadConfig();

		new UseSignActionHandler(this);
		new AddSignActionHandler(this);
		
		shopManager.saveConfig();
	}
	
	@Override
	public void onDisable() {
		shopManager.saveConfig();
	}

	public ShopManager getShopManager() {
		return shopManager;
	}

}
