package skeeter144.toc.client.gui;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SquidEntity;

public abstract class AbstractHealthDisplay implements ToroHealthDisplay {

	protected LivingEntity entity;

	public static enum Relation {
		FRIEND, FOE, UNKNOWN
	}

	protected Relation determineRelation() {
		if (entity instanceof MobEntity) {
			return Relation.FOE;
		} else if (entity instanceof SlimeEntity) {
			return Relation.FOE;
		} else if (entity instanceof GhastEntity) {
			return Relation.FOE;
		} else if (entity instanceof AnimalEntity) {
			return Relation.FRIEND;
		} else if (entity instanceof SquidEntity) {
			return Relation.FRIEND;
		} else if (entity instanceof AmbientEntity) {
			return Relation.FRIEND;
		} else {
			return Relation.UNKNOWN;
		}
	}

	@Override
	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}

	public String getEntityName() {
		if (entity == null || entity.getDisplayName() == null) {
			return "";
		}
		return entity.getDisplayName().getFormattedText();
	}
}