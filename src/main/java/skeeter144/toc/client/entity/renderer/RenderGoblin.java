package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.util.Reference;

public class RenderGoblin extends RenderLiving<EntityGoblin>{
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/goblin.png");
	
	public RenderGoblin(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGoblin entity) {
		return texture;
	}

}
