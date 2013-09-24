package musician101.controlcreativemode;

import java.io.File;

import musician101.controlcreativemode.commands.CCMCommand;
import musician101.controlcreativemode.commands.CreativeCommand;
import musician101.controlcreativemode.commands.SurvivalCommand;
import musician101.controlcreativemode.lib.Constants;
import musician101.controlcreativemode.listeners.BlockListener;
import musician101.controlcreativemode.listeners.EntityListener;
import musician101.controlcreativemode.listeners.PlayerListener;
import musician101.controlcreativemode.util.UpdateChecker;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the plugin.
 * 
 * @author Musician101
 */
public class ControlCreativeMode extends JavaPlugin
{
	protected UpdateChecker updateChecker;
	Config config;
	
	/** Loads the plugin's various configurations and reference files/folders. */
	public void loadConfiguration()
	{
		if (!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
	}
	
	/** Checks if new version is available. */
	public void versionCheck()
	{
		if (config.checkForUpdate)
		{
			updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/bukkit-plugins/control-creative-mode/files.rss");
			getLogger().info("Update checker is enabled.");
			if (updateChecker.updateNeeded())
			{
				getLogger().info("A new version is available: " + updateChecker.getVersion());
				getLogger().info("Get it from: " + updateChecker.getLink());
			}
			else
				getLogger().info("CCM is up to date.");
		}
		else if (!config.checkForUpdate)
			getLogger().info("Update checker is disabled.");
	}
	
	/** Initializes the plugin, checks for config, and register commands and listeners. */
	@Override
    public void onEnable()
	{
		loadConfiguration();
		config = new Config(this);
		
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		getCommand(Constants.CCM).setExecutor(new CCMCommand(this));
		getCommand(Constants.CREATIVE).setExecutor(new CreativeCommand(this));
		getCommand(Constants.SURVIVAL).setExecutor(new SurvivalCommand(this));
		
		versionCheck();
    }
	
	/** Shuts off the plugin. */
	@Override
	public void onDisable()
	{
        getLogger().info("Shutting down.");
    }
}
