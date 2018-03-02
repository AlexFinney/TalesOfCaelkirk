package skeeter144.toc.client.entity.model;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.mob.monster.EntityGhost;
import skeeter144.toc.entity.mob.monster.EntityGhost.AIGhostDiveAttack.DiveStage;
import skeeter144.toc.particles.particle.BasicParticle;

/**
 * Ghost - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelGhost extends AdvancedModelBase {
    public AdvancedModelRenderer body;
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer left_arm;
    public AdvancedModelRenderer left_arm_1;

    public ModelGhost() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.left_arm_1 = new AdvancedModelRenderer(this, 44, 0);
        this.left_arm_1.setRotationPoint(-11.0F, -8.0F, -3.0F);
        this.left_arm_1.addBox(0.0F, 0.0F, 0.0F, 5, 15, 5, 0.0F);
        this.left_arm = new AdvancedModelRenderer(this, 44, 0);
        this.left_arm.setRotationPoint(5.0F, -8.0F, -3.0F);
        this.left_arm.addBox(0.0F, 0.0F, 0.0F, 5, 15, 5, 0.0F);
        this.head = new AdvancedModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -13.7F, -0.8F);
        this.head.addBox(-4.5F, -4.0F, -4.0F, 9, 8, 8, 0.0F);
        this.body = new AdvancedModelRenderer(this, 0, 33);
        this.body.setRotationPoint(0.0F, 3.2F, 0.0F);
        this.body.addBox(-6.0F, -9.5F, -4.0F, 12, 23, 8, 0.0F);
        this.body.addChild(this.left_arm_1);
        this.body.addChild(this.left_arm);
        this.body.addChild(this.head);
        updateDefaultPose();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    		float headPitch, float scaleFactor, Entity entityIn) {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	
    	resetToDefaultPose();
    	
    	
    	
    	EntityGhost ghost = (EntityGhost)entityIn;
    	float f = entityIn.ticksExisted;
        float f1 = 1;
    	float speed = .2f;
        float degree =  1.5f;
    	
        int stage = (int)ghost.getEntityAttribute(EntityGhost.DIVE_STAGE).getBaseValue();
        
        
        if(ghost.world.rand.nextInt(20) == 0) {
			Particle p = new BasicParticle(ghost.world, ghost.posX, ghost.posY + 1, ghost.posZ, 1, 0x000000, 
					TOCMain.rand.nextFloat() * .5f - .25f, 
					TOCMain.rand.nextFloat() * .25f + .125f, 
					TOCMain.rand.nextFloat() * .5f - .25f, 
					true);
			
			Minecraft.getMinecraft().effectRenderer.addEffect(p);
		}
        
        if(stage == DiveStage.IDLE.ordinal()) {
        	this.walk(left_arm, speed, degree / 10, false, 0, 0, f, f1);
            this.walk(left_arm_1, speed, degree / 10, true, 0, 0, f, f1);
            
            bob(body, speed, 3, false, f, f1);
            
        }else if(stage == DiveStage.ANGRY.ordinal()) {
	        this.left_arm.rotationPointX = 8;
	        this.left_arm.rotationPointY = -6;
	        
	        this.left_arm.rotateAngleX = (float) Math.toRadians(-20);
	        this.left_arm.rotateAngleY = (float) Math.toRadians(20);
	        this.left_arm.rotateAngleZ = (float) Math.toRadians(-145);
	        
	        this.left_arm_1.rotationPointX = -3;
	        this.left_arm_1.rotationPointY = -8;
	        this.left_arm_1.rotationPointZ = -4;
	        
	        this.left_arm_1.rotateAngleX = (float) Math.toRadians(-20);
	        this.left_arm_1.rotateAngleY = (float) Math.toRadians(20);
	        this.left_arm_1.rotateAngleZ = (float) Math.toRadians(145);
	        
	        this.head.rotateAngleX = (float) Math.toRadians(-45);
	           
	        this.left_arm.walk(.8f, .3f, false, 0, 0, f, f1);
	        this.left_arm_1.walk(.8f, .3f, true, 0, 0, f, f1);
	        
	        this.head.swing(.5f, .3f, false, 0, 0, f, f1);
	        
	        if(ghost.world.rand.nextInt(2) == 0) {
				Particle p = new BasicParticle(ghost.world, ghost.posX, ghost.posY + 1, ghost.posZ, 1, 0x000000, 
						TOCMain.rand.nextFloat() * .5f - .25f, 
						TOCMain.rand.nextFloat() * .25f + .125f, 
						TOCMain.rand.nextFloat() * .5f - .25f, 
						true);
				
				Minecraft.getMinecraft().effectRenderer.addEffect(p);
			}
	        
        }else if(stage == DiveStage.DIVING.ordinal()) {
        	
        	Vec3d prevPos = new Vec3d(ghost.prevPosX, ghost.prevPosY, ghost.prevPosZ);
        	
        	double dy = ghost.prevPosY - ghost.posY;
            	
            double dist = prevPos.distanceTo(ghost.getPositionVector());
            
           	this.body.rotateAngleX = (float)Math.asin(dy / dist) + (float)Math.toRadians(75);
        	
            this.left_arm.rotationPointX = 8;
            this.left_arm.rotationPointY = -6;
            
            this.left_arm.rotateAngleX = (float) Math.toRadians(-20);
            this.left_arm.rotateAngleY = (float) Math.toRadians(20);
            this.left_arm.rotateAngleZ = (float) Math.toRadians(-145);
            
            this.left_arm_1.rotationPointX = -3;
            this.left_arm_1.rotationPointY = -8;
            this.left_arm_1.rotationPointZ = -4;
            
            this.left_arm_1.rotateAngleX = (float) Math.toRadians(-20);
            this.left_arm_1.rotateAngleY = (float) Math.toRadians(20);
            this.left_arm_1.rotateAngleZ = (float) Math.toRadians(145);
            
            this.head.rotateAngleX = (float) Math.toRadians(-60);
            
            this.left_arm.walk(.1f, .3f, false, 0, 0, f, f1);
            this.left_arm_1.walk(.1f, .3f, true, 0, 0, f, f1);
            
            if(ghost.world.rand.nextInt(2) == 0) {
				Particle p = new BasicParticle(ghost.world, ghost.posX, ghost.posY + 1, ghost.posZ, 1, 0x000000, 
						TOCMain.rand.nextFloat() * .5f - .25f, 
						TOCMain.rand.nextFloat() * .25f + .125f, 
						TOCMain.rand.nextFloat() * .5f - .25f, 
						true);
				
				Minecraft.getMinecraft().effectRenderer.addEffect(p);
			}
        }
        
    
        
       
     
    }
    
    public void setRotateAngle(AdvancedModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
