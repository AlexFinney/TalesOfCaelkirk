package skeeter144.toc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import skeeter144.toc.data.Database;
import skeeter144.toc.player.TOCPlayer;

public class PlayerManager {

	static PlayerManager instance;
	
	Map<UUID, TOCPlayer> players;
	
	private PlayerManager() {
		players = new HashMap<UUID, TOCPlayer>();
	}
	
	public boolean hasPlayerPreviouslyPlayed(PlayerEntity player) {
		return Database.playerExists(player);
	}
	
	public TOCPlayer getPlayer(PlayerEntity player) {
		TOCPlayer pl = players.get(player.getUniqueID());
		if(pl == null) { 
			pl = Database.getPlayer(player); 
			players.put(player.getUniqueID(), pl);
		}
		return pl;
	}
	
	public static PlayerManager instance() {
		if(instance == null)
			instance = new PlayerManager();
		
		return instance;
	}
	
	public void addPlayer(TOCPlayer player) {
		players.put(player.mcEntity.getUniqueID(), player);
	}
	
	public void savePlayer(TOCPlayer player) {
		Database.savePlayer(player);
	}
	
	public void savePlayers() {
		for(Map.Entry<UUID, TOCPlayer> entry : players.entrySet()) {
			Database.savePlayer(entry.getValue());
		}
	}
}
