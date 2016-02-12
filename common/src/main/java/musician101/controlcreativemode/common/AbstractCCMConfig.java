package musician101.controlcreativemode.common;

import musician101.common.java.minecraft.config.AbstractConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCCMConfig<EntityType, ItemStack, Serializer> extends AbstractConfig
{
    protected boolean checkForUpdate;
    protected final List<EntityType> bannedEntityDamage = new ArrayList<>();
    protected final List<EntityType> bannedEntityInventory = new ArrayList<>();
    protected final List<EntityType> bannedEntitySpawn = new ArrayList<>();
    protected final List<ItemStack> bannedItemDrop = new ArrayList<>();
    protected final List<ItemStack> bannedBlockBreak = new ArrayList<>();
    protected final List<ItemStack> bannedBlockInventory = new ArrayList<>();
    protected final List<ItemStack> bannedBlockPlace = new ArrayList<>();
    protected final List<ItemStack> bannedRightClick = new ArrayList<>();

    protected AbstractCCMConfig(File configFile)
    {
        super(configFile);
    }

    public boolean checkForUpdate()
    {
        return checkForUpdate;
    }

    public boolean isBlockBreakBanned(ItemStack itemStack)
    {
        return containsItem(bannedBlockBreak, itemStack);
    }

    public boolean isBlockInventoryBanned(ItemStack itemStack)
    {
        return containsItem(bannedBlockInventory, itemStack);
    }

    public boolean isBlockPlaceBanned(ItemStack itemStack)
    {
        return containsItem(bannedBlockInventory, itemStack);
    }

    public boolean isEntityDamageBanned(EntityType entityType)
    {
        return containsEntityType(bannedEntityDamage, entityType);
    }

    public boolean isEntityInventoryBanned(EntityType entityType)
    {
        return containsEntityType(bannedEntityInventory, entityType);
    }

    public boolean isEntitySpawnBanned(EntityType entityType)
    {
        return containsEntityType(bannedEntitySpawn, entityType);
    }

    public boolean isItemDropBanned(ItemStack itemStack)
    {
        return containsItem(bannedItemDrop, itemStack);
    }

    public boolean isRightClickBanned(ItemStack itemStack)
    {
        return containsItem(bannedRightClick, itemStack);
    }

    protected abstract void addAllEntities(List<EntityType> list);

    protected abstract void addEntities(List<EntityType> list, List<String> entityTypeNames);

    protected abstract void addAllItems(List<ItemStack> list);

    protected abstract void addItems(List<ItemStack> list, Serializer serializer);

    protected abstract boolean containsEntityType(List<EntityType> list, EntityType entityType);

    protected abstract boolean containsItem(List<ItemStack> list, ItemStack itemStack);
}
