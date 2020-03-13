package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.npc.EntityNpc;

public class RenderHumanNpc<T extends LivingEntity, M extends EntityModel<T>> extends LivingRenderer<T, M>{

	public RenderHumanNpc(EntityRendererManager renderManagerIn, M model, float scale) {
		super(renderManagerIn, model, scale);
	}
	
	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return ((EntityNpc)entity).texture;
	}
}