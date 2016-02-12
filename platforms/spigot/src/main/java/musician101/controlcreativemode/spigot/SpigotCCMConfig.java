package musician101.controlcreativemode.spigot;

import musician101.controlcreativemode.common.AbstractCCMConfig;
import musician101.controlcreativemode.common.Reference.Config;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SpigotCCMConfig extends AbstractCCMConfig<EntityType, ItemStack, ConfigurationSection>
{
	private final SpigotCCM plugin;
	
	public SpigotCCMConfig(SpigotCCM plugin)
	{
		super(new File(plugin.getDataFolder(), "config.yml"));
        this.plugin = plugin;
        plugin.saveDefaultConfig();
		reload();
	}

    @Override
	public void reload()
	{
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();
        if (config.get(Config.BANNED_BLOCK_BREAK, Config.ALL) instanceof String)
        {
            if (config.getString(Config.BANNED_BLOCK_BREAK, Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllItems(bannedBlockBreak);
        }
        else
            addItems(bannedBlockBreak, config.getConfigurationSection(Config.BANNED_BLOCK_BREAK));

        if (config.get(Config.BANNED_BLOCK_INV, Config.ALL) instanceof String)
        {
            if (config.getString(Config.BANNED_BLOCK_INV, Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllItems(bannedBlockInventory);
        }
        else
            addItems(bannedBlockInventory, config.getConfigurationSection(Config.BANNED_BLOCK_INV));

        if (config.get(Config.BANNED_BLOCK_PLACE, Config.ALL) instanceof String)
        {
            if (config.getString(Config.BANNED_ITEM_DROP, Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllItems(bannedBlockPlace);
        }
        else
            addItems(bannedBlockPlace, config.getConfigurationSection(Config.BANNED_BLOCK_PLACE));

        if (config.get(Config.BANNED_ENTITY_INV, Config.ALL) instanceof String)
        {
            if (config.getString(Config.BANNED_ENTITY_INV, Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllEntities(bannedEntityInventory);
        }
        else
            addEntities(bannedEntityInventory, config.getStringList(Config.BANNED_ENTITY_INV));

        if (config.get(Config.BANNED_ENTITY_SPAWN, Config.ALL) instanceof String)
        {
            if (config.getString(Config.BANNED_ENTITY_SPAWN, Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllEntities(bannedEntitySpawn);
        }
        else
            addEntities(bannedEntitySpawn, config.getStringList(Config.BANNED_ENTITY_SPAWN));

        if (config.get(Config.BANNED_RIGHT_CLICK, Config.ALL) instanceof String)
        {
            if (config.getString(Config.BANNED_RIGHT_CLICK, Config.ALL).equalsIgnoreCase(Config.ALL))
                addAllItems(bannedRightClick);
        }
        else
            addItems(bannedRightClick, config.getConfigurationSection(Config.BANNED_RIGHT_CLICK));
	}

    @Override
    protected void addAllEntities(List<EntityType> list)
    {
        list.addAll(Arrays.asList(EntityType.values()));
    }

    @Override
    protected void addEntities(List<EntityType> list, List<String> entityTypeNames)
    {
        for (String entityTypeName : entityTypeNames)
            for (EntityType entityType : EntityType.values())
                if (entityTypeName.equalsIgnoreCase(entityType.toString()))
                    list.add(entityType);
    }

    @Override
    protected void addAllItems(List<ItemStack> list)
    {
        for (Material material : Material.values())
            list.add(new ItemStack(material, 1));
    }

    @Override
    protected void addItems(List<ItemStack> list, ConfigurationSection configurationSection)
    {
        for (String materialName : configurationSection.getValues(false).keySet())
        {
            for (Material material : Material.values())
            {
                if (materialName.equalsIgnoreCase(material.toString()))
                {
                    if (configurationSection.isString(materialName) && configurationSection.getString(materialName).equalsIgnoreCase(Config.ALL))
                        list.add(new ItemStack(material, 1));
                    else
                        configurationSection.getShortList(materialName).forEach(damage -> list.add(new ItemStack(material, 0, damage)));
                }
            }
        }
    }

    @Override
    protected boolean containsEntityType(List<EntityType> list, EntityType entityType)
    {
        for (EntityType et : list)
            if (entityType.toString().equals(et.toString()))
                return true;

        return false;
    }

    @Override
    protected boolean containsItem(List<ItemStack> list, ItemStack itemStack)
    {
        for (ItemStack is : list)
            if (itemStack.getType() == is.getType())
                if (is.getAmount() > 0 || itemStack.getDurability() == is.getDurability())
                    return true;

        return false;
    }
}
