package io.musician101.controlcreativemode.sponge;

import io.musician101.common.java.minecraft.sponge.AbstractSpongePlugin;
import io.musician101.controlcreativemode.common.Reference;
import io.musician101.controlcreativemode.sponge.commands.CCMSpongeCommand;
import io.musician101.controlcreativemode.sponge.listener.SpongeCCMListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Optional;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION, authors = {"Musician101"}, description = "Control what players do when they're in Creative Mode.")
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

    public static SpongeCCM instance()
    {
        return (SpongeCCM) getPluginContainer();
    }

    public static PluginContainer getPluginContainer()
    {
        Optional<PluginContainer> plugin = Sponge.getPluginManager().getPlugin(Reference.ID);
        if (plugin.isPresent())
            return plugin.get();

        return null;
    }

    @Override
    public Logger getLogger()
    {
        if (getPluginContainer() == null)
            return LoggerFactory.getLogger(Reference.ID);

        return getPluginContainer().getLogger();
    }
}
