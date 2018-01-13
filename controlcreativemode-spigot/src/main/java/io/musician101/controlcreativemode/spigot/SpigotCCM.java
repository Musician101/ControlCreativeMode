package io.musician101.controlcreativemode.spigot;

import io.musician101.controlcreativemode.spigot.commands.SpigotCCMCommands;
import io.musician101.controlcreativemode.spigot.listener.SpigotCCMListener;
import io.musician101.musicianlibrary.java.minecraft.spigot.plugin.AbstractSpigotPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCCM extends AbstractSpigotPlugin<SpigotCCMConfig, SpigotCCM>
{
    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        config = new SpigotCCMConfig();
        getServer().getPluginManager().registerEvents(new SpigotCCMListener(), this);
        commands.add(SpigotCCMCommands.ccm());
    }

    public static SpigotCCM instance()
    {
        return JavaPlugin.getPlugin(SpigotCCM.class);
    }
}
