package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomLiving<T extends EntityLiving> extends RenderLiving<T> {
	ResourceLocation texture;
	public RenderCustomLiving(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn, ResourceLocation texture) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
		this.texture = texture;
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return texture;
	}

}
