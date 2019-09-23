package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.mount.flying.EntityGriffin;
import skeeter144.toc.util.Reference;

public class RenderGriffin extends RenderLiving<EntityGriffin>{
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/griffin.png");
	
	public RenderGriffin(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGriffin entity) {
		return texture;
	}

}
