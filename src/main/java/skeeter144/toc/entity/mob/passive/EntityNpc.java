package skeeter144.toc.entity.mob.passive;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.CustomMob;

public class EntityNpc extends CustomMob{
	public ResourceLocation texture;
	public EntityNpc(World worldIn) {
		super(worldIn);
		this.enablePersistence();
	}
	
	@Override
	public boolean getIsInvulnerable() {
		return true;
	}
}
