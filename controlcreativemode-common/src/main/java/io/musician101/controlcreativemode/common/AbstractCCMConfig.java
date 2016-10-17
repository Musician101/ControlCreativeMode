package io.musician101.controlcreativemode.common;

import io.musician101.musicianlibrary.java.minecraft.AbstractConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class AbstractCCMConfig<E, I, S> extends AbstractConfig
{
    protected boolean checkForUpdate;
    protected final List<E> bannedEntityDamage = new ArrayList<>();
    protected final List<E> bannedEntityInventory = new ArrayList<>();
    protected final List<E> bannedEntitySpawn = new ArrayList<>();
    protected final List<I> bannedItemDrop = new ArrayList<>();
    protected final List<I> bannedBlockBreak = new ArrayList<>();
    protected final List<I> bannedBlockInventory = new ArrayList<>();
    protected final List<I> bannedBlockPlace = new ArrayList<>();
    protected final List<I> bannedRightClick = new ArrayList<>();

    protected AbstractCCMConfig(File configFile)
    {
        super(configFile);
    }

    public boolean checkForUpdate()
    {
        return checkForUpdate;
    }

    public boolean isBlockBreakBanned(I itemStack)
    {
        return containsItem(bannedBlockBreak, itemStack);
    }

    public boolean isBlockInventoryBanned(I itemStack)
    {
        return containsItem(bannedBlockInventory, itemStack);
    }

    public boolean isBlockPlaceBanned(I itemStack)
    {
        return containsItem(bannedBlockInventory, itemStack);
    }

    public boolean isEntityDamageBanned(E entityType)
    {
        return containsEntityType(bannedEntityDamage, entityType);
    }

    public boolean isEntityInventoryBanned(E entityType)
    {
        return containsEntityType(bannedEntityInventory, entityType);
    }

    public boolean isEntitySpawnBanned(E entityType)
    {
        return containsEntityType(bannedEntitySpawn, entityType);
    }

    public boolean isItemDropBanned(I itemStack)
    {
        return containsItem(bannedItemDrop, itemStack);
    }

    public boolean isRightClickBanned(I itemStack)
    {
        return containsItem(bannedRightClick, itemStack);
    }

    protected abstract void addAllEntities(List<E> list);

    protected abstract void addEntities(List<E> list, List<String> entityTypeNames);

    protected abstract void addAllItems(List<I> list);

    protected abstract void addItems(List<I> list, S serializer);

    protected abstract boolean containsEntityType(List<E> list, E entityType);

    protected abstract boolean containsItem(List<I> list, I itemStack);
}
