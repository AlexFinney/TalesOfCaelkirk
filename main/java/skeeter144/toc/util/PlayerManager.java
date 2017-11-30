package skeeter144.toc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skeeter144.toc.player.TOCPlayer;

public class PlayerManager {

	static PlayerManager instance;
	
	Map<UUID, TOCPlayer> players;
	
	private PlayerManager() {
		players = new HashMap<UUID, TOCPlayer>();
	}
	
	
	//returns true if the player has played on the server before
	public boolean loadPlayerFromFile(UUID uuid, EntityPlayer player) {
		//TODO
		players.put(uuid, new TOCPlayer(player));
		return false;
	}
	
	public void savePlayerToFile(UUID uuid) {
		//TODO
	}
	
	public TOCPlayer getPlayer(UUID uuid) {
		return players.get(uuid);
	}
	
	public static PlayerManager instance() {
		if(instance == null)
			instance = new PlayerManager();
		
		return instance;
	}
	
}
