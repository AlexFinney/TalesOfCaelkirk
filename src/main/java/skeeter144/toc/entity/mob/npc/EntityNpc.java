package skeeter144.toc.entity.mob.npc;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.CustomMob;

public class EntityNpc extends CustomMob{
	public ResourceLocation texture;
	public EntityNpc(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.enablePersistence();
	}
	
	@Override
	public boolean isInvulnerable() {
		return true;
	}
}
