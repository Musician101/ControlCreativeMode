package musician101.controlcreativemode.util;

public enum DurabilityEnum
{
	STONE(6),
	DIRT(2),
	PLANKS(5),
	SAPLING(5),
	SAND(1),
	LOG(15),
	LEAVES(15),
	SPONGE(1),
	SANDSTONE(2),
	LONG_GRASS(2),
	WOOL(15),
	RED_ROSE(8),
	DOUBLE_STEP(15),
	STEP(15),
	TORCH(5),
	REDSTONE_TORCH(5),
	STAINED_GLASS(15),
	SILVERFISH_BLOCK(5),
	STONEBRICKS(3),
	WOOD_DOUBLE_STEP(5),
	COBBLE_WALL(1),
	ANVIL(2),
	QUARTZ_BLOCK(2),
	STAINED_CLAY(15),
	STAINED_GLASS_PANE(15),
	LEAVES2(13),
	LOG2(13),
	PRISMARINE(2),
	CARPET(15),
	DOUBLE_TALL_FLOWER(5),
	RED_SANDSTONE(2),
	COAL(1),
	GOLDEN_APPLE(1),
	RAW_FISH(3),
	COOKED_FISH(1),
	INK_SACK(15),
	MONSTER_EGG(120),
	SKULL(4),
	BANNER(15);
	
	short maxDurability;
	
	private DurabilityEnum()
	{
		this(0);
	}
	
	private DurabilityEnum(int maxDurability)
	{
		this.maxDurability = (short) maxDurability; 
	}
	
	public short getMaxDurability()
	{
		return maxDurability;
	}
}
