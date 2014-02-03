package musician101.controlcreativemode;

import musician101.controlcreativemode.commands.CCMCommand;
import musician101.controlcreativemode.commands.CreativeCommand;
import musician101.controlcreativemode.commands.SurvivalCommand;
import musician101.controlcreativemode.lib.Commands;
import musician101.controlcreativemode.listeners.BlockListener;
import musician101.controlcreativemode.listeners.EntityListener;
import musician101.controlcreativemode.listeners.PlayerListener;
import musician101.controlcreativemode.util.Update;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the plugin.
 * 
 * @author Musician101
 */
public class ControlCreativeMode extends JavaPlugin
{
	Config config;
	
	/** Checks if new version is available. */
	public void versionCheck()
	{
		if (config.checkForUpdate)
		{
			@SuppressWarnings("unused")
			Update update = new Update(64447, "72784c134bdbc3c2216591011a29df99fac08239");
		}
		else if (!config.checkForUpdate)
			getLogger().info("Update checker is disabled.");
	}
	
	/** Initializes the plugin, checks for config, and register commands and listeners. */
	@Override
    public void onEnable()
	{
		config = new Config(this);
		
		getServer().getPluginManager().registerEvents(new BlockListener(this, config), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this, config), this);
		
		getCommand(Commands.CCM_CMD).setExecutor(new CCMCommand(this));
		getCommand(Commands.CREATIVE_CMD).setExecutor(new CreativeCommand(this));
		getCommand(Commands.SURVIVAL_CMD).setExecutor(new SurvivalCommand(this));
		
		versionCheck();
    }
	
	/** Shuts off the plugin. */
	@Override
	public void onDisable()
	{
        getLogger().info("Shutting down.");
    }
}
