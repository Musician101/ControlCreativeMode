package musician101.controlcreativemode.spigot;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.controlcreativemode.common.Reference.Messages;
import musician101.controlcreativemode.spigot.util.Updater;
import musician101.controlcreativemode.spigot.util.Updater.UpdateResult;
import musician101.controlcreativemode.spigot.util.Updater.UpdateType;
import musician101.controlcreativemode.spigot.commands.CCMSpigotCommand;
import musician101.controlcreativemode.spigot.listener.SpigotCCMListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class SpigotCCM extends JavaPlugin
{
	private SpigotCCMConfig config;
	private List<AbstractSpigotCommand<SpigotCCM>> commands;
	
	private void versionCheck()
	{
		if (!config.checkForUpdate())
		{
			getLogger().info(Messages.UPDATER_DISABLED);
			return;
		}
		
		Updater updater = new Updater(this, 64447, this.getFile(), UpdateType.NO_DOWNLOAD, true);
		if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE)
			getLogger().info(Messages.newVersionAvailable(updater.getLatestName()));
		else if (updater.getResult() == UpdateResult.NO_UPDATE)
			getLogger().info(Messages.noNewVersionAvailable(updater.getLatestName()));
		else
			getLogger().info(Messages.UPDATER_FAILED);
	}
	
	@Override
    public void onEnable()
	{
		saveDefaultConfig();
		config = new SpigotCCMConfig(this);
		versionCheck();
		getServer().getPluginManager().registerEvents(new SpigotCCMListener(this), this);
		commands = Collections.singletonList(new CCMSpigotCommand(this));
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		for (AbstractSpigotCommand<SpigotCCM> cmd : commands)
			if (command.getName().equalsIgnoreCase(cmd.getName()))
				return cmd.onCommand(sender, args);
		
		return false;
	}
	
	public List<AbstractSpigotCommand<SpigotCCM>> getCommands()
	{
		return commands;
	}

	public SpigotCCMConfig getPluginConfig()
    {
        return config;
    }
}
