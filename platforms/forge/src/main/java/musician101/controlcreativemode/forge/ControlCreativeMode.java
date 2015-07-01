package musician101.controlcreativemode.forge;

import musician101.controlcreativemode.forge.commands.CCMCommand;
import musician101.controlcreativemode.forge.lib.Constants;
import musician101.controlcreativemode.forge.lib.Constants.ModInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid=ModInfo.ID, name=ModInfo.NAME, version=ModInfo.VERSION)
public class ControlCreativeMode
{
	@Instance(value=ModInfo.ID)
	public static ControlCreativeMode instance;
	
	public static ConfigHandler config;
	public static Logger logger = LogManager.getLogger(ModInfo.NAME);
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
	{
		config = new ConfigHandler(event.getModConfigurationDirectory());
		FMLCommonHandler.instance().bus().register(config);
		//versionCheck();
		
		/*getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);*/
		
		getCommand(Constants.CCM_CMD).setExecutor(new CCMCommand(this));
    }
	
	/*private void versionCheck()
	{
		if (!config.checkForUpdate)
			getLogger().info("Update checker is disabled.");
		else if (config.checkForUpdate)
		{
			Updater updater = new Updater(this, 64447, this.getFile(), UpdateType.NO_DOWNLOAD, true);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE)
				getLogger().info("A new version is available. " + updater.getLatestName());
			else if (updater.getResult() == UpdateResult.NO_UPDATE)
				getLogger().info("The current version is the latest. " + updater.getLatestName());
			else
				getLogger().info("Error: Updater check failed.");
		}
	}*/
}
