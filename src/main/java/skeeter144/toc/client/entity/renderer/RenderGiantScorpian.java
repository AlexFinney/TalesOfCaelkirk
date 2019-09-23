package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.monster.EntityGiantScorpian;
import skeeter144.toc.util.Reference;

public class RenderGiantScorpian extends RenderLiving<EntityGiantScorpian>{
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/giant_scorpian.png");
	
	public RenderGiantScorpian(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGiantScorpian entity) {
		return texture;
	}

}
