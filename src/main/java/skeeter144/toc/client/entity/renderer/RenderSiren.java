package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.monster.EntitySiren;
import skeeter144.toc.util.Reference;

public class RenderSiren extends RenderLiving<EntitySiren>{

	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/siren.png");
	
	public RenderSiren(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySiren entity) {
		return texture;
	}
	
	
}
