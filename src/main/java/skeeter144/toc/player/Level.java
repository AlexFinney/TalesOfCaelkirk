package skeeter144.toc.player;

import java.io.Serializable;

import net.minecraft.item.ItemBook;
import net.minecraft.util.ResourceLocation;

public class Level implements Serializable{
	int curXp = 0;
	int level = 1;
	public final String name;
	public ItemBook infoBook;
	
	public final String iconName;
	
	public Level(String name) {
		this.name = name;
		iconName =  "toc:textures/icons/levels/" + name.toLowerCase() + "_icon.png";
	}
	
	public Level(String name, int xp) {
		this.name = name;
		iconName =  "toc:textures/icons/levels/" + name.toLowerCase() + "_icon.png";
		setXp(xp);
	}
	
	
	public String getName() {
		return name;
	}
	
	public boolean addExp(int amt){
		curXp += amt;
		boolean levelUp = curXp >= EntityLevels.getExpForLevel(level + 1);
		if(levelUp)
			++level;
		
		return levelUp;
	}
	
	public void setXp(int xp) {
		this.curXp = xp;
		this.level = EntityLevels.getLevelForExp(curXp);
	}
	
	public int getLevel() {
		return EntityLevels.getLevelForExp(curXp);
	}
	
	public int getXp() {
		return curXp;
	}
}
