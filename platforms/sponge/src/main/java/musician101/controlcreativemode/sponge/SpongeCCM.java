package musician101.controlcreativemode.sponge;

import musician101.controlcreativemode.common.Reference;
import musician101.controlcreativemode.sponge.commands.CCMSpongeCommand;
import musician101.controlcreativemode.sponge.listener.SpongeCCMListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION)
public class SpongeCCM
{
    private Logger logger;
    private SpongeCCMConfig config;
    private static SpongeCCM instance;

	@Listener
    public void preInit(GameStartedServerEvent event)
	{
		config = new SpongeCCMConfig();
        logger = LoggerFactory.getLogger(Reference.ID);
        EventManager ev = Sponge.getEventManager();
		ev.registerListeners(this, new SpongeCCMListener());
		Sponge.getCommandManager().register(this, new CCMSpongeCommand());
        instance = this;
    }

	public Logger getLogger()
    {
        return logger;
    }

    public SpongeCCMConfig getPluginConfig()
    {
        return config;
    }

    public static SpongeCCM instance()
    {
        return instance;
    }
}
