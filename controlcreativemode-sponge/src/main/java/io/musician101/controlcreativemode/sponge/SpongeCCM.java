package io.musician101.controlcreativemode.sponge;

import io.musician101.controlcreativemode.common.Reference;
import io.musician101.controlcreativemode.sponge.commands.CCMSpongeCommand;
import io.musician101.controlcreativemode.sponge.listener.SpongeCCMListener;
import io.musician101.musicianlibrary.java.minecraft.sponge.AbstractSpongePlugin;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION, authors = {"Musician101"},
        dependencies = {@Dependency(id = "io.musician101.common.java.minecraft.sponge", version = "3.0-SNAPSHOT")},
        description = "Control what players do when they're in Creative Mode.")
public class SpongeCCM extends AbstractSpongePlugin<SpongeCCMConfig>
{
    @Listener
    public void preInit(GamePreInitializationEvent event)
    {
        config = new SpongeCCMConfig();
        Sponge.getEventManager().registerListeners(this, new SpongeCCMListener());
        Sponge.getCommandManager().register(this, new CCMSpongeCommand());
    }

    public static SpongeCCM instance()
    {
        //noinspection OptionalGetWithoutIsPresent
        return (SpongeCCM) getPluginContainer().getInstance().get();//NOSONAR
    }

    public static PluginContainer getPluginContainer()
    {
        //noinspection OptionalGetWithoutIsPresent
        return Sponge.getPluginManager().getPlugin(Reference.ID).get();//NOSONAR
    }

    @Override
    public Logger getLogger()
    {
        return getPluginContainer().getLogger();
    }
}
