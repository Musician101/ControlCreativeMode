package musician101.controlcreativemode.sponge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import musician101.controlcreativemode.sponge.util.CCMUtils;

import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class Config
{
	ControlCreativeMode plugin;
	public boolean blockLavaBucket;
	public boolean blockTNTMinecart;
	public boolean blockWaterBucket;
	public boolean checkForUpdate;
	public List<EntityType> noEntityBasedInventory = new ArrayList<EntityType>();
	public List<EntityType> noSpawn = new ArrayList<EntityType>();
	public List<ItemStack> noBlockBasedInventory = new ArrayList<ItemStack>();
	public List<ItemStack> noDrop = new ArrayList<ItemStack>();
	public List<ItemStack> noPlace = new ArrayList<ItemStack>();
	public List<ItemStack> noThrow = new ArrayList<ItemStack>();
	
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
	
	public void reloadConfiguration()
	{
		plugin.reloadConfig();
		final FileConfiguration config = plugin.getConfig();
		blockLavaBucket = config.getBoolean("blockLavaBucket", true);
		blockTNTMinecart = config.getBoolean("blockTNTMinecart", true);
		blockWaterBucket = config.getBoolean("blockWaterBucket", true);
		checkForUpdate = config.getBoolean("checkForUpdate", true);
		
		for (String mob : config.getStringList("noEntityBasedInventory"))
		{
			if (mob.equalsIgnoreCase("all"))
				for (EntityType entity : EntityType.values())
					noEntityBasedInventory.add(entity);
			else
				noEntityBasedInventory.add(EntityType.valueOf(mob.toUpperCase()));
		}
		
		/* Includes support for MONSTER_EGG being used to spawn ANY type of Entity
		 * Example: Chicken, Zombie, Minecart, Item Frame, etc.
		 */ 
		for (String mob : config.getStringList("noSpawn"))
		{
			if (mob.equalsIgnoreCase("all"))
				for (EntityType entity : EntityType.values())
					noSpawn.add(entity);
			else
				noSpawn.add(EntityType.valueOf(mob.toUpperCase()));
		}
		
		for (Entry<String, Object> entry : config.getConfigurationSection("noBlockBasedInventory").getValues(true).entrySet())
		{
			if (!(entry.getValue() instanceof MemorySection))
			{
				if (entry.getKey().equalsIgnoreCase("all"))
				{
					for (Material m : Material.values())
						if (m.isBlock())
							for (int dura : CCMUtils.getDurabilities(m))
								noBlockBasedInventory.add(new ItemStack(m, 0, (short) dura));
				}
				else
				{
					for (Object data : (List<?>) entry.getValue())
					{
						if (data.equals("all"))
							for (int dura : CCMUtils.getDurabilities(Material.getMaterial(entry.getKey().toUpperCase())))
								noBlockBasedInventory.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, (short) dura));
						else
							noBlockBasedInventory.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, Short.valueOf(data.toString())));
					}
				}
			}
		}
		
		for (Entry<String, Object> entry : config.getConfigurationSection("noDrop").getValues(true).entrySet())
		{
			if (!(entry.getValue() instanceof MemorySection))
			{
				if (entry.getKey().equalsIgnoreCase("all"))
				{
					for (Material m : Material.values())
						for (int dura : CCMUtils.getDurabilities(m))
							noDrop.add(new ItemStack(m, 0, (short) dura));
				}
				else
				{
					for (Object data : (List<?>) entry.getValue())
					{
						if (data.equals("all"))
							for (int dura : CCMUtils.getDurabilities(Material.getMaterial(entry.getKey().toUpperCase())))
								noDrop.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, (short) dura));
						else
							noDrop.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, Short.valueOf(data.toString())));
					}
				}
			}
		}
		
		for (Entry<String, Object> entry : config.getConfigurationSection("noPlace").getValues(true).entrySet())
		{
			if (!(entry.getValue() instanceof MemorySection))
			{
				if (entry.getKey().equalsIgnoreCase("all"))
				{
					for (Material m : Material.values())
						if (m.isBlock())
							for (int dura : CCMUtils.getDurabilities(m))
								noPlace.add(new ItemStack(m, 0, (short) dura));
				}
				else
				{
					for (Object data : (List<?>) entry.getValue())
					{
						if (data.equals("all"))
							for (int dura : CCMUtils.getDurabilities(Material.getMaterial(entry.getKey().toUpperCase())))
								noPlace.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, (short) dura));
						else
							noPlace.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, Short.valueOf(data.toString())));
					}
				}
			}
		}
		
		for (Entry<String, Object> entry : config.getConfigurationSection("noThrow").getValues(true).entrySet())
		{
			if (!(entry.getValue() instanceof MemorySection))
			{
				for (Object data : (List<?>) entry.getValue())
				{
					if (data.equals("all"))
						for (int dura : CCMUtils.getDurabilities(Material.getMaterial(entry.getKey().toUpperCase())))
							noThrow.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, (short) dura));
					else
						noThrow.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, Short.valueOf(data.toString())));
				}
			}
		}
	}
}
