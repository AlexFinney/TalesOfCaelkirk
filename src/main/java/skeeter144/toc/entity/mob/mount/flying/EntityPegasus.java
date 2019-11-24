package skeeter144.toc.entity.mob.mount.flying;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.world.World;

public class EntityPegasus extends EntityAbstractFlyingMount{
	public EntityPegasus(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn) {
		this(type, worldIn, null);
	}
	
	public EntityPegasus(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn, UUID uuid) {
		super(type, worldIn, uuid);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.3);
		this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(.5);
	}
	
}
