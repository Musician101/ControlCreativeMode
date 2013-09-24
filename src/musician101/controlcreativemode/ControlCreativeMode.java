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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the plugin.
 * 
 * @author Musician101
 */
public class ControlCreativeMode extends JavaPlugin
{
	protected UpdateChecker updateChecker;
	FileConfiguration config;
	
	/** Loads the plugin's various configurations and reference files/folders. */
	public void loadConfiguration()
	{
		if (!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
	}
	
	/** Checks if new version is available. */
	public void versionCheck()
	{
		if (config.getBoolean("checkForUpdate"))
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
		else if (!config.getBoolean("checkForUpdate"))
			getLogger().info("Update checker is disabled.");
	}
	
	/** Initializes the plugin, checks for config, and register commands and listeners. */
	@Override
    public void onEnable()
	{
		/** Listener Registers */
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		/** Command Executors */
		getCommand(Constants.CCM).setExecutor(new CCMCommand(this));
		getCommand(Constants.CREATIVE).setExecutor(new CreativeCommand(this));
		getCommand(Constants.SURVIVAL).setExecutor(new SurvivalCommand(this));
		
		/** Check for config, create it if it's missing, and load it. */
		loadConfiguration();
		config = new YamlConfiguration();
		try
		{
			config.load(new File(getDataFolder() + "/config.yml"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }
	
	/** Shuts off the plugin. */
	@Override
	public void onDisable()
	{
        getLogger().info(Constants.PREFIX_PERMISSION + "Shutting down.");
    }
}
