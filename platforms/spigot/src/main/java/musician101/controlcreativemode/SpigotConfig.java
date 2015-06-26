package musician101.controlcreativemode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import musician101.controlcreativemode.common.config.AbstractConfig;
import musician101.controlcreativemode.util.DurabilityEnum;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class SpigotConfig extends AbstractConfig
{
	ControlCreativeMode plugin;
	public List<EntityType> noEntityBasedInventory = new ArrayList<EntityType>();
	public List<EntityType> noSpawn = new ArrayList<EntityType>();
	public List<ItemStack> noBlockBasedInventory = new ArrayList<ItemStack>();
	public List<ItemStack> noDrop = new ArrayList<ItemStack>();
	public List<ItemStack> noPlace = new ArrayList<ItemStack>();
	public List<ItemStack> noThrow = new ArrayList<ItemStack>();
	
	public SpigotConfig(ControlCreativeMode plugin)
	{
		super(plugin.getConfig().getBoolean("blockLavaBucket", true),
				plugin.getConfig().getBoolean("blockTNTMinecart", true),
				plugin.getConfig().getBoolean("blockWaterBucekt", true),
				plugin.getConfig().getBoolean("checkForUpdate", true));
		
		this.plugin = plugin;
		reloadConfiguration();
	}
	
	public void reloadConfiguration()
	{
		plugin.reloadConfig();
		final FileConfiguration config = plugin.getConfig();
		setLavaBucketBlocked(config.getBoolean("blockLavaBucket", true));
		setTNTMinecartBlocked(config.getBoolean("blockTNTMinecart", true));
		setWaterBucketBlocked(config.getBoolean("blockWaterBucket", true));
		setCheckForUpdate(config.getBoolean("checkForUpdate", true));
		
		for (String mob : config.getStringList("noEntityBasedInventory"))
		{
			if (mob.equalsIgnoreCase("all"))
			{
				Collections.addAll(noEntityBasedInventory, EntityType.values());
				return;
			}
			else
				noEntityBasedInventory.add(EntityType.valueOf(mob.toUpperCase()));
		}
		
		for (String mob : config.getStringList("noSpawn"))
		{
			if (mob.equalsIgnoreCase("all"))
				for (EntityType entity : EntityType.values())
					noSpawn.add(entity);
			else
				noSpawn.add(EntityType.valueOf(mob.toUpperCase()));
		}
		//TODO left off here. redo all of the all options
		for (String mat : config.getStringList("noBlockBasedInventory"))
		{
			if (mat.equalsIgnoreCase("all"))
			{
				for (Material material : Material.values())
					if (material.isBlock())
						noBlockBasedInventory.add(new ItemStack(material, 0, (short) 0));
				
				return;
			}
			else
				noBlockBasedInventory.add(new ItemStack(Material.getMaterial(mat)));
		}
		
		ConfigurationSection noDropCS = config.getConfigurationSection("noDrop");
		for (Material material : Material.values())
		{
			if (material.getMaxDurability() > 0)
				for (short durability = 0; durability < material.getMaxDurability(); durability++)
					noDrop.add(new ItemStack(material, 0, durability));
			else
				for (Short durability : noDropCS.getShortList(material.toString()))
					noDrop.add(new ItemStack(material, 0, durability));
		}
		
		for (Entry<String, Object> entry : config.getConfigurationSection("noPlace").getValues(true).entrySet())
		{
			if (!(entry.getValue() instanceof MemorySection))
			{
				if (entry.getKey().equalsIgnoreCase("all"))
				{
					for (Material m : Material.values())
						if (m.isBlock())
							for (int dura = 0; dura < DurabilityEnum.getMaxDurability(m); dura++)
								noPlace.add(new ItemStack(m, 0, (short) dura));
				}
				else
				{
					for (Object data : (List<?>) entry.getValue())
					{
						if (data.equals("all"))
							for (int dura = 0; dura < DurabilityEnum.getMaxDurability(Material.getMaterial(entry.getKey().toUpperCase())); dura++)
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
						for (int dura = 0; dura < DurabilityEnum.getMaxDurability(Material.getMaterial(entry.getKey().toUpperCase())); dura++)
							noThrow.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, (short) dura));
					else
						noThrow.add(new ItemStack(Material.getMaterial(entry.getKey().toUpperCase()), 0, Short.valueOf(data.toString())));
				}
			}
		}
	}
}
