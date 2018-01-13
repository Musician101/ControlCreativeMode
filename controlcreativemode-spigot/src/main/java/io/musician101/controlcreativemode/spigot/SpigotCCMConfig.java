package io.musician101.controlcreativemode.spigot;

import io.musician101.controlcreativemode.common.AbstractCCMConfig;
import io.musician101.controlcreativemode.common.Reference.Config;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class SpigotCCMConfig extends AbstractCCMConfig<EntityType, ItemStack, ConfigurationSection> {

    public SpigotCCMConfig() {
        super(new File(SpigotCCM.instance().getDataFolder(), "config.yml"));
        SpigotCCM.instance().saveDefaultConfig();
        reload();
    }

    @Override
    protected void addAllEntities(List<EntityType> list) {
        list.addAll(Arrays.asList(EntityType.values()));
    }

    @Override
    protected void addAllItems(List<ItemStack> list) {
        for (Material material : Material.values()) {
            list.add(new ItemStack(material, 1));
        }
    }

    @Override
    protected void addEntities(List<EntityType> list, List<String> entityTypeNames) {
        for (String entityTypeName : entityTypeNames) {
            for (EntityType entityType : EntityType.values()) {
                if (entityTypeName.equalsIgnoreCase(entityType.toString())) {
                    list.add(entityType);
                }
            }
        }
    }

    @Override
    protected void addItems(List<ItemStack> list, ConfigurationSection configurationSection) {
        for (Material material : Material.values()) {
            MinecraftKey key = Item.REGISTRY.b(CraftMagicNumbers.getItem(material));
            String id = key == null ? "minecraft:air" : key.toString();
            if (configurationSection.isString(id) && configurationSection.getString(id).equalsIgnoreCase(Config.ALL)) {
                list.add(new ItemStack(material, 1));
            }
            else {
                configurationSection.getShortList(id).forEach(damage -> list.add(new ItemStack(material, 0, damage)));
            }
        }
    }

    @Override
    protected boolean containsEntityType(List<EntityType> list, EntityType entityType) {
        for (EntityType et : list) {
            if (entityType.toString().equals(et.toString())) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean containsItem(List<ItemStack> list, ItemStack itemStack) {
        for (ItemStack is : list) {
            if (itemStack.getType() == is.getType() && (is.getAmount() > 0 || itemStack.getDurability() == is.getDurability())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void reload() {
        SpigotCCM.instance().reloadConfig();
        FileConfiguration config = SpigotCCM.instance().getConfig();
        checkForUpdate = config.getBoolean(Config.UPDATE_CHECK, false);
        if (config.get(Config.BANNED_BLOCK_BREAK, Config.ALL) instanceof String) {
            if (config.getString(Config.BANNED_BLOCK_BREAK, Config.ALL).equalsIgnoreCase(Config.ALL)) {
                addAllItems(bannedBlockBreak);
            }
        }
        else {
            addItems(bannedBlockBreak, config.getConfigurationSection(Config.BANNED_BLOCK_BREAK));
        }

        if (config.get(Config.BANNED_BLOCK_INV, Config.ALL) instanceof String) {
            if (config.getString(Config.BANNED_BLOCK_INV, Config.ALL).equalsIgnoreCase(Config.ALL)) {
                addAllItems(bannedBlockInventory);
            }
        }
        else {
            addItems(bannedBlockInventory, config.getConfigurationSection(Config.BANNED_BLOCK_INV));
        }

        if (config.get(Config.BANNED_BLOCK_PLACE, Config.ALL) instanceof String) {
            if (config.getString(Config.BANNED_ITEM_DROP, Config.ALL).equalsIgnoreCase(Config.ALL)) {
                addAllItems(bannedBlockPlace);
            }
        }
        else {
            addItems(bannedBlockPlace, config.getConfigurationSection(Config.BANNED_BLOCK_PLACE));
        }

        if (config.get(Config.BANNED_ENTITY_DAMAGE, Config.ALL) instanceof String) {
            if (config.getString(Config.BANNED_ENTITY_DAMAGE, Config.ALL).equalsIgnoreCase(Config.ALL)) {
                addAllEntities(bannedEntityDamage);
            }
        }
        else {
            addEntities(bannedEntityDamage, config.getStringList(Config.BANNED_ENTITY_DAMAGE));
        }

        if (config.get(Config.BANNED_ENTITY_INV, Config.ALL) instanceof String) {
            if (config.getString(Config.BANNED_ENTITY_INV, Config.ALL).equalsIgnoreCase(Config.ALL)) {
                addAllEntities(bannedEntityInventory);
            }
        }
        else {
            addEntities(bannedEntityInventory, config.getStringList(Config.BANNED_ENTITY_INV));
        }

        if (config.get(Config.BANNED_ENTITY_SPAWN, Config.ALL) instanceof String) {
            if (config.getString(Config.BANNED_ENTITY_SPAWN, Config.ALL).equalsIgnoreCase(Config.ALL)) {
                addAllEntities(bannedEntitySpawn);
            }
        }
        else {
            addEntities(bannedEntitySpawn, config.getStringList(Config.BANNED_ENTITY_SPAWN));
        }

        if (config.get(Config.BANNED_ITEM_DROP, Config.ALL) instanceof String) {
            if (config.getString(Config.BANNED_ITEM_DROP, Config.ALL).equalsIgnoreCase(Config.ALL)) {
                addAllItems(bannedItemDrop);
            }
        }
        else {
            addItems(bannedItemDrop, config.getConfigurationSection(Config.BANNED_ITEM_DROP));
        }

        if (config.get(Config.BANNED_RIGHT_CLICK, Config.ALL) instanceof String) {
            if (config.getString(Config.BANNED_RIGHT_CLICK, Config.ALL).equalsIgnoreCase(Config.ALL)) {
                addAllItems(bannedRightClick);
            }
        }
        else {
            addItems(bannedRightClick, config.getConfigurationSection(Config.BANNED_RIGHT_CLICK));
        }
    }
}
