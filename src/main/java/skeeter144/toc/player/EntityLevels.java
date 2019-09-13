package skeeter144.toc.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityLevels implements Serializable{
	
	public static int HP_PER_LEVEL = 10;
	public static float HP_REGEN_PER_HP_LEVEL = .3f;
	public static float HP_REGEN_PER_STRENGTH_LEVEL = .2f;
	public static int MANA_PER_LEVEL = 7;
	public static float MANA_REGEN_PER_MAGIC_LEVEL = 1f;
	public static float STRENGTH_MULT_PER_LEVEL = .03f;
	
	LinkedHashMap<Levels, Level> levelsMap;
	
	public EntityLevels() {
		levelsMap = new LinkedHashMap<Levels, Level>();
		
		levelsMap.put(Levels.ATTACK, new Level("Attack"));
		levelsMap.put(Levels.STRENGTH, new Level("Strength"));
		levelsMap.put(Levels.DEFENSE, new Level("Defense"));
		levelsMap.put(Levels.HITPOINTS, new Level("Hitpoints"));
		levelsMap.put(Levels.MAGIC, new Level("Magic"));
		levelsMap.put(Levels.RANGED, new Level("Ranged"));
		levelsMap.put(Levels.MINING, new Level("Mining"));
		levelsMap.put(Levels.SMITHING, new Level("Smithing"));
		levelsMap.put(Levels.FISHING, new Level("Fishing"));
		levelsMap.put(Levels.WOODCUTTING, new Level("Woodcutting"));
		levelsMap.put(Levels.CRAFTING, new Level("Crafting"));
		
		initBooks();
	}
	
	public EntityLevels(ArrayList<Integer> levelsXp) {
		levelsMap = new LinkedHashMap<Levels, Level>();
		
		levelsMap.put(Levels.ATTACK, new Level("Attack", levelsXp.get((0))));
		levelsMap.put(Levels.STRENGTH, new Level("Strength", levelsXp.get((1))));
		levelsMap.put(Levels.DEFENSE, new Level("Defense", levelsXp.get((2))));
		levelsMap.put(Levels.MAGIC, new Level("Magic", levelsXp.get((3))));
		levelsMap.put(Levels.WOODCUTTING, new Level("Woodcutting", levelsXp.get((4))));
		levelsMap.put(Levels.MINING, new Level("Mining", levelsXp.get((5))));
		levelsMap.put(Levels.SMITHING, new Level("Smithing", levelsXp.get((6))));
		levelsMap.put(Levels.CRAFTING, new Level("Crafting", levelsXp.get((7))));
		levelsMap.put(Levels.HITPOINTS, new Level("Hitpoints", levelsXp.get((8))));
		levelsMap.put(Levels.FISHING, new Level("Fishing", levelsXp.get((9))));
		levelsMap.put(Levels.RANGED, new Level("Ranged", levelsXp.get((10))));
		
		initBooks();
	}
	
	private void initBooks() {

	}

	public boolean addExp(Levels level, int amt) {
		Level l = levelsMap.get(level);
		return l.addExp(amt);
	}
	
	//only called on mobs for their creation. They dont have exp
	public void setLevelFor(Levels l, int level) {
		levelsMap.get(l).setXp(getExpForLevel(level));
	}
	
	public int getLevelFor(Levels l) {
		return levelsMap.get(l).getLevel();
	}
	
	//index 0 is starting exp
	static int[] expForLevels = new int[100];
	
	public static void init() {
		for(int i= 1; i < 100; ++i) {
			int sum = 0;
			for(int j = 1; j < i; j++) {
				sum = (int)    (j + 250 * Math.pow(2, ((float)j / 8.0f))) / 4   ;
			}
			expForLevels[i] = sum + expForLevels[i-1];
		}
		
	}
	
	public static int getExpForLevel(int level)	{
		return expForLevels[level];
	}
	
	public static int getLevelForExp(int exp) {
		int highestLevel = 0;
		for(int i = 1; i < 100; ++i) {
			if(exp >= expForLevels[i]) {
				highestLevel = i;
			}else {
				return highestLevel;
			}
		}
		return -1;
	}
	
	public Collection<Level> getLevels(){
		return levelsMap.values();
	}
	
	public Level getLevel(Levels level) {
		return levelsMap.get(level);
	}
	
	public enum Levels{
		MAGIC,
		ATTACK,
		STRENGTH,
		DEFENSE,
		HITPOINTS, 
		MINING,
		SMITHING,
		FISHING,
		WOODCUTTING,
		CRAFTING, 
		RANGED
	}
}
