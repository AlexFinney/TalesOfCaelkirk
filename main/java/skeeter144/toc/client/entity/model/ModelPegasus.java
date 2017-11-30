package skeeter144.toc.client.entity.model;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelHorse - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelPegasus extends AdvancedModelBase {
    public AdvancedModelRenderer field_110705_d;
    public AdvancedModelRenderer field_110703_f;
    public AdvancedModelRenderer field_110709_a;
    public AdvancedModelRenderer field_110706_e;
    public AdvancedModelRenderer field_110704_g;
    public AdvancedModelRenderer field_110702_R;
    public AdvancedModelRenderer field_110701_S;
    public AdvancedModelRenderer field_110699_Q;
    public AdvancedModelRenderer field_110696_I;
    public AdvancedModelRenderer field_110692_M;
    public AdvancedModelRenderer field_110695_H;
    public AdvancedModelRenderer field_110691_L;
    public AdvancedModelRenderer field_110698_K;
    public AdvancedModelRenderer field_110694_O;
    public AdvancedModelRenderer field_110697_J;
    public AdvancedModelRenderer field_110693_N;
    public AdvancedModelRenderer field_110717_i;
    public AdvancedModelRenderer body;
    public AdvancedModelRenderer field_110713_m;
    public AdvancedModelRenderer field_110700_P;
    public AdvancedModelRenderer field_110716_h;
    public AdvancedModelRenderer field_110714_j;
    public AdvancedModelRenderer field_110712_l;
    public AdvancedModelRenderer field_110710_n;
    public AdvancedModelRenderer field_110687_G;
    public AdvancedModelRenderer bl_shoulder;
    public AdvancedModelRenderer field_110709_aChild;
    public AdvancedModelRenderer field_110709_aChild_1;
    public AdvancedModelRenderer fl_shoulder;
    public AdvancedModelRenderer fr_shoulder;
    public AdvancedModelRenderer br_shoulder;
    public AdvancedModelRenderer l_wing;
    public AdvancedModelRenderer l_wing_2;
    public AdvancedModelRenderer r_wing;
    public AdvancedModelRenderer l_wing_fearhers;
    public AdvancedModelRenderer r_wing_2;
    public AdvancedModelRenderer r_wing_feathers;
    public AdvancedModelRenderer fl_shin;
    public AdvancedModelRenderer fl_hoof;
    public AdvancedModelRenderer fr_shin;
    public AdvancedModelRenderer fr_hoof;
    public AdvancedModelRenderer br_shin;
    public AdvancedModelRenderer br_hoof;
    public AdvancedModelRenderer bl_shin;
    public AdvancedModelRenderer bl_hoof;

    public ModelPegasus() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        init();
        this.updateDefaultPose();
    }
    
    private void init() {
    	  this.body = new AdvancedModelRenderer(this, 0, 34);
          this.body.setRotationPoint(0.0F, 11.0F, 11.0F);
          this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24, 0.0F);
          this.br_shoulder = new AdvancedModelRenderer(this, 44, 29);
          this.br_shoulder.setRotationPoint(-3.4F, -2.0F, 3.8F);
          this.br_shoulder.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
          this.l_wing_fearhers = new AdvancedModelRenderer(this, 0, 89);
          this.l_wing_fearhers.setRotationPoint(-4.7F, 0.0F, 0.5F);
          this.l_wing_fearhers.addBox(0.0F, 0.0F, 0.0F, 32, 19, 0, 0.0F);
          this.bl_hoof = new AdvancedModelRenderer(this, 44, 51);
          this.bl_hoof.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.bl_hoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4, 0.0F);
          this.field_110687_G = new AdvancedModelRenderer(this, 0, 34);
          this.field_110687_G.setRotationPoint(-7.5F, 3.0F, 10.0F);
          this.field_110687_G.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, 0.0F);
          this.setRotateAngle(field_110687_G, -0.0F, 1.5707963705062866F, 0.0F);
          this.field_110694_O = new AdvancedModelRenderer(this, 74, 4);
          this.field_110694_O.setRotationPoint(-5.0F, 3.0F, 2.0F);
          this.field_110694_O.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2, 0.0F);
          this.field_110698_K = new AdvancedModelRenderer(this, 80, 9);
          this.field_110698_K.setRotationPoint(0.0F, 2.0F, 2.0F);
          this.field_110698_K.addBox(-4.0F, -1.0F, 3.0F, 8, 1, 2, 0.0F);
          this.field_110709_a = new AdvancedModelRenderer(this, 0, 0);
          this.field_110709_a.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110709_a.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7, 0.0F);
          this.setRotateAngle(field_110709_a, 0.5235987901687622F, 0.0F, 0.0F);
          this.field_110709_aChild = new AdvancedModelRenderer(this, 24, 18);
          this.field_110709_aChild.setRotationPoint(0.0F, 0.019999999552965164F, 0.019999999552965164F);
          this.field_110709_aChild.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6, 0.0F);
          this.bl_shin = new AdvancedModelRenderer(this, 44, 41);
          this.bl_shin.setRotationPoint(0.1F, 6.0F, 0.0F);
          this.bl_shin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3, 0.0F);
          this.r_wing = new AdvancedModelRenderer(this, 0, 64);
          this.r_wing.setRotationPoint(-6.0F, -8.0F, -11.0F);
          this.r_wing.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
          this.setRotateAngle(r_wing, -0.8594001236820078F, 3.0003955171034518F, 0.771610062306693F);
          this.fl_shin = new AdvancedModelRenderer(this, 44, 41);
          this.fl_shin.setRotationPoint(-0.1F, 6.0F, 0.0F);
          this.fl_shin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3, 0.0F);
          this.field_110706_e = new AdvancedModelRenderer(this, 0, 0);
          this.field_110706_e.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110706_e.addBox(-2.450000047683716F, -12.0F, 4.0F, 2, 3, 1, 0.0F);
          this.setRotateAngle(field_110706_e, 0.5235987901687622F, 0.0F, 0.0F);
          this.field_110695_H = new AdvancedModelRenderer(this, 0, 47);
          this.field_110695_H.setRotationPoint(4.5F, 3.0F, 10.0F);
          this.field_110695_H.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, 0.0F);
          this.setRotateAngle(field_110695_H, 0.0F, 1.5707963705062866F, 0.0F);
          this.r_wing_2 = new AdvancedModelRenderer(this, 0, 64);
          this.r_wing_2.setRotationPoint(16.0F, 0.0F, 0.0F);
          this.r_wing_2.addBox(0.0F, 0.0F, 0.0F, 15, 1, 1, 0.0F);
          this.setRotateAngle(r_wing_2, 0.0F, 0.0F, 0.6592108584782583F);
          this.fl_hoof = new AdvancedModelRenderer(this, 44, 51);
          this.fl_hoof.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.fl_hoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4, 0.0F);
          this.field_110716_h = new AdvancedModelRenderer(this, 0, 12);
          this.field_110716_h.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110716_h.addBox(-2.049999952316284F, -9.800000190734863F, -2.0F, 4, 14, 8, 0.0F);
          this.setRotateAngle(field_110716_h, 0.5235987901687622F, 0.0F, 0.0F);
          this.field_110717_i = new AdvancedModelRenderer(this, 80, 12);
          this.field_110717_i.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110717_i.addBox(-2.5F, -10.100000381469727F, -7.0F, 5, 5, 12, 0.19999980926513672F);
          this.setRotateAngle(field_110717_i, 0.5235987901687622F, 0.0F, 0.0F);
          this.field_110704_g = new AdvancedModelRenderer(this, 0, 12);
          this.field_110704_g.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110704_g.addBox(0.0F, -16.0F, 4.0F, 2, 7, 1, 0.0F);
          this.setRotateAngle(field_110704_g, 0.5235987901687622F, 0.0F, -0.2617993950843811F);
          this.br_hoof = new AdvancedModelRenderer(this, 44, 51);
          this.br_hoof.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.br_hoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4, 0.0F);
          this.field_110701_S = new AdvancedModelRenderer(this, 44, 5);
          this.field_110701_S.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110701_S.addBox(-2.5999999046325684F, -6.0F, -6.0F, 0, 3, 16, 0.0F);
          this.l_wing = new AdvancedModelRenderer(this, 0, 63);
          this.l_wing.setRotationPoint(4.0F, -7.1F, -11.0F);
          this.l_wing.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
          this.setRotateAngle(l_wing, 0.8943067087218944F, 0.3764675196551769F, -0.7838273670706533F);
          this.l_wing_2 = new AdvancedModelRenderer(this, 0, 0);
          this.l_wing_2.setRotationPoint(16.0F, 0.0F, 0.0F);
          this.l_wing_2.addBox(0.0F, 0.0F, 0.0F, 15, 1, 1, 0.0F);
          this.setRotateAngle(l_wing_2, 0.0F, 0.0F, 0.6592108584782583F);
          this.field_110710_n = new AdvancedModelRenderer(this, 24, 3);
          this.field_110710_n.setRotationPoint(0.0F, 3.0F, 14.0F);
          this.field_110710_n.addBox(-1.5F, -4.5F, 9.0F, 3, 4, 7, 0.0F);
          this.setRotateAngle(field_110710_n, -1.570796251296997F, 0.0F, 0.0F);
          this.field_110696_I = new AdvancedModelRenderer(this, 80, 0);
          this.field_110696_I.setRotationPoint(0.0F, 2.0F, 2.0F);
          this.field_110696_I.addBox(-5.0F, 0.0F, -3.0F, 10, 1, 8, 0.0F);
          this.br_shin = new AdvancedModelRenderer(this, 44, 41);
          this.br_shin.setRotationPoint(0.1F, 6.0F, 0.0F);
          this.br_shin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3, 0.0F);
          this.field_110703_f = new AdvancedModelRenderer(this, 0, 12);
          this.field_110703_f.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110703_f.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1, 0.0F);
          this.setRotateAngle(field_110703_f, 0.5235987901687622F, 0.0F, 0.2617993950843811F);
          this.fr_shoulder = new AdvancedModelRenderer(this, 44, 29);
          this.fr_shoulder.setRotationPoint(-3.4F, -2.0F, -17.2F);
          this.fr_shoulder.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
          this.bl_shoulder = new AdvancedModelRenderer(this, 44, 29);
          this.bl_shoulder.setRotationPoint(4.6F, 9.0F, 13.8F);
          this.bl_shoulder.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
          this.field_110697_J = new AdvancedModelRenderer(this, 106, 9);
          this.field_110697_J.setRotationPoint(0.0F, 2.0F, 2.0F);
          this.field_110697_J.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2, 0.0F);
          this.field_110699_Q = new AdvancedModelRenderer(this, 74, 13);
          this.field_110699_Q.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110699_Q.addBox(-2.5F, -8.0F, -4.0F, 1, 2, 2, 0.0F);
          this.setRotateAngle(field_110699_Q, 0.5235987901687622F, 0.0F, 0.0F);
          this.field_110693_N = new AdvancedModelRenderer(this, 80, 0);
          this.field_110693_N.setRotationPoint(-5.0F, 3.0F, 2.0F);
          this.field_110693_N.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
          this.field_110705_d = new AdvancedModelRenderer(this, 0, 0);
          this.field_110705_d.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110705_d.addBox(0.44999998807907104F, -12.0F, 4.0F, 2, 3, 1, 0.0F);
          this.setRotateAngle(field_110705_d, 0.5235987901687622F, 0.0F, 0.0F);
          this.field_110713_m = new AdvancedModelRenderer(this, 38, 7);
          this.field_110713_m.setRotationPoint(0.0F, 3.0F, 14.0F);
          this.field_110713_m.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7, 0.0F);
          this.setRotateAngle(field_110713_m, -1.3089969158172607F, 0.0F, 0.0F);
          this.field_110691_L = new AdvancedModelRenderer(this, 70, 0);
          this.field_110691_L.setRotationPoint(5.0F, 3.0F, 2.0F);
          this.field_110691_L.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
          this.field_110709_aChild_1 = new AdvancedModelRenderer(this, 24, 27);
          this.field_110709_aChild_1.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.field_110709_aChild_1.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5, 0.0F);
          this.fr_hoof = new AdvancedModelRenderer(this, 44, 51);
          this.fr_hoof.setRotationPoint(0.0F, 0.0F, 0.0F);
          this.fr_hoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4, 0.0F);
          this.field_110700_P = new AdvancedModelRenderer(this, 74, 13);
          this.field_110700_P.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110700_P.addBox(1.5F, -8.0F, -4.0F, 1, 2, 2, 0.0F);
          this.setRotateAngle(field_110700_P, 0.5235987901687622F, 0.0F, 0.0F);
          this.r_wing_feathers = new AdvancedModelRenderer(this, 0, 69);
          this.r_wing_feathers.setRotationPoint(-4.7F, 0.0F, 0.5F);
          this.r_wing_feathers.addBox(0.0F, 0.0F, 0.0F, 33, 18, 0, 0.0F);
          this.field_110692_M = new AdvancedModelRenderer(this, 74, 0);
          this.field_110692_M.setRotationPoint(5.0F, 3.0F, 2.0F);
          this.field_110692_M.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2, 0.0F);
          this.fl_shoulder = new AdvancedModelRenderer(this, 44, 29);
          this.fl_shoulder.setRotationPoint(4.4F, -2.0F, -17.3F);
          this.fl_shoulder.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4, 0.0F);
          this.fr_shin = new AdvancedModelRenderer(this, 44, 41);
          this.fr_shin.setRotationPoint(0.1F, 6.0F, 0.0F);
          this.fr_shin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3, 0.0F);
          this.field_110712_l = new AdvancedModelRenderer(this, 44, 0);
          this.field_110712_l.setRotationPoint(0.0F, 3.0F, 14.0F);
          this.field_110712_l.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3, 0.0F);
          this.setRotateAngle(field_110712_l, -1.3089969158172607F, 0.0F, 0.0F);
          this.field_110714_j = new AdvancedModelRenderer(this, 58, 0);
          this.field_110714_j.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110714_j.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4, 0.0F);
          this.setRotateAngle(field_110714_j, 0.5235987901687622F, 0.0F, 0.0F);
          this.field_110702_R = new AdvancedModelRenderer(this, 44, 10);
          this.field_110702_R.setRotationPoint(0.0F, 4.0F, -10.0F);
          this.field_110702_R.addBox(2.5999999046325684F, -6.0F, -6.0F, 0, 3, 16, 0.0F);
          this.body.addChild(this.br_shoulder);
          this.l_wing.addChild(this.l_wing_fearhers);
          this.bl_shin.addChild(this.bl_hoof);
          this.field_110709_a.addChild(this.field_110709_aChild);
          this.bl_shoulder.addChild(this.bl_shin);
          this.body.addChild(this.r_wing);
          this.fl_shoulder.addChild(this.fl_shin);
          this.r_wing.addChild(this.r_wing_2);
          this.fl_shin.addChild(this.fl_hoof);
          this.br_shin.addChild(this.br_hoof);
          this.body.addChild(this.l_wing);
          this.l_wing.addChild(this.l_wing_2);
          this.br_shoulder.addChild(this.br_shin);
          this.body.addChild(this.fr_shoulder);
          this.field_110709_a.addChild(this.field_110709_aChild_1);
          this.fr_shin.addChild(this.fr_hoof);
          this.r_wing.addChild(this.r_wing_feathers);
          this.body.addChild(this.fl_shoulder);
          this.fr_shoulder.addChild(this.fr_shin);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
         
          this.field_110687_G.render(f5);
          this.field_110694_O.render(f5);
          this.field_110698_K.render(f5);
          this.field_110709_a.render(f5);
          this.field_110706_e.render(f5);
          this.field_110695_H.render(f5);
          this.field_110716_h.render(f5);
          this.field_110717_i.render(f5);
          this.field_110704_g.render(f5);
          this.field_110701_S.render(f5);
          this.field_110710_n.render(f5);
          this.field_110696_I.render(f5);
          this.field_110703_f.render(f5);
          this.bl_shoulder.render(f5);
          this.field_110697_J.render(f5);
          this.field_110699_Q.render(f5);
          this.field_110693_N.render(f5);
          this.field_110705_d.render(f5);
          this.field_110713_m.render(f5);
          this.field_110691_L.render(f5);
          this.field_110700_P.render(f5);
          this.field_110692_M.render(f5);
          this.field_110712_l.render(f5);
          this.field_110714_j.render(f5);
          this.field_110702_R.render(f5);
          this.body.render(f5);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    		float headPitch, float scaleFactor, Entity entityIn) {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	resetToDefaultPose();

    	float f = entityIn.ticksExisted;
        float f1 = 1;
    	float speed = .3f;
        float height = .2f;
        float degree =  2f;
        float flapHeight = .5f;
        
        boolean isFlying = true;
        
        if(isFlying) {
        	// bob(body, speed, degree, false, f, f1);
        	
        	 flap(r_wing, speed, flapHeight, false, 0, -.3F, f, f1);
             walk(r_wing, speed, degree / 8, false, -1F, 0, f, f1);
             
             flap(l_wing, speed, flapHeight, false, 3.6F, .34F, f, f1);
             walk(l_wing, speed, degree / 8, false, 1F, 0, f, f1);
             
             fl_shoulder.rotateAngleX = .5f;
             fl_shin.rotateAngleX = .7f;
             
             fr_shoulder.rotateAngleX = .5f;
             fr_shin.rotateAngleX = .7f;
             
             bl_shoulder.rotateAngleX = .5f;
             bl_shin.rotateAngleX = .7f;
             
             br_shoulder.rotateAngleX = .5f;
             br_shin.rotateAngleX = .7f;
        }else {
        	
        }
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
