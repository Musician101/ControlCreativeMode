package musician101.controlcreativemode.forge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import musician101.controlcreativemode.common.config.AbstractConfig;
import musician101.controlcreativemode.forge.lib.Constants.ModInfo;
import musician101.controlcreativemode.forge.util.DamageValues;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class ConfigHandler extends AbstractConfig
{
	private Configuration config;
	
	//TODO redo update checker
	//TODO no break needed
	//TODO subType methods doesn't exist on the server side -_-
	public static List<String> noEntityBasedInventory = new ArrayList<String>();
	public static List<String> noSpawn = new ArrayList<String>();
	public static List<Block> noBlockBasedInventory = new ArrayList<Block>();
	public static List<ItemStack> noDrop = new ArrayList<ItemStack>();
	//TODO might need to change to block, highly unlikely
	public static List<ItemStack> noPlace = new ArrayList<ItemStack>();
	public static List<ItemStack> noThrow = new ArrayList<ItemStack>();
	
	public ConfigHandler(File configDir)
	{
		if (config == null)
		{
			config = new Configuration(new File(configDir, ModInfo.ID + ".cfg"));
			loadConfig();
		}
	}
	
	@EventHandler
	public void onConfigChangedEvent(OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(ModInfo.ID))
			loadConfig();
	}
	
	private void loadConfig()
	{
		setLavaBucketBlocked(config.getBoolean("blockLavaBucket", Configuration.CATEGORY_GENERAL, true, "Block the use of lava buckets while in creative mode."));
		setTNTMinecartBlocked(config.getBoolean("blockTNTMinecart", Configuration.CATEGORY_GENERAL, true, "Block the use of TNT minecarts while in creative mode."));
		setWaterBucketBlocked(config.getBoolean("blockWaterBucket", Configuration.CATEGORY_GENERAL, true, "Block the use of water buckets while in creative mode."));
		//checkForUpdate = config.getBoolean("checkForUpdate", Configuration.CATEGORY_GENERAL, true, "Whether the plugin should check for updates.");
		
		// I honestly don't know if this is going to work
		for (String mob : config.getStringList("noEntityBasedInventory", Configuration.CATEGORY_GENERAL, new String[0], "Block the use of an entity's inventory."))
		{
			if (mob.equalsIgnoreCase("all"))
				for (Object entity : EntityList.stringToClassMapping.keySet())
					noEntityBasedInventory.add(entity.toString());
			else
				for (Object entity : EntityList.stringToClassMapping.keySet())
					if (mob.equals(entity.toString()))
						noEntityBasedInventory.add(mob);
		}
		
		for (String mob : config.getStringList("noSpawn", Configuration.CATEGORY_GENERAL, new String[0], "Block the use of an entity's inventory."))
		{
			if (mob.equalsIgnoreCase("all"))
				for (Object entity : EntityList.stringToClassMapping.keySet())
					noSpawn.add(entity.toString());
			else
				for (Object entity : EntityList.stringToClassMapping.keySet())
					if (mob.equals(entity.toString()))
						noSpawn.add(mob);
		}
		
		//TODO need to rewrite this seeing as this only pertains to blocks
		for (String entry : config.getStringList("noBlockBasedInventory", Configuration.CATEGORY_GENERAL, new String[0], "Block the use of a block's inventory while in creative mode."))
		{
			RegistryNamespaced blockRegistry = Block.blockRegistry;
			if (!entry.contains(":"))
			{
				for (Object block : blockRegistry.getKeys())
				{
					if (block.toString().startsWith(entry))
					{
						Block b = (Block) blockRegistry.getObject(block.toString());
						DamageValues.addSubTypes(b, noBlockBasedInventory);
						/*Item i = Item.getByNameOrId(item.toString());
						// Might throw a NPE because CreativeTab argument == null
						i.getSubItems(i, null, noBlockBasedInventory);*/
					}
				}
			}
			else if (entry.split(":").length == 2)
			{
				for (Object block : blockRegistry.getKeys())
				{
					for (Object item : itemRegistry.getKeys())
					{
						if (block.equals(item) && block.toString().equals(entry))
						{
							Item i = Item.getByNameOrId(item.toString());
							// Might throw a NPE because CreativeTab argument == null
							i.getSubItems(i, null, noBlockBasedInventory);
						}
					}
				}
			}
			else if (entry.split(":").length == 3)
			{
				Item item = Item.getByNameOrId(entry.split(":")[0] + ":" + entry.split(":")[1]);
				String meta = entry.split(":")[2];
				noBlockBasedInventory.add(new ItemStack(item, 1, Integer.parseInt(meta)));
			}
		}
		
		for (String entry : config.getStringList("noDrop", Configuration.CATEGORY_GENERAL, new String[0], "Block which items can be dropped while in creative mode."))
		{
			RegistryNamespaced itemRegistry = Item.itemRegistry;
			if (!entry.contains(":"))
			{
				for (Object item : itemRegistry.getKeys())
				{
					if (item.toString().startsWith(entry))
					{
						Item i = Item.getByNameOrId(item.toString());
						// Might throw a NPE because CreativeTab argument == null
						i.getSubItems(i, null, noDrop);
					}
				}
			}
			else if (entry.split(":").length == 2)
			{
				for (Object item : itemRegistry.getKeys())
				{
					if (item.toString().equals(entry))
					{
						Item i = Item.getByNameOrId(item.toString());
						// Might throw a NPE because CreativeTab argument == null
						i.getSubItems(i, null, noDrop);
					}
				}
			}
			else if (entry.split(":").length == 3)
			{
				Item item = Item.getByNameOrId(entry.split(":")[0] + ":" + entry.split(":")[1]);
				String meta = entry.split(":")[2];
				noDrop.add(new ItemStack(item, 1, Integer.parseInt(meta)));
			}
		}
		
		for (String entry : config.getStringList("noPlace", Configuration.CATEGORY_GENERAL, new String[0], "Block which blocks can be placed while in creative mode."))
		{
			RegistryNamespaced blockRegistry = Block.blockRegistry;
			RegistryNamespaced itemRegistry = Item.itemRegistry;
			if (!entry.contains(":"))
			{
				for (Object block : blockRegistry.getKeys())
				{
					for (Object item : itemRegistry.getKeys())
					{
						if (block.equals(item) && block.toString().startsWith(entry) && item.toString().startsWith(entry))
						{
							Item i = Item.getByNameOrId(item.toString());
							// Might throw a NPE because CreativeTab argument == null
							i.getSubItems(i, null, noPlace);
						}
					}
				}
			}
			else if (entry.split(":").length == 2)
			{
				for (Object block : blockRegistry.getKeys())
				{
					for (Object item : itemRegistry.getKeys())
					{
						if (block.equals(item) && block.toString().equals(entry))
						{
							Item i = Item.getByNameOrId(item.toString());
							// Might throw a NPE because CreativeTab argument == null
							i.getSubItems(i, null, noPlace);
						}
					}
				}
			}
			else if (entry.split(":").length == 3)
			{
				Item item = Item.getByNameOrId(entry.split(":")[0] + ":" + entry.split(":")[1]);
				String meta = entry.split(":")[2];
				noPlace.add(new ItemStack(item, 1, Integer.parseInt(meta)));
			}
		}
		
		for (String entry : config.getStringList("noThrow", Configuration.CATEGORY_GENERAL, new String[0], "Block items that can be thrown with the 'Use Item/Place Block' button while in creative mode."))
		{
			RegistryNamespaced itemRegistry = Item.itemRegistry;
			if (!entry.contains(":"))
			{
				for (Object item : itemRegistry.getKeys())
				{
					if (item.toString().startsWith(entry))
					{
						Item i = Item.getByNameOrId(item.toString());
						// Might throw a NPE because CreativeTab argument == null
						i.getSubItems(i, null, noThrow);
					}
				}
			}
			else if (entry.split(":").length == 2)
			{
				for (Object item : itemRegistry.getKeys())
				{
					if (item.toString().equals(entry))
					{
						Item i = Item.getByNameOrId(item.toString());
						// Might throw a NPE because CreativeTab argument == null
						i.getSubItems(i, null, noThrow);
					}
				}
			}
			else if (entry.split(":").length == 3)
			{
				Item item = Item.getByNameOrId(entry.split(":")[0] + ":" + entry.split(":")[1]);
				String meta = entry.split(":")[2];
				noThrow.add(new ItemStack(item, 1, Integer.parseInt(meta)));
			}
		}
	}
	
	public boolean canSpawnEntity(Entity entity)
	{
		return isEntityBlocked(noSpawn, entity);
	}
	
	public boolean isEntityBasedInventoryBlocked(Entity entity)
	{
		return isEntityBlocked(noEntityBasedInventory, entity);
	}
	
	private boolean isEntityBlocked(List<String> ids, Entity entity)
	{
		return ids.contains(EntityList.getEntityString(entity));
	}
	
	public boolean isBlockBasedInventoryBlocked(World world, Block block, BlockPos position)
	{
		for (Block b : noBlockBasedInventory)
			//TODO might break
			if (block.getUnlocalizedName().equals(b.getUnlocalizedName()) && block.getDamageValue(world, position) == b.getDamageValue(world, position))
				return true;
		
		return false;
	}
	
	public boolean isBlockPlace(ItemStack item)
	{
		return isItemBlocked(noPlace, item);
	}
	
	public boolean isItemDropBlocked(ItemStack item)
	{
		return isItemBlocked(noDrop, item);
	}
	
	public boolean isThrownItemBlocked(ItemStack item)
	{
		return isItemBlocked(noThrow, item);
	}
	
	private boolean isItemBlocked(List<ItemStack> itemStacks, ItemStack itemStack)
	{
		for (ItemStack is : itemStacks)
			if (is.getItem() == itemStack.getItem() && is.getItemDamage() == itemStack.getItemDamage())
				return true;
		
		return false;
	}
}
