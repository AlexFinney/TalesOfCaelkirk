package skeeter144.toc.client.entity.model;

import net.minecraft.entity.Entity;
import skeeter144.toc.client.entity.renderer.AdvancedModelRenderer;
import skeeter144.toc.entity.mob.mount.flying.EntityPegasus;

public class ModelPegasus extends AdvancedModelBase {
    public AdvancedModelRenderer body;
    public AdvancedModelRenderer l_wing;
    public AdvancedModelRenderer r_wing;
    public AdvancedModelRenderer fl_shoulder;
    public AdvancedModelRenderer fr_shoulder;
    public AdvancedModelRenderer br_shoulder;
    public AdvancedModelRenderer saddle_seat;
    public AdvancedModelRenderer right_saddle_bag;
    public AdvancedModelRenderer left_bag;
    public AdvancedModelRenderer bl_shoulder;
    public AdvancedModelRenderer tale_base;
    public AdvancedModelRenderer neck;
    public AdvancedModelRenderer left_wing_bone2;
    public AdvancedModelRenderer shape45;
    public AdvancedModelRenderer r_wing_2;
    public AdvancedModelRenderer r_wing_feathers;
    public AdvancedModelRenderer fl_shin;
    public AdvancedModelRenderer fl_hoof;
    public AdvancedModelRenderer fr_shin;
    public AdvancedModelRenderer fr_hoof;
    public AdvancedModelRenderer br_shin;
    public AdvancedModelRenderer br_hoof;
    public AdvancedModelRenderer saddle_brace;
    public AdvancedModelRenderer saddle_horn;
    public AdvancedModelRenderer right_saddle_strap;
    public AdvancedModelRenderer left_saddle_strap;
    public AdvancedModelRenderer right_stirrup;
    public AdvancedModelRenderer left_stirrup;
    public AdvancedModelRenderer bl_shin;
    public AdvancedModelRenderer bl_hoof;
    public AdvancedModelRenderer tail_base_1;
    public AdvancedModelRenderer tale_base_2;
    public AdvancedModelRenderer mane;
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer muzzle;
    public AdvancedModelRenderer jaw;
    public AdvancedModelRenderer left_alt_ear;
    public AdvancedModelRenderer right_alt_ear;
    public AdvancedModelRenderer left_ear;
    public AdvancedModelRenderer right_ear;
    public AdvancedModelRenderer left_rein;
    public AdvancedModelRenderer right_rein;
    public AdvancedModelRenderer right_rein_2;
    public AdvancedModelRenderer muzzle_overlay;
    public AdvancedModelRenderer left_bit;

