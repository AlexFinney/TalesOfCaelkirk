package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.client.entity.model.ModelViking;
import skeeter144.toc.entity.mob.monster.EntityViking;
import skeeter144.toc.util.Reference;

public class RenderViking extends RenderLiving<EntityViking>{

	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/viking.png");
	
	public RenderViking(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
		
	        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
	        {
	            protected void initArmor()
	            {
	                this.modelLeggings = new ModelViking(0.5F, true);
	                this.modelArmor = new ModelViking(1.0F, true);
	            }
	        };
	        this.addLayer(layerbipedarmor);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityViking entity) {
		return texture;
	}
	
	
}
