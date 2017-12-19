package skeeter144.toc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import skeeter144.toc.entity.TOCEntity;
import skeeter144.toc.player.TOCPlayer;

public class MobManager {

	static MobManager instance;
	
	Map<UUID, TOCEntity> entites;
	
	private MobManager() {
		entites = new HashMap<UUID, TOCEntity>();
	}
	
	public void addMob(UUID uuid, TOCEntity e) {
		entites.put(uuid, e);
	}
	
	public void removeMob(UUID uuid) {
		entites.remove(uuid);
	}
	
	public TOCEntity getMob(UUID uuid) {
		return entites.get(uuid);
	}
	
	public static MobManager instance() {
		if(instance == null)
			instance = new MobManager();
		
		return instance;
	}
	
}
