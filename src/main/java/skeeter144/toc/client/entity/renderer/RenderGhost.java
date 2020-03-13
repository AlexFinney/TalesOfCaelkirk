package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class RenderGhost<T extends LivingEntity, M extends EntityModel<T>> extends LivingRenderer<T, M>{
	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/ghost.png");
	
	public RenderGhost(EntityRendererManager rendermanagerIn, M ModelIn, float shadowsizeIn) {
		super(rendermanagerIn, ModelIn, shadowsizeIn);
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return texture;
	}

}
