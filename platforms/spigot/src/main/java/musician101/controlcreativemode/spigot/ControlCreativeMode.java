package musician101.controlcreativemode.spigot;

import musician101.controlcreativemode.spigot.commands.CCMCommand;
import musician101.controlcreativemode.spigot.lib.Constants;
import musician101.controlcreativemode.spigot.listeners.BlockListener;
import musician101.controlcreativemode.spigot.listeners.EntityListener;
import musician101.controlcreativemode.spigot.listeners.PlayerListener;
import musician101.controlcreativemode.spigot.util.Updater;
import musician101.controlcreativemode.spigot.util.Updater.UpdateResult;
import musician101.controlcreativemode.spigot.util.Updater.UpdateType;

import org.bukkit.plugin.java.JavaPlugin;

public class ControlCreativeMode extends JavaPlugin
{
	public SpigotConfig config;
	
	private void versionCheck()
	{
		if (!config.checkForUpdate())
		{
			getLogger().info("Update checker is disabled.");
			return;
		}
		
		Updater updater = new Updater(this, 64447, this.getFile(), UpdateType.NO_DOWNLOAD, true);
		if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE)
			getLogger().info("A new version is available. " + updater.getLatestName());
		else if (updater.getResult() == UpdateResult.NO_UPDATE)
			getLogger().info("The current version is the latest. " + updater.getLatestName());
		else
			getLogger().info("Error: Updater check failed.");
	}
	
	@Override
    public void onEnable()
	{
		saveDefaultConfig();
		config = new SpigotConfig(this);
		versionCheck();
		
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		getCommand(Constants.CCM_CMD).setExecutor(new CCMCommand(this));
    }
}
