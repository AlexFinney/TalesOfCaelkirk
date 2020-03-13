package skeeter144.toc.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import skeeter144.toc.client.entity.renderer.AdvancedModelRenderer;
import skeeter144.toc.entity.mob.monster.EntityGoblin;

/**
 * Goblin - Skeeter144
 * Created using Tabula 5.1.0
 */
public class ModelGoblin<T extends EntityGoblin> extends EntityModel<EntityGoblin>
{
    public AdvancedModelRenderer body;
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer waist;
    public AdvancedModelRenderer left_arm;
    public AdvancedModelRenderer left_arm_1;
    public AdvancedModelRenderer left_eyebrow;
    public AdvancedModelRenderer left_ear;
    public AdvancedModelRenderer right_ear;
    public AdvancedModelRenderer right_eyebrow;
    public AdvancedModelRenderer nose_1;
    public AdvancedModelRenderer left_ear_2;
    public AdvancedModelRenderer left_ear_3;
    public AdvancedModelRenderer left_ear_1;
    public AdvancedModelRenderer left_ear_4;
    public AdvancedModelRenderer left_ear_5;
    public AdvancedModelRenderer left_ear_2_1;
    public AdvancedModelRenderer left_ear_3_1;
    public AdvancedModelRenderer left_ear_1_1;
    public AdvancedModelRenderer left_ear_4_1;
    public AdvancedModelRenderer left_ear_5_1;
    public AdvancedModelRenderer nose_2;
    public AdvancedModelRenderer left_leg;
    public AdvancedModelRenderer left_leg_1;
    public AdvancedModelRenderer left_foot;
    public AdvancedModelRenderer left_foot_1;
    public AdvancedModelRenderer left_shoulder;
    public AdvancedModelRenderer left_hand;
    public AdvancedModelRenderer left_shoulder_1;
    public AdvancedModelRenderer left_hand_1;

