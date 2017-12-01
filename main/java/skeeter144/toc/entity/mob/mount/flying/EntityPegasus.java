package skeeter144.toc.entity.mob.mount.flying;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityAbstractHorseMount;

public class EntityPegasus extends EntityAbstractFlyingMount{
	public EntityPegasus(World worldIn) {
		super(worldIn);
		this.setSize(1.5f, 2f);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.2);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(.5);
	}
	
}
