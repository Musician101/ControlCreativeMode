package io.musician101.controlcreativemode.sponge;

import io.musician101.controlcreativemode.common.Reference;
import io.musician101.controlcreativemode.sponge.commands.SpongeCCMCommands;
import io.musician101.controlcreativemode.sponge.listener.SpongeCCMListener;
import io.musician101.musicianlibrary.java.minecraft.sponge.plugin.AbstractSpongePlugin;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION, authors = {"Musician101"}, dependencies = {@Dependency(id = "io.musician101.common.java.minecraft.sponge", version = "3.0-SNAPSHOT")}, description = "Control what players do when they're in Creative Mode.")
public class SpongeCCM extends AbstractSpongePlugin<SpongeCCMConfig> {

    @Inject
    private PluginContainer pluginContainer;

    public static Optional<SpongeCCM> instance() {
        return Sponge.getPluginManager().getPlugin(Reference.ID).flatMap(PluginContainer::getInstance).filter(SpongeCCM.class::isInstance).map(SpongeCCM.class::cast);
    }

    @Nonnull
    @Override
    public PluginContainer getPluginContainer() {
        return pluginContainer;
    }

    @Listener
    public void preInit(GamePreInitializationEvent event) {
        config = new SpongeCCMConfig();
        Sponge.getEventManager().registerListeners(this, new SpongeCCMListener());
        Sponge.getCommandManager().register(this, SpongeCCMCommands.ccm());
    }
}