    public ModelGoblin() {
    	this.textureWidth = 64;
        this.textureHeight = 64;
        this.left_ear = new AdvancedModelRenderer(this, 26, 18);
        this.left_ear.setRotationPoint(3.0F, -14.0F, 0.0F);
        this.left_ear.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear, 0.0F, 0.0F, 0.1038470904936626F);
        this.left_eyebrow = new AdvancedModelRenderer(this, 0, 0);
        this.left_eyebrow.setRotationPoint(-1.2F, -12.2F, -9.0F);
        this.left_eyebrow.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(left_eyebrow, 0.0F, 0.0F, -2.0943951023931953F);
        this.left_ear_2 = new AdvancedModelRenderer(this, 36, 29);
        this.left_ear_2.setRotationPoint(5.8F, 0.0F, 0.0F);
        this.left_ear_2.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_2, 0.0F, 0.0F, 0.22759093446006054F);
        this.left_ear_1_1 = new AdvancedModelRenderer(this, 24, 20);
        this.left_ear_1_1.setRotationPoint(5.8F, 0.0F, 0.0F);
        this.left_ear_1_1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_1_1, 0.0F, 0.0F, 0.22759093446006054F);
        this.right_ear = new AdvancedModelRenderer(this, 26, 18);
        this.right_ear.setRotationPoint(-7.7F, -14.0F, 0.0F);
        this.right_ear.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(right_ear, 0.0F, -3.141592653589793F, -0.08185594191853406F);
        this.left_ear_3_1 = new AdvancedModelRenderer(this, 36, 9);
        this.left_ear_3_1.setRotationPoint(0.0F, 1.6F, -0.1F);
        this.left_ear_3_1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_3_1, 0.0F, 0.0F, 0.045553093477052F);
        this.left_ear_5 = new AdvancedModelRenderer(this, 26, 15);
        this.left_ear_5.setRotationPoint(0.0F, 3.5F, -0.1F);
        this.left_ear_5.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_5, 0.0F, 0.0F, 0.045553093477052F);
        this.left_foot_1 = new AdvancedModelRenderer(this, 0, 50);
        this.left_foot_1.setRotationPoint(-0.5F, 9.0F, -1.1F);
        this.left_foot_1.addBox(0.0F, 0.0F, 0.0F, 5, 8, 6, 0.0F);
        this.setRotateAngle(left_foot_1, 0.0F, 0.0F, 0.14364059743913332F);
        this.left_ear_4_1 = new AdvancedModelRenderer(this, 26, 16);
        this.left_ear_4_1.setRotationPoint(5.0F, 1.6F, -0.3F);
        this.left_ear_4_1.addBox(0.0F, 0.0F, 0.0F, 4, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_4_1, 0.0F, 0.0F, 0.22759093446006054F);
        this.body = new AdvancedModelRenderer(this, 0, 23);
        this.body.setRotationPoint(0.0F, 2.7F, 0.0F);
        this.body.addBox(-11.0F, -18.0F, -8.0F, 17, 14, 11, 0.0F);
        this.left_ear_4 = new AdvancedModelRenderer(this, 26, 16);
        this.left_ear_4.setRotationPoint(5.0F, 1.6F, 0.4F);
        this.left_ear_4.addBox(0.0F, 0.0F, 0.0F, 4, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_4, 0.0F, 0.0F, 0.22759093446006054F);
        this.right_eyebrow = new AdvancedModelRenderer(this, 4, 0);
        this.right_eyebrow.setRotationPoint(-3.2F, -13.1F, -9.0F);
        this.right_eyebrow.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(right_eyebrow, 0.0F, 0.0F, -4.1887902047863905F);
        this.left_ear_1 = new AdvancedModelRenderer(this, 24, 20);
        this.left_ear_1.setRotationPoint(5.8F, 0.0F, 0.0F);
        this.left_ear_1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_1, 0.0F, 0.0F, 0.22759093446006054F);
        this.left_ear_5_1 = new AdvancedModelRenderer(this, 30, 15);
        this.left_ear_5_1.setRotationPoint(0.0F, 3.5F, 0.0F);
        this.left_ear_5_1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_5_1, 0.0F, 0.0F, 0.045553093477052F);
        this.left_shoulder = new AdvancedModelRenderer(this, 44, 19);
        this.left_shoulder.setRotationPoint(0.0F, -2.0F, -2.0F);
        this.left_shoulder.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.left_hand = new AdvancedModelRenderer(this, 20, 0);
        this.left_hand.setRotationPoint(0.0F, 6.5F, -1.4F);
        this.left_hand.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.setRotateAngle(left_hand, -0.4553564018453205F, 0.0F, 0.0F);
        this.waist = new AdvancedModelRenderer(this, 20, 38);
        this.waist.setRotationPoint(-3.0F, -4.0F, -1.0F);
        this.waist.addBox(-5.0F, 0.0F, -6.0F, 12, 9, 10, 0.0F);
        this.left_arm = new AdvancedModelRenderer(this, 20, 0);
        this.left_arm.setRotationPoint(6.0F, -17.0F, -4.0F);
        this.left_arm.addBox(0.0F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
        this.left_hand_1 = new AdvancedModelRenderer(this, 20, 0);
        this.left_hand_1.setRotationPoint(0.0F, 6.2F, -1.2F);
        this.left_hand_1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.setRotateAngle(left_hand_1, -0.4553564018453205F, 0.0F, 0.0F);
        this.left_foot = new AdvancedModelRenderer(this, 0, 50);
        this.left_foot.setRotationPoint(-0.5F, 9.0F, -1.1F);
        this.left_foot.addBox(0.0F, 0.0F, 0.0F, 5, 8, 6, 0.0F);
        this.setRotateAngle(left_foot, 0.0F, 0.0F, 0.14364059743913332F);
        this.left_arm_1 = new AdvancedModelRenderer(this, 20, 0);
        this.left_arm_1.setRotationPoint(-14.0F, -17.0F, -4.0F);
        this.left_arm_1.addBox(0.0F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
        this.left_ear_3 = new AdvancedModelRenderer(this, 36, 5);
        this.left_ear_3.setRotationPoint(0.0F, 1.6F, 0.5F);
        this.left_ear_3.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_3, 0.0F, 0.0F, 0.045553093477052F);
        this.nose_1 = new AdvancedModelRenderer(this, 20, 0);
        this.nose_1.setRotationPoint(-3.0F, -10.2F, -8.0F);
        this.nose_1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(nose_1, -1.1838568316277536F, 0.0F, 0.0F);
        this.head = new AdvancedModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -13.0F, -3.0F);
        this.head.addBox(-8.0F, -16.0F, -8.0F, 12, 11, 12, 0.0F);
        this.left_shoulder_1 = new AdvancedModelRenderer(this, 44, 19);
        this.left_shoulder_1.setRotationPoint(-1.0F, -2.0F, -2.0F);
        this.left_shoulder_1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.left_leg_1 = new AdvancedModelRenderer(this, 20, 0);
        this.left_leg_1.setRotationPoint(2.0F, 8.0F, -3.0F);
        this.left_leg_1.addBox(0.0F, 0.0F, 0.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(left_leg_1, 0.0F, 0.0F, -0.136659280431156F);
        this.left_ear_2_1 = new AdvancedModelRenderer(this, 48, 38);
        this.left_ear_2_1.setRotationPoint(5.8F, 0.0F, 0.0F);
        this.left_ear_2_1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.setRotateAngle(left_ear_2_1, 0.0F, 0.0F, 0.22759093446006054F);
        this.nose_2 = new AdvancedModelRenderer(this, 20, 0);
        this.nose_2.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.nose_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(nose_2, 0.36425021489121656F, 0.0F, 0.0F);
        this.left_leg = new AdvancedModelRenderer(this, 20, 0);
        this.left_leg.setRotationPoint(1.0F, 8.0F, 1.0F);
        this.left_leg.addBox(0.0F, 0.0F, 0.0F, 4, 10, 4, 0.0F);
        this.setRotateAngle(left_leg, 0.0F, 3.141592653589793F, 0.13840460968315033F);
        this.head.addChild(this.left_ear);
        this.head.addChild(this.left_eyebrow);
        this.left_ear.addChild(this.left_ear_2);
        this.right_ear.addChild(this.left_ear_1_1);
        this.head.addChild(this.right_ear);
        this.right_ear.addChild(this.left_ear_3_1);
        this.left_ear.addChild(this.left_ear_5);
        this.left_leg_1.addChild(this.left_foot_1);
        this.right_ear.addChild(this.left_ear_4_1);
        this.left_ear.addChild(this.left_ear_4);
        this.head.addChild(this.right_eyebrow);
        this.left_ear.addChild(this.left_ear_1);
        this.right_ear.addChild(this.left_ear_5_1);
        this.left_arm.addChild(this.left_shoulder);
        this.left_arm.addChild(this.left_hand);
        this.body.addChild(this.waist);
        this.body.addChild(this.left_arm);
        this.left_arm_1.addChild(this.left_hand_1);
        this.left_leg.addChild(this.left_foot);
        this.body.addChild(this.left_arm_1);
        this.left_ear.addChild(this.left_ear_3);
        this.head.addChild(this.nose_1);
        this.body.addChild(this.head);
        this.left_arm_1.addChild(this.left_shoulder_1);
        this.waist.addChild(this.left_leg_1);
        this.right_ear.addChild(this.left_ear_2_1);
        this.nose_1.addChild(this.nose_2);
        this.waist.addChild(this.left_leg);
        this.body.setScale(.5f, .5f, .5f);
        //updateDefaultPose();
    }

    @Override
    public void setRotationAngles(EntityGoblin entityGoblin, float v, float v1, float v2, float v3, float v4)
    {

    }
/*
    @Override
    public void render(EntityGoblin entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	body.scaleChildren = true;
    	body.defaultOffsetY = .7f;
        this.body.render(f5);
    }

    
    @Override
    public void setRotationAngles(EntityGoblin entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    		float headPitch, float scaleFactor) {
    	resetToDefaultPose();
    	
    	float f = limbSwing;
        float f1 = limbSwingAmount;
          
        float speed = 2f;
        float height = 1.5f;
        float degree = 1.5f;
    	
        bob(body, speed, height, false, f, f1);
        walk(left_leg, speed / 2, degree, false, 0, 0, f, f1);
        walk(left_leg_1, speed / 2, degree, false, 0, 0, f, f1);

        walk(left_arm_1, speed / 2, degree, false, 0, 0, f, f1);
        walk(left_arm, speed / 2, degree, true, 0, 0, f, f1);
        
        swing(body, speed / 2, .05f * degree, true, 0, 0, f, f1);
        bob(head, speed, height * .1f, false, f, f1);
    }
   */
    public void setRotateAngle(AdvancedModelRenderer RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3)
    {

    }
}
