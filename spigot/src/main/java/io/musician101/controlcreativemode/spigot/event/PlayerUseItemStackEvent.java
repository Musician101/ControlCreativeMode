package io.musician101.controlcreativemode.spigot.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerUseItemStackEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled = false;
    private final ItemStack itemStack;

    public PlayerUseItemStackEvent(ItemStack itemStack, Player player)
    {
        super(player);
        this.itemStack = itemStack;
    }

    @Override
    public boolean isCancelled()
    {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled)
    {
        this.isCancelled = isCancelled;
    }

    public ItemStack getItem()
    {
        return itemStack;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
