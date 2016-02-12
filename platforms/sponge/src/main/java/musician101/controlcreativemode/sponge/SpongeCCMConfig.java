package musician101.controlcreativemode.sponge;

import com.google.common.reflect.TypeToken;
import musician101.controlcreativemode.common.AbstractCCMConfig;
import musician101.controlcreativemode.common.Reference;
import musician101.controlcreativemode.common.Reference.Config;
import musician101.controlcreativemode.common.Reference.Messages;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.manipulator.catalog.CatalogBlockData;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class SpongeCCMConfig extends AbstractCCMConfig<EntityType, ItemStack, ConfigurationNode>
{
	public SpongeCCMConfig()
	{
		super(new File("config/" + Reference.ID, "config.conf"));
		reload();
	}

	@Override
	public void reload()
	{
		Logger logger = SpongeCCM.instance().getLogger();
        if (!configFile.exists())
        {
            if (!configFile.mkdirs())
            {
                logger.error(Messages.CONFIG_LOAD_FAIL);
                return;
            }

            try
            {
                if (!configFile.createNewFile())
                {
                    logger.error(Messages.CONFIG_LOAD_FAIL);
                    return;
                }

                URL url = SpongeCCM.class.getResource("config.conf");
                if (url == null)
                {
                    logger.error(Messages.CONFIG_LOAD_FAIL);
                    return;
                }

                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                InputStream input = connection.getInputStream();
                OutputStream output = new FileOutputStream(configFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = input.read(buf)) > 0)
                    output.write(buf, 0, len);

                output.close();
                input.close();
            }
            catch (IOException e)
            {
                logger.warn(Messages.CONFIG_LOAD_FAIL);
                return;
            }
        }

        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setFile(configFile).build();
        ConfigurationNode config;
        try
        {
            config = loader.load();
        }
        catch (IOException e)
        {
            logger.error(Messages.CONFIG_LOAD_FAIL);
            return;
        }

        ConfigurationNode bannedBlockBreakNode = config.getNode(Config.BANNED_BLOCK_BREAK);
        if (bannedBlockBreakNode.getValue(Config.ALL) instanceof String)
        {
            if (bannedBlockBreakNode.getString(Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllItems(bannedBlockBreak);
        }
        else
            addItems(bannedBlockBreak, bannedBlockBreakNode);

        ConfigurationNode bannedBlockInventoryNode = config.getNode(Config.BANNED_BLOCK_INV);
        if (bannedBlockInventoryNode.getValue(Config.ALL) instanceof String)
        {
            if (bannedBlockInventoryNode.getString(Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllItems(bannedBlockInventory);
        }
        else
            addItems(bannedBlockInventory, bannedBlockInventoryNode);

        ConfigurationNode bannedBlockPlaceNode = config.getNode(Config.BANNED_BLOCK_PLACE);
        if (bannedBlockBreakNode.getValue(Config.ALL) instanceof String)
        {
            if (bannedBlockPlaceNode.getString(Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllItems(bannedBlockPlace);
        }
        else
            addItems(bannedBlockPlace, bannedBlockPlaceNode);

        ConfigurationNode bannedEntityInventoryNode = config.getNode(Config.BANNED_ENTITY_INV);
        if (bannedEntityInventoryNode.getValue(Config.ALL) instanceof String)
        {
            if (bannedEntityInventoryNode.getString(Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllEntities(bannedEntityInventory);
        }
        else
        {
            try
            {
                addEntities(bannedEntityInventory, bannedEntityInventoryNode.getList(TypeToken.of(String.class)));
            }
            catch (ObjectMappingException e)
            {
                logger.error(Messages.CONFIG_LOAD_FAIL);
            }
        }

        ConfigurationNode bannedEntitySpawnNode = config.getNode(Config.BANNED_ENTITY_SPAWN);
        if (bannedEntitySpawnNode.getValue(Config.ALL) instanceof String)
        {
            if (bannedEntitySpawnNode.getString(Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllEntities(bannedEntitySpawn);
        }
        else
        {
            try
            {
                addEntities(bannedEntitySpawn, bannedEntitySpawnNode.getList(TypeToken.of(String.class)));
            }
            catch (ObjectMappingException e)
            {
                logger.error(Messages.CONFIG_LOAD_FAIL);
            }
        }

        ConfigurationNode bannedRightClickedNode = config.getNode(Config.BANNED_RIGHT_CLICK);
        if (bannedRightClickedNode.getValue(Config.ALL) instanceof String)
        {
            if (bannedRightClickedNode.getString(Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllItems(bannedRightClick);
        }
        else
            addItems(bannedRightClick, bannedRightClickedNode);
	}

    @Override
    protected void addAllEntities(List<EntityType> list)
    {
        list.addAll(Sponge.getRegistry().getAllOf(EntityType.class));
    }

    @Override
    protected void addEntities(List<EntityType> list, List<String> entityTypeNames)
    {
        for (String entityTypeName : entityTypeNames)
            for (EntityType entityType : Sponge.getRegistry().getAllOf(EntityType.class))
                if (entityTypeName.equalsIgnoreCase(entityType.getId()))
                    list.add(entityType);
    }

    @Override
    protected void addAllItems(List<ItemStack> list)
    {
        for (ItemType itemType : Sponge.getRegistry().getAllOf(ItemType.class))
            list.add(ItemStack.of(itemType, 1));
    }

    @Override
    protected void addItems(List<ItemStack> list, ConfigurationNode configurationNode)
    {
        for (Object itemNameObject : configurationNode.getChildrenMap().keySet())
        {
            String itemName = itemNameObject.toString();
            for (ItemType itemType : Sponge.getRegistry().getAllOf(ItemType.class))
            {
                if (itemName.equalsIgnoreCase(itemType.getId()))
                {
                    ConfigurationNode itemTypeNode = configurationNode.getNode(itemName);
                    if (itemTypeNode.getValue() instanceof String && itemTypeNode.getString(Config.ALL).equalsIgnoreCase(Config.ALL))
                        list.add(ItemStack.of(itemType, 1));
                    else
                    {
                        try
                        {
                            for (String variation : itemTypeNode.getList(TypeToken.of(String.class)))
                            {
                                ItemStack item = ItemStack.of(itemType, 0);
                                if (item.get(CatalogBlockData.STONE_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.STONE_TYPE, CatalogBlockData.STONE_DATA, Keys.STONE_TYPE);
                                else if (item.get(CatalogBlockData.DIRT_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.DIRT_TYPE, CatalogBlockData.DIRT_DATA, Keys.DIRT_TYPE);
                                else if (item.get(CatalogBlockData.TREE_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.TREE_TYPE, CatalogBlockData.TREE_DATA, Keys.TREE_TYPE);
                                else if (item.get(CatalogBlockData.SAND_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.SAND_TYPE, CatalogBlockData.SAND_DATA, Keys.SAND_TYPE);
                                else if (item.get(CatalogBlockData.SANDSTONE_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.SANDSTONE_TYPE, CatalogBlockData.SANDSTONE_DATA, Keys.SANDSTONE_TYPE);
                                else if (item.get(CatalogBlockData.SHRUB_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.SHRUB_TYPE, CatalogBlockData.SHRUB_DATA, Keys.SHRUB_TYPE);
                                else if (item.get(CatalogBlockData.DYEABLE_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.DYE_COLOR, CatalogBlockData.DYEABLE_DATA, Keys.DYE_COLOR);
                                else if (item.get(CatalogBlockData.SLAB_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.SLAB_TYPE, CatalogBlockData.SLAB_DATA, Keys.SLAB_TYPE);
                                else if (item.get(CatalogBlockData.DISGUISED_BLOCK_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.DISGUISED_BLOCK_TYPE, CatalogBlockData.DISGUISED_BLOCK_DATA, Keys.DISGUISED_BLOCK_TYPE);
                                else if (item.get(CatalogBlockData.BRICK_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.BRICK_TYPE, CatalogBlockData.BRICK_DATA, Keys.BRICK_TYPE);
                                else if (item.get(CatalogBlockData.WALL_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.WALL_TYPE, CatalogBlockData.WALL_DATA, Keys.WALL_TYPE);
                                else if (item.get(CatalogBlockData.QUARTZ_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.QUARTZ_TYPE, CatalogBlockData.QUARTZ_DATA, Keys.QUARTZ_TYPE);
                                else if (item.get(CatalogBlockData.PRISMARINE_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.PRISMARINE_TYPE, CatalogBlockData.PRISMARINE_DATA, Keys.PRISMARINE_TYPE);
                                else if (item.get(CatalogBlockData.DOUBLE_PLANT_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.DOUBLE_SIZE_PLANT_TYPE, CatalogBlockData.DOUBLE_PLANT_DATA, Keys.DOUBLE_PLANT_TYPE);
                                else if (item.get(CatalogItemData.COAL_ITEM_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.COAL_TYPE, CatalogItemData.COAL_ITEM_DATA, Keys.COAL_TYPE);
                                else if (item.get(CatalogItemData.GOLDEN_APPLE_ITEM_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.GOLDEN_APPLE, CatalogItemData.GOLDEN_APPLE_ITEM_DATA, Keys.GOLDEN_APPLE_TYPE);
                                else if (item.get(CatalogItemData.FISH_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.FISH, CatalogItemData.FISH_DATA, Keys.FISH_TYPE);
                                else if (item.get(CatalogItemData.COOKED_FISH_ITEM_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.COOKED_FISH, CatalogItemData.COOKED_FISH_ITEM_DATA, Keys.COOKED_FISH);
                                else if (item.get(CatalogItemData.SPAWNABLE_DATA).isPresent())
                                    parseItem(itemType, variation, CatalogTypes.ENTITY_TYPE, CatalogItemData.SPAWNABLE_DATA, Keys.SPAWNABLE_ENTITY_TYPE);
                            }
                        }
                        catch (ObjectMappingException e)
                        {
                            SpongeCCM.instance().getLogger().error(Messages.CONFIG_LOAD_FAIL);
                        }
                    }
                }
            }
        }
    }

    private <T extends CatalogType, D extends DataManipulator<D, I>, I extends ImmutableDataManipulator<I, D>>
        ItemStack parseItem(ItemType itemType, String variation, Class<T> typeClass, Class<D> dataClass, Key<Value<T>> key)
    {
        ItemStack.Builder isb = ItemStack.builder().itemType(itemType).quantity(0);
        for (T type : Sponge.getRegistry().getAllOf(typeClass))
        {
            if (type.getId().equalsIgnoreCase(variation))
            {
                D data = Sponge.getDataManager().getManipulatorBuilder(dataClass).get().create().set(Sponge.getRegistry().getValueFactory().createValue(key, type));
                isb.itemData(data);
            }
        }
        return isb.build();
    }

    @Override
    protected boolean containsEntityType(List<EntityType> list, EntityType entityType)
    {
        for (EntityType et : list)
            if (entityType.getId().equals(et.getId()))
                return true;

        return false;
    }

    @Override
    protected boolean containsItem(List<ItemStack> list, ItemStack itemStack)
    {
        for (ItemStack is : list)
            if (itemStack.getItem() == is.getItem())
                if (is.getQuantity() > 0 || isSameVariant(itemStack, is))
                    return true;

        return false;
    }

    private boolean isSameVariant(ItemStack itemStack1, ItemStack itemStack2)
    {
        DataContainer container1 = itemStack1.toContainer();
        DataContainer container2 = itemStack2.toContainer();
        for (DataQuery query1 : container1.getKeys(true))
            for (DataQuery query2 : container2.getKeys(true))
                if(query1 == query2)
                    if (container1.get(query1).get() == container2.get(query2).get())
                        return true;

        return false;
    }
}
