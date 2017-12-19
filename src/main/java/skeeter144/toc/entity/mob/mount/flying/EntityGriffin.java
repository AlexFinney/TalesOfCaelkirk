package skeeter144.toc.entity.mob.mount.flying;

import java.util.UUID;

import net.minecraft.world.World;

public class EntityGriffin extends EntityAbstractFlyingMount{

	public EntityGriffin(World worldIn) {
		this(worldIn, null);
	}

	
	public EntityGriffin(World worldIn, UUID uuid) {
		super(worldIn, uuid);
	}

}
