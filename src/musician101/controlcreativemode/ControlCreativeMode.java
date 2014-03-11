package musician101.controlcreativemode;

import musician101.controlcreativemode.commands.CCMCommand;
import musician101.controlcreativemode.commands.CreativeCommand;
import musician101.controlcreativemode.commands.SurvivalCommand;
import musician101.controlcreativemode.lib.Commands;
import musician101.controlcreativemode.listeners.BlockListener;
import musician101.controlcreativemode.listeners.EntityListener;
import musician101.controlcreativemode.listeners.PlayerListener;
import musician101.controlcreativemode.util.Updater;
import musician101.controlcreativemode.util.Updater.UpdateResult;
import musician101.controlcreativemode.util.Updater.UpdateType;

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
		if (!config.checkForUpdate)
			getLogger().info("Update checker is disabled.");
		else if (config.checkForUpdate)
		{
			Updater updater = new Updater(this, 64447, this.getFile(), UpdateType.NO_DOWNLOAD, true);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE)
				getLogger().info("A new version is available." + updater.getLatestName());
			else if (updater.getResult() == UpdateResult.NO_UPDATE)
				getLogger().info("The current version is the latest." + updater.getLatestName());
			else
				getLogger().info("Error: Updater check failed.");
		}
			
	}
	
	/** Initializes the plugin, checks for config, and register commands and listeners. */
	@Override
    public void onEnable()
	{
		config = new Config(this);
		versionCheck();
		
		getServer().getPluginManager().registerEvents(new BlockListener(this, config), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this, config), this);
		
		getCommand(Commands.CCM_CMD).setExecutor(new CCMCommand(this));
		getCommand(Commands.CREATIVE_CMD).setExecutor(new CreativeCommand(this));
		getCommand(Commands.SURVIVAL_CMD).setExecutor(new SurvivalCommand(this));
    }
	
	/** Shuts off the plugin. */
	@Override
	public void onDisable()
	{
        getLogger().info("Shutting down.");
    }
}
