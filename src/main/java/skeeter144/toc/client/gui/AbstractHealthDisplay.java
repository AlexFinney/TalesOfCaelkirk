package skeeter144.toc.client.gui;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;

public abstract class AbstractHealthDisplay implements ToroHealthDisplay {

	protected EntityLivingBase entity;

	public static enum Relation {
		FRIEND, FOE, UNKNOWN
	}

	protected Relation determineRelation() {
		if (entity instanceof EntityMob) {
			return Relation.FOE;
		} else if (entity instanceof EntitySlime) {
			return Relation.FOE;
		} else if (entity instanceof EntityGhast) {
			return Relation.FOE;
		} else if (entity instanceof EntityAnimal) {
			return Relation.FRIEND;
		} else if (entity instanceof EntitySquid) {
			return Relation.FRIEND;
		} else if (entity instanceof EntityAmbientCreature) {
			return Relation.FRIEND;
		} else {
			return Relation.UNKNOWN;
		}
	}

	@Override
	public void setEntity(EntityLivingBase entity) {
		this.entity = entity;
	}

	public String getEntityName() {
		if (entity == null || entity.getDisplayName() == null) {
			return "";
		}
		return entity.getDisplayName().getFormattedText();
	}
}