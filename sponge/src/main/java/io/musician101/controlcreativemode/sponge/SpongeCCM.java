package io.musician101.controlcreativemode.sponge;

import io.musician101.common.java.minecraft.sponge.AbstractSpongePlugin;
import io.musician101.controlcreativemode.common.Reference;
import io.musician101.controlcreativemode.sponge.commands.CCMSpongeCommand;
import io.musician101.controlcreativemode.sponge.listener.SpongeCCMListener;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;

import javax.annotation.Nonnull;
import java.util.Optional;

public class SpongeCCM extends AbstractSpongePlugin<SpongeCCMConfig>
{
    @Listener
    @Override
    public void onServerStart(GameStartedServerEvent event)
    {
        config = new SpongeCCMConfig();
        Sponge.getEventManager().registerListeners(this, new SpongeCCMListener());
        Sponge.getCommandManager().register(this, new CCMSpongeCommand());
    }

    @Nonnull
    @Override
    public Optional<?> getInstance()
    {
        return Optional.of(Sponge.getPluginManager().fromInstance(this));
    }

    public static SpongeCCM instance()
    {
        //noinspection OptionalGetWithoutIsPresent
        return (SpongeCCM) Sponge.getPluginManager().getPlugin(Reference.ID).get();
    }
}
