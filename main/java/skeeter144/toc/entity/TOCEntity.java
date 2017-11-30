package skeeter144.toc.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import skeeter144.toc.player.EntityLevels;

public class TOCEntity{
	public final Entity mcEntity;
	public final EntityLevels levels;
	
	public TOCEntity(Entity mcEntity, EntityLevels levels) {
		this.mcEntity = mcEntity;
		this.levels = levels;
	}
	
}
