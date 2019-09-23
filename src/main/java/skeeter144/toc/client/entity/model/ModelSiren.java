package skeeter144.toc.client.entity.model;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.entity.Entity;
import skeeter144.toc.entity.mob.monster.EntitySiren;

public class ModelSiren extends AdvancedModelBase {
	private final AdvancedModelRenderer Base;
	private final AdvancedModelRenderer Body;
	private final AdvancedModelRenderer NeckBone;
	private final AdvancedModelRenderer HeadBone;
	private final AdvancedModelRenderer Tail;
	private final AdvancedModelRenderer Tail2;
	private final AdvancedModelRenderer Tail3;
	private final AdvancedModelRenderer Tail4;
	private final AdvancedModelRenderer Fin;
	private final AdvancedModelRenderer FinLeft;
	private final AdvancedModelRenderer FinRight;
	private final AdvancedModelRenderer RightArmBone;
	private final AdvancedModelRenderer LeftArmBone;

	public ModelSiren() {
		textureWidth = 64;
		textureHeight = 64;

		Base = new AdvancedModelRenderer(this);
		Base.setRotationPoint(0.0F, 13.0F, -3.0F);
		setRotateAngle(Base, 0.0F, 3.1416F, 0.0F);

		Body = new AdvancedModelRenderer(this);
		Body.setRotationPoint(0.0F, 6.0F, 0.0F);
		Base.addChild(Body);
		Body.cubeList.add(new ModelBox(Body, 0, 36, -4.0F, -7.0F, -4.0F, 8, 6, 8, 0.0F, false));

		NeckBone = new AdvancedModelRenderer(this);
		NeckBone.setRotationPoint(0.0F, -10.0F, 0.0F);
		Body.addChild(NeckBone);
		NeckBone.cubeList.add(new ModelBox(NeckBone, 38, 17, -3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F, false));

		HeadBone = new AdvancedModelRenderer(this);
		HeadBone.setRotationPoint(0.0F, -4.0F, 0.0F);
		NeckBone.addChild(HeadBone);
		HeadBone.cubeList.add(new ModelBox(HeadBone, 32, 36, -4.0F, -2.0F, -4.0F, 8, 7, 8, 0.0F, false));

		Tail = new AdvancedModelRenderer(this);
		Tail.setRotationPoint(0.0F, 2.0F, 0.0F);
		Body.addChild(Tail);
		Tail.cubeList.add(new ModelBox(Tail, 0, 53, -3.0F, -3.0F, -3.0F, 6, 5, 6, 0.0F, false));

		Tail2 = new AdvancedModelRenderer(this);
		Tail2.setRotationPoint(0.0F, 0.0F, -1.0F);
		Tail.addChild(Tail2);
		Tail2.cubeList.add(new ModelBox(Tail2, 32, 0, -2.0F, -2.0F, -8.0F, 4, 5, 10, 0.0F, false));

		Tail3 = new AdvancedModelRenderer(this);
		Tail3.setRotationPoint(0.0F, 1.0F, -8.0F);
		Tail2.addChild(Tail3);
		Tail3.cubeList.add(new ModelBox(Tail3, 27, 51, -2.0F, -2.0F, -5.0F, 4, 4, 5, 0.0F, false));

		Tail4 = new AdvancedModelRenderer(this);
		Tail4.setRotationPoint(0.0F, 1.0F, -5.0F);
		Tail3.addChild(Tail4);
		Tail4.cubeList.add(new ModelBox(Tail4, 0, 27, -2.0F, -2.0F, -5.0F, 4, 3, 5, 0.0F, false));

		Fin = new AdvancedModelRenderer(this);
		Fin.setRotationPoint(0.0F, 1.0F, -5.0F);
		Tail4.addChild(Fin);

		FinLeft = new AdvancedModelRenderer(this);
		FinLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotateAngle(FinLeft, 0.0F, 0.0F, 0.0873F);
		Fin.addChild(FinLeft);
		FinLeft.cubeList.add(new ModelBox(FinLeft, 17, 27, -6.0F, -1.0F, -9.0F, 6, 0, 9, 0.0F, true));

		FinRight = new AdvancedModelRenderer(this);
		FinRight.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotateAngle(FinRight, 0.0F, 0.0F, -0.0873F);
		Fin.addChild(FinRight);
		FinRight.cubeList.add(new ModelBox(FinRight, 17, 27, 0.0F, -1.0F, -9.0F, 6, 0, 9, 0.0F, false));

		RightArmBone = new AdvancedModelRenderer(this);
		RightArmBone.setRotationPoint(6.0F, 0.0F, 0.0F);
		setRotateAngle(RightArmBone, -1.5708F, 0.0F, 0.0F);
		Base.addChild(RightArmBone);
		RightArmBone.cubeList.add(new ModelBox(RightArmBone, 0, 10, -2.0F, -2.0F, -1.0F, 2, 4, 10, 0.0F, false));

		LeftArmBone = new AdvancedModelRenderer(this);
		LeftArmBone.setRotationPoint(-6.0F, 0.0F, 1.0F);
		setRotateAngle(LeftArmBone, -1.5708F, 0.0F, 0.0F);
		Base.addChild(LeftArmBone);
		LeftArmBone.cubeList.add(new ModelBox(LeftArmBone, 0, 10, 0.0F, -1.0F, -1.0F, 2, 4, 10, 0.0F, false));
		
		updateDefaultPose();
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Base.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		resetToDefaultPose();
		if(entityIn.isInWater() || entityIn.isOverWater()) {
			walk(Tail, .25f, .2f, false, 0, 7F, entityIn.ticksExisted, .2f);
			walk(Tail2, .25f, .5f, false, 0, .1F, entityIn.ticksExisted, .5f);
			walk(Tail3, .25f, .5f, false, 0, .1F, entityIn.ticksExisted, .5f);
			walk(Tail4, .25f, .5f, false, 0, .1F, entityIn.ticksExisted, 1f);
		}
		
		if( ((EntitySiren)entityIn).getIsRapidSwim()) 
		{
			setRotateAngle(Base, (float)Math.PI, 0, 0);
		}
		else 
		{
			
		}
		
	}
	
	 public void setRotateAngle(AdvancedModelRenderer modelRenderer, float x, float y, float z) {
	        modelRenderer.rotateAngleX = x;
	        modelRenderer.rotateAngleY = y;
	        modelRenderer.rotateAngleZ = z;
	    }
}