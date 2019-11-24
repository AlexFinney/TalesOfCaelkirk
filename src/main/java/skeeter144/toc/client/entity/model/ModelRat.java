package skeeter144.toc.client.entity.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import skeeter144.toc.client.entity.renderer.AdvancedModelRenderer;
import skeeter144.toc.entity.mob.monster.EntityRat;

public class ModelRat extends AdvancedModelBase<EntityRat> {
	public AdvancedModelRenderer body;
	public AdvancedModelRenderer head;
	public AdvancedModelRenderer tail1;
	public AdvancedModelRenderer front_left_leg;
	public AdvancedModelRenderer back_left_leg;
	public AdvancedModelRenderer front_right_leg;
	public AdvancedModelRenderer back_right_leg;
	public AdvancedModelRenderer nose;
	public AdvancedModelRenderer right_ear;
	public AdvancedModelRenderer left_ear;
	public AdvancedModelRenderer tail2;
	public AdvancedModelRenderer tail3;
	public AdvancedModelRenderer tail4;

	public ModelRat() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.tail1 = new AdvancedModelRenderer(this, 0, 0);
		this.tail1.setRotationPoint(2.0F, 1.0F, 9.0F);
		this.tail1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.setRotateAngle(tail1, 0.0F, -0.02638046198805339F, 0.0F);
		this.front_left_leg = new AdvancedModelRenderer(this, 0, 0);
		this.front_left_leg.setRotationPoint(5.0F, 1.3F, 1.0F);
		this.front_left_leg.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.left_ear = new AdvancedModelRenderer(this, 4, 16);
		this.left_ear.setRotationPoint(2.4F, -1.1F, 1.3F);
		this.left_ear.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.tail3 = new AdvancedModelRenderer(this, 0, 0);
		this.tail3.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.tail3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.body = new AdvancedModelRenderer(this, 0, 0);
		this.body.setRotationPoint(-2.0F, 18.5F, -3.0F);
		this.body.addBox(0.0F, 0.0F, 0.0F, 5, 4, 9, 0.0F);
		this.back_right_leg = new AdvancedModelRenderer(this, 0, 0);
		this.back_right_leg.setRotationPoint(-1.0F, 1.2F, 7.0F);
		this.back_right_leg.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.back_left_leg = new AdvancedModelRenderer(this, 0, 0);
		this.back_left_leg.setRotationPoint(5.0F, 1.3F, 7.0F);
		this.back_left_leg.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.front_right_leg = new AdvancedModelRenderer(this, 0, 0);
		this.front_right_leg.setRotationPoint(-1.0F, 1.5F, 1.0F);
		this.front_right_leg.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.head = new AdvancedModelRenderer(this, 0, 16);
		this.head.setRotationPoint(1.0F, 0.3F, -3.0F);
		this.head.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.0F);
		this.tail4 = new AdvancedModelRenderer(this, 0, 0);
		this.tail4.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.tail4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.nose = new AdvancedModelRenderer(this, 0, 22);
		this.nose.setRotationPoint(1.0F, 1.0F, -1.3F);
		this.nose.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.right_ear = new AdvancedModelRenderer(this, 4, 16);
		this.right_ear.setRotationPoint(-0.6F, -1.1F, 1.3F);
		this.right_ear.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.tail2 = new AdvancedModelRenderer(this, 0, 0);
		this.tail2.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.tail2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
		this.body.addChild(this.tail1);
		this.body.addChild(this.front_left_leg);
		this.head.addChild(this.left_ear);
		this.tail2.addChild(this.tail3);
		this.body.addChild(this.back_right_leg);
		this.body.addChild(this.back_left_leg);
		this.body.addChild(this.front_right_leg);
		this.body.addChild(this.head);
		this.tail3.addChild(this.tail4);
		this.head.addChild(this.nose);
		this.head.addChild(this.right_ear);
		this.tail1.addChild(this.tail2);

		updateDefaultPose();
	}

	@Override
	public void render(EntityRat entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.body.render(f5);
	}

	@Override
	public void setRotationAngles(EntityRat entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scaleFactor) {
		resetToDefaultPose();

		float f = limbSwing;
		float f1 = limbSwingAmount;

		float speed = 1.3f;
		float height = .5f;
		float degree = 1.5f;

		bob(body, speed, height, false, f, f1);
		walk(back_left_leg, 0.5f * speed, .5f * degree, false, 0, 0, f, f1);
		walk(front_left_leg, 0.5f * speed, .5f * degree, true, 0, 0, f, f1);
		walk(back_right_leg, 0.5f * speed, .5f * degree, true, 0, 0, f, f1);
		walk(front_right_leg, 0.5f * speed, .5f * degree, false, 0, 0, f, f1);

		walk(tail1, speed, .1f * degree, true, 0, 0, f, f1);
		walk(tail2, speed, .2f * degree, true, 0, 0, f, f1);
		walk(tail3, speed, .3f * degree, true, 0, 0, f, f1);

		swing(tail1, speed, .2f * degree, true, 0, 0, f, f1);
		swing(tail2, speed, .3f * degree, true, 0, 0, f, f1);
		swing(tail3, speed, .4f * degree, true, 0, 0, f, f1);

		walk(head, speed, .1f, false, 0, 0, f, f1);
	}

	public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}
