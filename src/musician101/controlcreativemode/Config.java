package musician101.controlcreativemode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import musician101.controlcreativemode.lib.ErrorMessages;
import musician101.controlcreativemode.lib.Messages;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
	ControlCreativeMode plugin;
	public boolean blockLavaBucket;
	public boolean blockTNTMinecart;
	public boolean blockWaterBucket;
	public boolean checkForUpdate;
	public List<String> noBlockBasedInventory = new ArrayList<String>();
	public List<String> noEntityBasedInventory;
	public List<String> noDrop = new ArrayList<String>();
	public List<String> noPlace = new ArrayList<String>();
	public List<String> noSpawn = new ArrayList<String>();
	public List<String> noThrow = new ArrayList<String>();
	
	/**
	 * Config constructor.
	 * 
	 * @param plugin Reference's the plugin's main class.
	 */
	public Config(ControlCreativeMode plugin)
	{
		this.plugin = plugin;
		File config = new File(plugin.getDataFolder(), "config.yml");
		if (!config.exists())
		{
			if (!config.getParentFile().mkdirs()) plugin.getLogger().warning("Could not create config.yml");
			plugin.saveDefaultConfig();
		}
		reloadConfiguration();
	}
	
	/** Reloads the server's configuration file. */
	public void reloadConfiguration()
	{
		plugin.reloadConfig();
		final FileConfiguration config = plugin.getConfig();
		blockLavaBucket = config.getBoolean("blockLavaBucket", true);
		blockTNTMinecart = config.getBoolean("blockTNTMinecart", true);
		blockWaterBucket = config.getBoolean("blockWaterBucket", true);
		checkForUpdate = config.getBoolean("checkForUpdate", true);
		noEntityBasedInventory = config.getStringList("noEntityBasedInventory");
		noSpawn = config.getStringList("noSpawn");
		
		for (String material : config.getStringList("noBlockBasedInventory"))
		{
			if (Material.getMaterial(material.toUpperCase()) != null)
				noBlockBasedInventory.add(material.toUpperCase());
			else
				plugin.getLogger().warning(ErrorMessages.getMaterialError(material));
		}
		
		for (String material : config.getStringList("noDrop"))
		{
			if (Material.getMaterial(material.toUpperCase()) != null)
				noDrop.add(material.toUpperCase());
			else
				plugin.getLogger().warning(ErrorMessages.getMaterialError(material));
		}
		
		for (String material : config.getStringList("noPlace"))
		{
			if (Material.getMaterial(material.toUpperCase()) != null)
				noPlace.add(material.toUpperCase());
			else
				plugin.getLogger().warning(ErrorMessages.getMaterialError(material));
		}
		
		for (String mob : config.getStringList("noSpawn"))
		{
			if (Messages.MOB_LIST.contains(mob.toLowerCase()))
				noSpawn.add(mob.toLowerCase());
			else
				plugin.getLogger().warning(ErrorMessages.getMobError(mob));
		}
		
		for (String material : config.getStringList("noThrow"))
		{
			if (Material.getMaterial(material.toUpperCase()) != null)
				noThrow.add(material.toUpperCase());
			else
				plugin.getLogger().warning(ErrorMessages.getMaterialError(material));
		}
	}
}
