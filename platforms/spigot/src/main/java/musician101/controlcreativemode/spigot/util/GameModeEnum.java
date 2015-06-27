package musician101.controlcreativemode.spigot.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;

public enum GameModeEnum
{
	ADVENTURE(GameMode.ADVENTURE, Arrays.asList("adventure", "a")),
	CREATIVE(GameMode.CREATIVE, Arrays.asList("creative", "c")),
	SPECTATOR(GameMode.SPECTATOR, Arrays.asList("spectator", "sp")),
	SURVIVAL(GameMode.SURVIVAL, Arrays.asList("survival", "s"));
	
	GameMode gamemode;
	List<String> aliases;
	
	private GameModeEnum(GameMode gamemode, List<String> aliases)
	{
		this.gamemode = gamemode;
		this.aliases = aliases;
	}
	
	public GameMode getGameMode()
	{
		return gamemode;
	}
	
	public List<String> getAliases()
	{
		return aliases;
	}
	
	public static GameMode getGameMode(String gamemode)
	{
		for (GameModeEnum gme : values())
			for (String alias : gme.getAliases())
				if (alias.equalsIgnoreCase(gamemode))
					gme.getGameMode();
		
		return null;
	}
}
