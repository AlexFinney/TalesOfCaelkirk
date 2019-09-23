package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.monster.EntityGiantSpider;
import skeeter144.toc.util.Reference;

public class RenderGiantSpider extends RenderLiving<EntityGiantSpider>{
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/giant_spider.png");
	
	public RenderGiantSpider(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGiantSpider entity) {
		return texture;
	}

}
