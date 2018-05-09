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
	
	public TOCPlayer getPlayer(EntityPlayer player) {
		TOCPlayer pl = players.get(player.getPersistentID());
		
		if(pl == null) {
			pl = new TOCPlayer(player);
			players.put(pl.mcEntity.getPersistentID(), pl);
			savePlayers();
		}
		return pl;
	}
	
	public static PlayerManager instance() {
		if(instance == null)
			instance = new PlayerManager();
		
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public void loadPlayer(EntityPlayer player) {
		if(players.containsKey(player.getPersistentID()))
			return;
		
		try {
			File playerFile = new File("players\\" + player.getPersistentID());
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(playerFile));
			
			TOCPlayer pl = new TOCPlayer(player);
			pl.levels = (EntityLevels)ois.readObject();
			pl.setHealthAndMana(ois.readInt(), ois.readInt());

			pl.specialAttackCooldowns = (HashMap<String, Pair<Integer, Integer>>) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void savePlayers() {
		new File("players").mkdirs();
		for(Map.Entry<UUID, TOCPlayer> entry : players.entrySet()) {
			
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("players\\" + entry.getKey().toString()));
				
				TOCPlayer pl = entry.getValue();
				
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
	
}
