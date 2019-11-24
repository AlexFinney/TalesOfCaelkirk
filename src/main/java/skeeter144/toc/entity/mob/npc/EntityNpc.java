package skeeter144.toc.entity.mob.npc;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.CustomCreature;

public class EntityNpc extends CustomCreature{
	public ResourceLocation texture;
	public EntityNpc(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(type, worldIn);
		this.enablePersistence();
	}
	
	@Override
	public boolean isInvulnerable() {
		return true;
	}
}
