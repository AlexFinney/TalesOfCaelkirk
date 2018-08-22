package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.npc.EntityNpc;

public class RenderHumanNpc extends RenderBiped<EntityNpc>{

	public RenderHumanNpc(RenderManager renderManagerIn, ModelBiped model, float scale) {
		super(renderManagerIn, model, scale);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNpc entity) {
		return entity.texture;
	}
}