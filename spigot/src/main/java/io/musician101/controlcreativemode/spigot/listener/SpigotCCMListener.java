package io.musician101.controlcreativemode.spigot.listener;

import io.musician101.controlcreativemode.common.CCMListener;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.spigot.SpigotCCM;
import io.musician101.controlcreativemode.spigot.SpigotCCMConfig;
import io.musician101.controlcreativemode.spigot.event.PlayerUseItemStackEvent;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class SpigotCCMListener implements CCMListener<BlockBreakEvent, PlayerInteractEvent, BlockPlaceEvent, EntityDamageByEntityEvent, PlayerDropItemEvent, PlayerInteractEntityEvent, PlayerGameModeChangeEvent, PlayerUseItemStackEvent, ProjectileLaunchEvent>, Listener
{
    public SpigotCCMListener()
    {
        super();
    }

    @EventHandler
    @Override
    public void blockBreak(BlockBreakEvent event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        Block block = event.getBlock();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isBlockBreakBanned(toItemStack(block));
        if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_BREAK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        //noinspection deprecation
        warnStaff(Messages.playerBrokeBlock(player.getName(), block.getType().toString(),
                Byte.toString(block.getData()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));//NOSONAR
    }

    @EventHandler
    @Override
    public void blockInteract(PlayerInteractEvent event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        if (event.getHand() != EquipmentSlot.HAND)
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        Block block = event.getClickedBlock();
        ItemStack itemStack = event.getItem();
        if (block.getType() == Material.AIR && itemStack != null && !SpigotCCM.instance().getPluginConfig().isRightClickBanned(itemStack))
        {
            Bukkit.getPluginManager().callEvent(new PlayerUseItemStackEvent(itemStack, player));
            return;
        }

        boolean isBanned = SpigotCCM.instance().getPluginConfig().isBlockInventoryBanned(toItemStack(block));
        if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_INVENTORY))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        //noinspection deprecation
        warnStaff(Messages.playerAccessedBlockInventory(player.getName(), block.getType().toString(),
                Byte.toString(block.getData()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));//NOSONAR
    }

    @EventHandler
    @Override
    public void blockPlace(BlockPlaceEvent event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        Block block = event.getBlock();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isBlockPlaceBanned(toItemStack(block));
        if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_PLACE))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        //noinspection deprecation
        warnStaff(Messages.playerPlacedBlock(player.getName(), block.getType().toString(),
                Byte.toString(block.getData()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));//NOSONAR
    }

    @EventHandler
    @Override
    public void damageEntity(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player))
            return;

        Entity entity = event.getEntity();
        Player player = (Player) event.getDamager();
        if (player.getUniqueId() == entity.getUniqueId() || player.getGameMode() != GameMode.CREATIVE)
            return;

        EntityType entityType = entity.getType();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isEntityDamageBanned(entityType);
        if (!hasPermission(isBanned, player, Permissions.ALLOW_ATTACK))
        {
            event.setCancelled(true);
            return;
        }

        Location location = player.getLocation();
        warnStaff(Messages.playerAttackedEntity(player.getName(), entity.getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void dropItem(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        ItemStack item = event.getItemDrop().getItemStack();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isItemDropBanned(item);
        if (isBanned && !player.hasPermission(Permissions.ALLOW_ITEM_DROP))
        {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        warnStaff(Messages.playerDroppedItem(player.getName(), item.getType().toString(), Short.toString(item.getDurability()), item.getAmount(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void entityInteract(PlayerInteractEntityEvent event)
    {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        boolean isBanned = SpigotCCM.instance().getPluginConfig().isEntityInventoryBanned(entity.getType());
        if (isBanned && !player.hasPermission(Permissions.ALLOW_ENTITY_INVENTORY))
        {
            event.setCancelled(true);
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = entity.getLocation();
        warnStaff(Messages.playerAccessedEntityInventory(player.getName(), entity.toString(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void gameModeChange(PlayerGameModeChangeEvent event)
    {
        if (!isInventoryEmpty(event.getPlayer()))
            warnStaff(Messages.playerChangeGameMode(event.getPlayer().getName(), event.getNewGameMode().toString()));
    }

    private boolean isInventoryEmpty(Player player)
    {
        for (ItemStack item : player.getInventory().getContents())
            if (item != null)
                return false;

        for (ItemStack item : player.getInventory().getArmorContents())
            if (item != null)
                return false;

        return true;
    }

    @EventHandler
    @Override
    public void useItem(PlayerUseItemStackEvent event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        boolean isBanned;
        ItemStack itemStack = event.getItem();
        SpigotCCMConfig config = SpigotCCM.instance().getPluginConfig();
        if (itemStack.getType() == Material.MONSTER_EGG)
        {
            EntityType entityType = getEntityTypeFromEgg(itemStack);
            isBanned = config.isEntitySpawnBanned(entityType);
            if (!hasPermission(isBanned, player, Permissions.ALLOW_ENTITY_SPAWN))
            {
                event.setCancelled(true);
                return;
            }

            if (!isBanned)
                return;

            Location location = player.getLocation();
            warnStaff(Messages.playerSpawnedEntity(player.getName(), entityType.toString(),
                    location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            return;
        }

        isBanned = config.isRightClickBanned(itemStack);
        if (!hasPermission(isBanned, player, Permissions.ALLOW_RIGHT_CLICK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        warnStaff(Messages.playerRightItemClick(player.getName(), itemStack.getType().toString(),
                Short.toString(itemStack.getDurability()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    private EntityType getEntityTypeFromEgg(ItemStack itemStack)
    {
        net.minecraft.server.v1_10_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = stack.getTag();
        if (tagCompound == null || !tagCompound.hasKey("EntityTag"))
            return EntityType.UNKNOWN;

        @SuppressWarnings("deprecation") EntityType entityType = EntityType.fromName(tagCompound.getCompound("EntityTag")//NOSONAR
                .getString("id"));
        if (entityType == null)
            return EntityType.UNKNOWN;
        else
            return entityType;
    }

    @Override
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Projectile entity = event.getEntity();
        if (!(entity.getShooter() instanceof Player))
            return;

        Player player = (Player) entity.getShooter();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isRightClickBanned(itemStack);
        if (!hasPermission(isBanned, player, Permissions.ALLOW_RIGHT_CLICK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        warnStaff(Messages.playerRightItemClick(player.getName(), itemStack.getType().toString(),
                Short.toString(itemStack.getDurability()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
    {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        ItemStack bucket = new ItemStack(event.getBucket());
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isRightClickBanned(bucket);
        if (isBanned && !player.hasPermission(Permissions.ALLOW_BLOCK_PLACE))
        {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        warnStaff(Messages.playerPlacedBlock(player.getName(), bucket.getType().toString(), Short.toString(bucket.getDurability()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean hasPermission(boolean isBanned, Player player, String permission)
    {
        if (!isBanned || player.hasPermission(permission))
            return true;

        player.sendMessage(ChatColor.RED + Messages.NO_PERMISSION);
        return false;
    }

    // Apparently certain blocks have damage values based on which direction they're facing.
    // This sets the proper durability.
    private ItemStack toItemStack(Block block)//NOSONAR
    {
        if (block == null)
            return new ItemStack(Material.AIR);

        List<Material> materials = Arrays.asList(Material.STONE, Material.DIRT, Material.WOOD, Material.SAPLING,
                Material.SAND, Material.LOG, Material.LEAVES, Material.SPONGE, Material.SANDSTONE, Material.LONG_GRASS,
                Material.WOOL, Material.RED_ROSE, Material.DOUBLE_STEP, Material.STEP, Material.STAINED_GLASS,
                Material.MONSTER_EGGS, Material.SMOOTH_BRICK, Material.WOOD_DOUBLE_STEP, Material.WOOD_STEP,
                Material.COBBLE_WALL, Material.QUARTZ_BLOCK, Material.STAINED_CLAY, Material.STAINED_GLASS_PANE,
                Material.LEAVES_2, Material.LOG_2, Material.PRISMARINE, Material.CARPET, Material.DOUBLE_PLANT,
                Material.RED_SANDSTONE, Material.COAL, Material.GOLDEN_APPLE, Material.RAW_FISH, Material.INK_SACK,
                Material.MONSTER_EGG, Material.SKULL_ITEM);

        ItemStack item = block.getState().getData().toItemStack();
        if (!materials.contains(item.getType()))
            item.setDurability((short) 0);

        // As an item it has 3 durabilities (0-2).
        // As a block it has 6 durabilities (1-2,5-6,9-10).
        if (item.getType() == Material.ANVIL)
        {
            if (item.getDurability() == 1 || item.getDurability() == 2)
                item.setDurability((short) 0);
            else if (item.getDurability() == 3 || item.getDurability() == 4)
                item.setDurability((short) 1);
            else if (item.getDurability() == 9 || item.getDurability() == 10)
                item.setDurability((short) 2);
        }

        return item;
    }

    private void warnStaff(String message)
    {
        SpigotCCM.instance().getLogger().warning(message);
        Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.hasPermission(Permissions.WARNING)).forEach(player -> player.sendMessage(ChatColor.GOLD + Messages.PREFIX + message));
    }
}
