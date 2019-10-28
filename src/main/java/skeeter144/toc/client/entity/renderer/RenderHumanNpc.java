package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.entity.mob.npc.EntityNpc;

public class RenderHumanNpc extends RenderLiving<EntityNpc>{

	public RenderHumanNpc(RenderManager renderManagerIn, ModelBase model, float scale) {
		super(renderManagerIn, model, scale);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNpc entity) {
		return entity.texture;
	}
}