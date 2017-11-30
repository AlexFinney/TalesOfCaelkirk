package skeeter144.toc.entity.mob.mount.flying;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;

public class EntityPegasus extends EntityTameable{

	public EntityPegasus(World worldIn) {
		super(worldIn);
		this.setSize(1.5f, 2f);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

}
