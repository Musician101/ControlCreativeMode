package io.musician101.controlcreativemode.spigot;

import io.musician101.common.java.minecraft.spigot.AbstractSpigotPlugin;
import io.musician101.controlcreativemode.spigot.commands.CCMSpigotCommand;
import io.musician101.controlcreativemode.spigot.listener.SpigotCCMListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class SpigotCCM extends AbstractSpigotPlugin<SpigotCCMConfig>
{
    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        config = new SpigotCCMConfig();
        versionCheck(64447);
        getServer().getPluginManager().registerEvents(new SpigotCCMListener(), this);
        commands = Collections.singletonList(new CCMSpigotCommand());
    }

    public static SpigotCCM instance()
    {
        return JavaPlugin.getPlugin(SpigotCCM.class);
    }
}
