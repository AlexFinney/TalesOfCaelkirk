package skeeter144.toc.entity.mob.passive.banker;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.Reference;

public class EntityThreeStreamsBanker extends EntityBanker{
	
	public EntityThreeStreamsBanker(World worldIn) {
		super(worldIn);
		this.texture = new ResourceLocation(Reference.MODID, "textures/entity/sam_derric.png");
	}
	
}
