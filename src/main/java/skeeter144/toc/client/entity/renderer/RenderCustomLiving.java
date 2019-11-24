package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class RenderCustomLiving<T extends LivingEntity, M extends EntityModel<T>> extends LivingRenderer<T, M> {
	ResourceLocation texture;
	public RenderCustomLiving(EntityRendererManager rendermanagerIn, M ModelIn, float shadowsizeIn, ResourceLocation texture) {
		super(rendermanagerIn, ModelIn, shadowsizeIn);
		this.texture = texture;
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return texture;
	}

}
