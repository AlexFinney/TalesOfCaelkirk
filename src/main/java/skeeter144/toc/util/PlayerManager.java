package skeeter144.toc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import skeeter144.toc.data.Database;
import skeeter144.toc.player.TOCPlayer;

public class PlayerManager {

	static PlayerManager instance;
	
	Map<UUID, TOCPlayer> players;
	
	private PlayerManager() {
		players = new HashMap<UUID, TOCPlayer>();
	}
	
	public boolean hasPlayerPreviouslyPlayed(EntityPlayer player) {
		return Database.playerExists(player);
	}
	
	public TOCPlayer getPlayer(EntityPlayer player) {
		TOCPlayer pl = players.get(player.getPersistentID());
		
		if(pl == null)  pl = Database.getPlayer(player);
			
		players.put(player.getPersistentID(), pl);
		return pl;
	}
	
	public static PlayerManager instance() {
		if(instance == null)
			instance = new PlayerManager();
		
		return instance;
	}
	
	public void savePlayers() {
		for(Map.Entry<UUID, TOCPlayer> entry : players.entrySet()) {
			Database.savePlayer(entry.getValue());
		}
	}
}
