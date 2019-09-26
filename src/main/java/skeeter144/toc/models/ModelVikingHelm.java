package skeeter144.toc.models;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBiped;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 5.1.0
 */
public class ModelVikingHelm extends ModelBiped{
   
    public ModelRenderer helmBase;
    public ModelRenderer rightHornBase;
    public ModelRenderer leftHornBase;
    public ModelRenderer rightHorn1;
    public ModelRenderer rightHorn2;
    public ModelRenderer leftHorn1;
    public ModelRenderer leftHorn2;

    public ModelVikingHelm() {
    	  this.textureWidth = 64;
          this.textureHeight = 64;
        
          this.leftHorn1 = new ModelRenderer(this, 0, 0);
          this.leftHorn1.setRotationPoint(2.6F, -4.7F, 0.0F);
          this.leftHorn1.addBox(0.3F, -0.7F, 0.0F, 2, 6, 2, 0.0F);
          this.setRotateAngle(leftHorn1, 0.0F, 0.0F, 0.5614724203665759F);
          this.rightHorn2 = new ModelRenderer(this, 0, 0);
          this.rightHorn2.setRotationPoint(2.9F, -5.3F, 0.0F);
          this.rightHorn2.addBox(0.3F, -0.7F, 0.0F, 2, 6, 2, 0.0F);
          this.setRotateAngle(rightHorn2, 0.0F, 0.0F, 0.5614724203665759F);
          this.rightHorn1 = new ModelRenderer(this, 0, 0);
          this.rightHorn1.setRotationPoint(2.6F, -4.7F, 0.0F);
          this.rightHorn1.addBox(0.3F, -0.7F, 0.0F, 2, 6, 2, 0.0F);
          this.setRotateAngle(rightHorn1, 0.0F, 0.0F, 0.5614724203665759F);
          this.rightHornBase = new ModelRenderer(this, 0, 22);
          this.rightHornBase.setRotationPoint(-1.8F, 5.1F, 6.0F);
          this.rightHornBase.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
          this.setRotateAngle(rightHornBase, 1.6062265106103817F, 1.2088150399312727F, -0.3490658503988659F);
          this.leftHorn2 = new ModelRenderer(this, 0, 0);
          this.leftHorn2.setRotationPoint(2.9F, -5.3F, 0.0F);
          this.leftHorn2.addBox(0.3F, -0.7F, 0.0F, 2, 6, 2, 0.0F);
          this.setRotateAngle(leftHorn2, 0.0F, 0.0F, 0.5614724203665759F);
          this.helmBase = new ModelRenderer(this, 0, 0);
          this.helmBase.setRotationPoint(-5.0F, -9.0F, -5.0F);
          this.helmBase.addBox(0.0F, 0.0F, 0.0F, 10, 9, 10, 0.0F);
          this.leftHornBase = new ModelRenderer(this, 0, 0);
          this.leftHornBase.setRotationPoint(13.2F, 3.3F, 6.0F);
          this.leftHornBase.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2, 0.0F);
          this.setRotateAngle(leftHornBase, -1.7001252243676763F, -4.1243875553878F, 0.1218239817892042F);
          this.leftHornBase.addChild(this.leftHorn1);
          this.rightHorn1.addChild(this.rightHorn2);
          this.rightHornBase.addChild(this.rightHorn1);
          this.helmBase.addChild(this.rightHornBase);
          this.leftHorn1.addChild(this.leftHorn2);
          this.helmBase.addChild(this.leftHornBase);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	renderHelmet(f5);
    
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    private void renderHelmet(float scale) {
    	 GlStateManager.pushMatrix();
    	 GlStateManager.enableAlphaTest();
    	 GlStateManager.enableBlend();
    	 
    	 if (bipedHead.rotateAngleZ != 0.0F)
             GlStateManager.rotatef(bipedHead.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
         if (bipedHead.rotateAngleY != 0.0F)
             GlStateManager.rotatef(bipedHead.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
         if (bipedHead.rotateAngleX != 0.0F)
             GlStateManager.rotatef(bipedHead.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);

         this.helmBase.render(scale);
         GlStateManager.popMatrix();
         
    }
    
    
}
