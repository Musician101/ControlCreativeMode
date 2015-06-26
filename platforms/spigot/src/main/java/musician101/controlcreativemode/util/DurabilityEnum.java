package musician101.controlcreativemode.util;

import org.bukkit.Material;

public enum DurabilityEnum
{
	STONE(Material.STONE, 6),
	DIRT(Material.DIRT, 2),
	PLANKS(Material.WOOD, 5),
	SAPLING(Material.SAPLING, 5),
	SAND(Material.SAND, 1),
	LOG(Material.LOG, 15),
	LEAVES(Material.LEAVES, 15),
	SPONGE(Material.SPONGE, 1),
	SANDSTONE(Material.SANDSTONE, 2),
	LONG_GRASS(Material.LONG_GRASS, 2),
	WOOL(Material.WOOL, 15),
	RED_ROSE(Material.RED_ROSE, 8),
	DOUBLE_STEP(Material.DOUBLE_STEP, 15),
	STEP(Material.STEP, 15),
	TORCH(Material.TORCH, 5),
	REDSTONE_TORCH_ON(Material.REDSTONE_TORCH_ON, 5),
	REDSTONE_TORCH_OFF(Material.REDSTONE_TORCH_OFF, 5),
	STAINED_GLASS(Material.STAINED_GLASS, 15),
	SILVERFISH_BLOCK(Material.MONSTER_EGGS, 5),
	STONEBRICKS(Material.SMOOTH_BRICK, 3),
	WOOD_DOUBLE_STEP(Material.WOOD_DOUBLE_STEP, 5),
	COBBLE_WALL(Material.COBBLE_WALL, 1),
	ANVIL(Material.ANVIL, 2),
	QUARTZ_BLOCK(Material.QUARTZ_BLOCK, 2),
	STAINED_CLAY(Material.STAINED_CLAY, 15),
	STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 15),
	LEAVES2(Material.LEAVES_2, 13),
	LOG2(Material.LOG_2, 13),
	PRISMARINE(Material.PRISMARINE, 2),
	CARPET(Material.CARPET, 15),
	DOUBLE_TALL_FLOWER(Material.DOUBLE_PLANT, 5),
	RED_SANDSTONE(Material.RED_SANDSTONE, 2),
	COAL(Material.COAL, 1),
	GOLDEN_APPLE(Material.GOLDEN_APPLE, 1),
	RAW_FISH(Material.RAW_FISH, 3),
	COOKED_FISH(Material.COOKED_FISH, 1),
	INK_SACK(Material.INK_SACK, 15),
	MONSTER_EGG(Material.MONSTER_EGG, 120),
	SKULL(Material.SKULL, 4),
	BANNER(Material.BANNER, 15);
	
	Material type;
	int maxDurability;
	
	private DurabilityEnum(Material type)
	{
		this(type, 0);
	}
	
	private DurabilityEnum(Material type, int maxDurability)
	{
		this.type = type;
		this.maxDurability = maxDurability; 
	}
	
	public Material getMaterial()
	{
		return type;
	}
	
	public int getMaxDurability()
	{
		return maxDurability;
	}
	
	public static int getMaxDurability(Material material)
	{
		for (DurabilityEnum value : values())
			if (material == value.getMaterial())
				return value.getMaxDurability();
		
		return 0;
	}
}
