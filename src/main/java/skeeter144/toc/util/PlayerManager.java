package skeeter144.toc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.player.EntityPlayer;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.TOCPlayer;

public class PlayerManager {

	static PlayerManager instance;
	
	Map<UUID, TOCPlayer> players;
	
	private PlayerManager() {
		players = new HashMap<UUID, TOCPlayer>();
	}
	
	
	//returns true if the player has played on the server before
	public boolean hasPlayerPreviouslyPlayed(UUID uuid) {
		return new File("players\\" + uuid.toString()).exists();
	}
	
	@SuppressWarnings("unchecked")
	public TOCPlayer getPlayer(EntityPlayer player) {
		TOCPlayer pl = players.get(player.getPersistentID());
		File playerFile = new File("players\\" + player.getPersistentID().toString());
		
		if(pl == null) {
			if(!playerFile.exists()) {
				pl = new TOCPlayer(player);
				savePlayer(pl, player.getPersistentID());
				return pl;
			}
		
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(playerFile));
				
				pl = new TOCPlayer(player);
				pl.levels = (EntityLevels)ois.readObject();
				pl.setHealthAndMana(ois.readInt(), ois.readInt());

				pl.specialAttackCooldowns = (HashMap<String, Pair<Integer, Integer>>) ois.readObject();
				
				
				ois.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		
		}
			
		players.put(player.getPersistentID(), pl);
		
		return pl;
	}
	
	public static PlayerManager instance() {
		if(instance == null)
			instance = new PlayerManager();
		
		return instance;
	}
	
	public void savePlayers() {
		new File("players").mkdirs();
		for(Map.Entry<UUID, TOCPlayer> entry : players.entrySet()) {
			savePlayer(entry.getValue(), entry.getKey());
		}
	}
	
	void savePlayer(TOCPlayer pl, UUID uuid) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("players\\" + uuid.toString()));
			
			oos.writeObject(pl.levels);
			
			oos.writeInt(pl.getHealth());
			oos.writeInt(pl.getMana());
			oos.writeObject(pl.specialAttackCooldowns);
			
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
