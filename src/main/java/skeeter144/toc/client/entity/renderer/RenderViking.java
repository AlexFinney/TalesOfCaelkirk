package skeeter144.toc.client.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class RenderViking<T extends LivingEntity, M extends EntityModel<T>> extends LivingRenderer<T, M>{

	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/entity/viking.png");
	
	public RenderViking(EntityRendererManager rendermanagerIn, M ModelIn, float shadowsizeIn) {
		super(rendermanagerIn, ModelIn, shadowsizeIn);
		
//	        BipedArmorLayer<LivingEntity, BipedModel<T>, BipedModel<T>> layerbipedarmor = new LayerBipedArmor(this)
//	        {
//	            protected void initArmor()
//	            {
//	                this.modelLeggings = new ModelViking(0.5F, true);
//	                this.modelArmor = new ModelViking(1.0F, true);
//	            }
//	        };
//	        this.addLayer(layerbipedarmor);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return texture;
	}
	
	
}
