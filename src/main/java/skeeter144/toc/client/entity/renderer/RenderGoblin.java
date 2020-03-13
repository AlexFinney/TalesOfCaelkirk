package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.client.entity.model.ModelGoblin;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.util.Reference;

public class RenderGoblin extends MobRenderer<EntityGoblin, ModelGoblin<EntityGoblin>>{
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/goblin.png");
	
	public RenderGoblin(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, new ModelGoblin<>(), .5f);
	}

	@Override
	public ResourceLocation getEntityTexture(EntityGoblin entity) {
		return texture;
	}

}
