package skeeter144.toc.entity.mob.mount.flying;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityPegasus extends EntityAbstractFlyingMount{
	public EntityPegasus(EntityType<?> type, World worldIn) {
		this(type, worldIn, null);
	}
	
	public EntityPegasus(EntityType<?> type, World worldIn, UUID uuid) {
		super(type, worldIn, uuid);
		this.setSize(1.5f, 2f);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.3);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(.5);
	}
	
}