    public ModelPegasus() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        init();
    }

    private void init() {
    	  this.br_shoulder = new AdvancedModelRenderer(this, 44, 29);
          this.br_shoulder.setRotationPoint(-3.4F, -2.0F, 3.8F);
          this.br_shoulder.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
          this.right_stirrup = new AdvancedModelRenderer(this, 74, 4);
          this.right_stirrup.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.right_stirrup.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2, 0.0F);
          this.muzzle = new AdvancedModelRenderer(this, 24, 18);
          this.muzzle.setRotationPoint(0.0F, 0.02F, 0.02F);
          this.muzzle.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6, 0.0F);
          this.saddle_seat = new AdvancedModelRenderer(this, 80, 0);
          this.saddle_seat.setRotationPoint(0.0F, -9.0F, -7.0F);
          this.saddle_seat.addBox(-5.0F, 0.0F, -3.0F, 10, 1, 8, 0.0F);
          this.fr_shoulder = new AdvancedModelRenderer(this, 44, 29);
          this.fr_shoulder.setRotationPoint(-3.4F, -2.0F, -17.2F);
          this.fr_shoulder.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
          this.fl_shoulder = new AdvancedModelRenderer(this, 44, 29);
          this.fl_shoulder.setRotationPoint(4.4F, -2.0F, -17.3F);
          this.fl_shoulder.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
          this.right_alt_ear = new AdvancedModelRenderer(this, 0, 12);
          this.right_alt_ear.setRotationPoint(1.4F, 0.0F, 0.0F);
          this.right_alt_ear.addBox(0.0F, -16.0F, 4.0F, 2, 7, 1, 0.0F);
          this.setRotateAngle(right_alt_ear, 0.0F, 0.0F, -0.4553564018453205F);
          this.left_wing_bone2 = new AdvancedModelRenderer(this, 0, 0);
          this.left_wing_bone2.setRotationPoint(16.0F, 0.0F, 0.0F);
          this.left_wing_bone2.addBox(0.0F, 0.0F, 0.0F, 15, 1, 1, 0.0F);
          this.setRotateAngle(left_wing_bone2, 0.0F, 0.0F, 0.6592108584782583F);
          this.fl_shin = new AdvancedModelRenderer(this, 44, 41);
          this.fl_shin.setRotationPoint(-0.1F, 6.0F, 0.0F);
          this.fl_shin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3, 0.0F);
          this.left_alt_ear = new AdvancedModelRenderer(this, 0, 12);
          this.left_alt_ear.setRotationPoint(-0.7F, 0.0F, 0.0F);
          this.left_alt_ear.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1, 0.0F);
          this.setRotateAngle(left_alt_ear, 0.0F, 0.0F, 0.36425021489121656F);
          this.left_bit = new AdvancedModelRenderer(this, 74, 13);
          this.left_bit.setRotationPoint(0.5F, 0.0F, 0.0F);
          this.left_bit.addBox(1.7F, -8.0F, -4.0F, 1, 2, 2, 0.0F);
          this.bl_shin = new AdvancedModelRenderer(this, 44, 41);
          this.bl_shin.setRotationPoint(0.1F, 6.0F, 0.0F);
          this.bl_shin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3, 0.0F);
          this.left_stirrup = new AdvancedModelRenderer(this, 74, 0);
          this.left_stirrup.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.left_stirrup.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2, 0.0F);
          this.right_rein_2 = new AdvancedModelRenderer(this, 74, 13);
          this.right_rein_2.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.right_rein_2.addBox(-2.5F, -8.0F, -4.0F, 1, 2, 2, 0.0F);
          this.l_wing = new AdvancedModelRenderer(this, 0, 63);
          this.l_wing.setRotationPoint(4.0F, -7.1F, -11.0F);
          this.l_wing.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
          this.setRotateAngle(l_wing, 0.8943067087218944F, 0.3764675196551769F, -0.7838273670706533F);
          this.right_saddle_bag = new AdvancedModelRenderer(this, 0, 34);
          this.right_saddle_bag.setRotationPoint(-7.9F, -8.0F, 0.5F);
          this.right_saddle_bag.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, 0.0F);
          this.setRotateAngle(right_saddle_bag, -0.0F, 1.5707963267948966F, 0.0F);
          this.body = new AdvancedModelRenderer(this, 0, 34);
          this.body.setRotationPoint(0.0F, 11.0F, 9.0F);
          this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24, 0.0F);
          this.shape45 = new AdvancedModelRenderer(this, 0, 89);
          this.shape45.setRotationPoint(-4.7F, 0.0F, 0.5F);
          this.shape45.addBox(0.0F, 0.0F, 0.0F, 32, 19, 0, 0.0F);
          this.right_ear = new AdvancedModelRenderer(this, 0, 0);
          this.right_ear.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.right_ear.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1, 0.0F);
          this.muzzle_overlay = new AdvancedModelRenderer(this, 80, 12);
          this.muzzle_overlay.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.muzzle_overlay.addBox(-3F, -10.1F, -7.0F, 6, 5, 12, 0.0F);
          this.neck = new AdvancedModelRenderer(this, 0, 12);
          this.neck.setRotationPoint(0.0F, -8.0F, -20.0F);
          this.neck.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8, 0.0F);
          this.setRotateAngle(neck, 0.5235987755982988F, 0.0F, 0.0F);
          this.r_wing = new AdvancedModelRenderer(this, 0, 64);
          this.r_wing.setRotationPoint(-6.0F, -8.0F, -11.0F);
          this.r_wing.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
          this.setRotateAngle(r_wing, -0.8594001236820078F, 3.0003955171034518F, 0.771610062306693F);
          this.left_saddle_strap = new AdvancedModelRenderer(this, 70, 0);
          this.left_saddle_strap.setRotationPoint(5.0F, 1.0F, 0.0F);
          this.left_saddle_strap.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
          this.fl_hoof = new AdvancedModelRenderer(this, 44, 51);
          this.fl_hoof.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.fl_hoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4, 0.0F);
          this.fr_shin = new AdvancedModelRenderer(this, 44, 41);
          this.fr_shin.setRotationPoint(0.1F, 6.0F, 0.0F);
          this.fr_shin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3, 0.0F);
          this.br_shin = new AdvancedModelRenderer(this, 44, 41);
          this.br_shin.setRotationPoint(0.1F, 6.0F, 0.0F);
          this.br_shin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3, 0.0F);
          this.left_rein = new AdvancedModelRenderer(this, 44, 10);
          this.left_rein.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.left_rein.addBox(2.6F, -6.0F, -6.0F, 0, 3, 16, 0.0F);
          this.setRotateAngle(left_rein, -0.5009094953223726F, 0.0F, 0.0F);
          this.jaw = new AdvancedModelRenderer(this, 24, 27);
          this.jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.jaw.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5, 0.0F);
          this.tale_base_2 = new AdvancedModelRenderer(this, 24, 3);
          this.tale_base_2.setRotationPoint(0.0F, -0.1F, 6.4F);
          this.tale_base_2.addBox(-1.6F, -2.0F, 0.0F, 3, 4, 7, 0.0F);
          this.setRotateAngle(tale_base_2, -0.2813470754214859F, 0.0F, 0.0F);
          this.saddle_horn = new AdvancedModelRenderer(this, 106, 9);
          this.saddle_horn.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.saddle_horn.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2, 0.0F);
          this.saddle_brace = new AdvancedModelRenderer(this, 80, 9);
          this.saddle_brace.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.saddle_brace.addBox(-4.0F, -1.0F, 3.0F, 8, 1, 2, 0.0F);
          this.r_wing_feathers = new AdvancedModelRenderer(this, 0, 69);
          this.r_wing_feathers.setRotationPoint(-4.7F, 0.0F, 0.5F);
          this.r_wing_feathers.addBox(0.0F, 0.0F, 0.0F, 33, 18, 0, 0.0F);
          this.mane = new AdvancedModelRenderer(this, 58, 0);
          this.mane.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.mane.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4, 0.0F);
          this.right_saddle_strap = new AdvancedModelRenderer(this, 80, 0);
          this.right_saddle_strap.setRotationPoint(-5.5F, 0.8F, 0.2F);
          this.right_saddle_strap.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
          this.tail_base_1 = new AdvancedModelRenderer(this, 38, 7);
          this.tail_base_1.setRotationPoint(0.0F, 0.0F, 3.2F);
          this.tail_base_1.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 7, 0.0F);
          this.head = new AdvancedModelRenderer(this, 0, 0);
          this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7, 0.0F);
          this.bl_hoof = new AdvancedModelRenderer(this, 44, 51);
          this.bl_hoof.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.bl_hoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4, 0.0F);
          this.tale_base = new AdvancedModelRenderer(this, 44, 0);
          this.tale_base.setRotationPoint(0.0F, -7.5F, 5.0F);
          this.tale_base.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3, 0.0F);
          this.setRotateAngle(tale_base, -1.3089969389957472F, 0.0F, 0.0F);
          this.br_hoof = new AdvancedModelRenderer(this, 44, 51);
          this.br_hoof.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.br_hoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4, 0.0F);
          this.right_rein = new AdvancedModelRenderer(this, 44, 5);
          this.right_rein.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.right_rein.addBox(-2.6F, -6.0F, -6.0F, 0, 3, 16, 0.0F);
          this.setRotateAngle(right_rein, -0.40980330836826856F, 0.0F, 0.0F);
          this.left_bag = new AdvancedModelRenderer(this, 0, 47);
          this.left_bag.setRotationPoint(4.0F, -7.8F, 0.5F);
          this.left_bag.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, 0.0F);
          this.setRotateAngle(left_bag, 0.0F, 1.5707963267948966F, 0.0F);
          this.r_wing_2 = new AdvancedModelRenderer(this, 0, 64);
          this.r_wing_2.setRotationPoint(16.0F, 0.0F, 0.0F);
          this.r_wing_2.addBox(0.0F, 0.0F, 0.0F, 15, 1, 1, 0.0F);
          this.setRotateAngle(r_wing_2, 0.0F, 0.0F, 0.6592108584782583F);
          this.fr_hoof = new AdvancedModelRenderer(this, 44, 51);
          this.fr_hoof.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.fr_hoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4, 0.0F);
          this.left_ear = new AdvancedModelRenderer(this, 0, 0);
          this.left_ear.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.left_ear.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1, 0.0F);
          this.bl_shoulder = new AdvancedModelRenderer(this, 44, 29);
          this.bl_shoulder.setRotationPoint(4.6F, -2.0F, 3.8F);
          this.bl_shoulder.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
          this.body.addChild(this.br_shoulder);
          this.right_saddle_strap.addChild(this.right_stirrup);
          this.head.addChild(this.muzzle);
          this.body.addChild(this.saddle_seat);
          this.body.addChild(this.fr_shoulder);
          this.body.addChild(this.fl_shoulder);
          this.head.addChild(this.right_alt_ear);
          this.l_wing.addChild(this.left_wing_bone2);
          this.fl_shoulder.addChild(this.fl_shin);
          this.head.addChild(this.left_alt_ear);
          this.head.addChild(this.left_bit);
          this.bl_shoulder.addChild(this.bl_shin);
          this.left_saddle_strap.addChild(this.left_stirrup);
          this.head.addChild(this.right_rein_2);
          this.body.addChild(this.l_wing);
          this.body.addChild(this.right_saddle_bag);
          this.l_wing.addChild(this.shape45);
          this.head.addChild(this.right_ear);
          this.head.addChild(this.muzzle_overlay);
          this.body.addChild(this.neck);
          this.body.addChild(this.r_wing);
          this.saddle_seat.addChild(this.left_saddle_strap);
          this.fl_shin.addChild(this.fl_hoof);
          this.fr_shoulder.addChild(this.fr_shin);
          this.br_shoulder.addChild(this.br_shin);
          this.head.addChild(this.left_rein);
          this.head.addChild(this.jaw);
          this.tail_base_1.addChild(this.tale_base_2);
          this.saddle_seat.addChild(this.saddle_horn);
          this.saddle_seat.addChild(this.saddle_brace);
          this.r_wing.addChild(this.r_wing_feathers);
          this.neck.addChild(this.mane);
          this.saddle_seat.addChild(this.right_saddle_strap);
          this.tale_base.addChild(this.tail_base_1);
          this.neck.addChild(this.head);
          this.bl_shin.addChild(this.bl_hoof);
          this.body.addChild(this.tale_base);
          this.br_shin.addChild(this.br_hoof);
          this.head.addChild(this.right_rein);
          this.body.addChild(this.left_bag);
          this.r_wing.addChild(this.r_wing_2);
          this.fr_shin.addChild(this.fr_hoof);
          this.head.addChild(this.left_ear);
          this.body.addChild(this.bl_shoulder);
          this.updateDefaultPose();
    }
    
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    		float headPitch, float scaleFactor, Entity entityIn) {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	//init();
    	resetToDefaultPose();

    	boolean isFlying = ((EntityPegasus)entityIn).getIsFlying();
    	
    	float f = entityIn.ticksExisted;
        float f1 = 1;
    	float speed = .3f;
        float height = .2f;
        float degree =  2f;
        float flapHeight = .5f;
        
        if(isFlying) {
        	 bob(body, speed, degree, false, f, f1);
        	
        	 flap(r_wing, speed, flapHeight, false, 0, -.3F, f, f1);
             walk(r_wing, speed, degree / 8, false, -1F, 0, f, f1);
             
             flap(l_wing, speed, flapHeight, false, 3.4F, .34F, f, f1);
             walk(l_wing, speed, degree / 8, false, 1F, 0, f, f1);
             
             fl_shoulder.rotateAngleX = .5f;
             fl_shin.rotateAngleX = .7f;
             
             fr_shoulder.rotateAngleX = .5f;
             fr_shin.rotateAngleX = .7f;
             
             bl_shoulder.rotateAngleX = .5f;
             bl_shin.rotateAngleX = .7f;
             
             br_shoulder.rotateAngleX = .5f;
             br_shin.rotateAngleX = .7f;
             
             
             walk(fl_shoulder, speed, degree / 32, false, 2F, 0, f, f1);
             walk(fl_shin, speed, degree / 32, false, 2F, 0, f, f1);
             
             walk(fr_shoulder, speed, degree / 32, false, 3F, 0, f, f1);
             walk(fr_shin, speed, degree / 32, false, 3F, 0, f, f1);
             
             walk(bl_shoulder, speed, degree / 32, false, 0F, 0, f, f1);
             walk(bl_shin, speed, degree / 32, false, 0F, 0, f, f1);
             
             walk(br_shoulder, speed, degree / 32, false, -1F, 0, f, f1);
             walk(br_shin, speed, degree / 32, false, -1F, 0, f, f1);
        }else {
        	// bob(body, speed * 2, degree / 8, false, f, f1);
        	
        	 walk(fl_shoulder, speed*2, degree / 8, false, 0F, 0, f, f1);
             walk(fl_shin, speed*2, degree / 8, false, 0F, 0, f, f1);
             
             walk(fr_shoulder, speed*2, degree / 8, false, 1F, 0, f, f1);
             walk(fr_shin, speed*2, degree / 8, false, 1F, 0, f, f1);
             
             walk(bl_shoulder, speed*2, degree / 8, false, 2F, 0, f, f1);
             walk(bl_shin, speed*2, degree / 8, false, 2F, 0, f, f1);
             
             walk(br_shoulder, speed*2, degree / 8, false, 3F, 0, f, f1);
             walk(br_shin, speed*2, degree / 8, false, 3F, 0, f, f1);
             
             flap(r_wing, speed, flapHeight / 16, false, 0, -.3F, f, f1);
             walk(r_wing, speed, degree / 8, false, -1F, 0, f, f1);
             
             flap(l_wing, speed, flapHeight / 16, false, 3.4F, .34F, f, f1);
             walk(l_wing, speed, degree / 8, false, 1F, 0, f, f1);
        }
    }
    
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(AdvancedModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
