package com.latibro.bukkit.plugin.shopsign;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class ConfigTest {

	//@Test
	public void serilizeLocation() throws Exception {
		
		World world = mock(World.class);
		when(world.toString()).thenReturn("world");
		
		Location location = new Location(world, 1, 2, 3);
		
		YamlConfiguration config = new YamlConfiguration();
		
		config.set("location", location);
		
		config.save("configtest.yml");
		
		assertThat(config.saveToString(), is("fisk"));
	
	}
}
