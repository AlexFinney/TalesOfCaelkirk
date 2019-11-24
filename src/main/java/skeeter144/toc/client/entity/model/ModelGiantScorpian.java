package skeeter144.toc.client.entity.model;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import skeeter144.toc.client.entity.animation.Animation;
import skeeter144.toc.client.entity.animation.Animation.AnimationState;
import skeeter144.toc.client.entity.animation.Animations;
import skeeter144.toc.client.entity.animation.KeyFrame;
import skeeter144.toc.client.entity.animation.PartOrientation;
import skeeter144.toc.client.entity.renderer.AdvancedModelRenderer;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.entity.mob.monster.EntityGiantScorpian;
import skeeter144.toc.network.AnimationEventPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.util.CustomRunnable;

/**
 * GiantScorpian - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelGiantScorpian extends AdvancedModelBase<EntityGiantScorpian> {
    public AdvancedModelRenderer body_1;
    public AdvancedModelRenderer left_claw_1;
    public AdvancedModelRenderer body_2;
    public AdvancedModelRenderer left_middle_leg_1;
    public AdvancedModelRenderer left_front_leg_1;
    public AdvancedModelRenderer right_middle_leg_1;
    public AdvancedModelRenderer right_front_leg_1;
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer left_claw_1_1;
    public AdvancedModelRenderer tail_base;
    public AdvancedModelRenderer left_back_leg_1;
    public AdvancedModelRenderer right_back_leg_1;
    public AdvancedModelRenderer tail_1;
    public AdvancedModelRenderer tail_2;
    public AdvancedModelRenderer tail_3;
    public AdvancedModelRenderer tail_4;
    public AdvancedModelRenderer tail_5;
    public AdvancedModelRenderer tail_6;
    public AdvancedModelRenderer tail7;
    public AdvancedModelRenderer tail_8;
    public AdvancedModelRenderer stinger_base;
    public AdvancedModelRenderer stinger_1;
    public AdvancedModelRenderer stinger_2;
    public AdvancedModelRenderer stinger_3;
    public AdvancedModelRenderer left_back_leg_2;
    public AdvancedModelRenderer left_back_leg_3;
    public AdvancedModelRenderer right_back_leg_2;
    public AdvancedModelRenderer right_back_leg_3;
    public AdvancedModelRenderer left_middle_leg_2;
    public AdvancedModelRenderer left_middle_leg_3;
    public AdvancedModelRenderer left_front_leg_2;
    public AdvancedModelRenderer left_front_leg_3;
    public AdvancedModelRenderer right_middle_leg_2;
    public AdvancedModelRenderer right_middle_leg_3;
    public AdvancedModelRenderer right_front_leg_2;
    public AdvancedModelRenderer right_front_leg_3;
    public AdvancedModelRenderer left_claw_2;
    public AdvancedModelRenderer left_claw_3;
    public AdvancedModelRenderer left_claw_4;
    public AdvancedModelRenderer left_claw_5;
    public AdvancedModelRenderer left_claw_6;
    public AdvancedModelRenderer left_claw_2_1;
    public AdvancedModelRenderer left_claw_3_1;
    public AdvancedModelRenderer left_claw_4_1;
    public AdvancedModelRenderer left_claw_5_1;
    public AdvancedModelRenderer left_claw_6_1;

    public ModelGiantScorpian() {
    	 this.textureWidth = 128;
         this.textureHeight = 128;
         this.right_back_leg_1 = new AdvancedModelRenderer(this, 0, 22);
         this.right_back_leg_1.setRotationPoint(-7.0F, -0.4F, -0.1F);
         this.right_back_leg_1.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
         this.setRotateAngle(right_back_leg_1, 0.0F, 3.141592653589793F, 0.25761059759436306F);
         this.right_middle_leg_3 = new AdvancedModelRenderer(this, 0, 25);
         this.right_middle_leg_3.setRotationPoint(5.4F, -0.8F, 0.0F);
         this.right_middle_leg_3.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
         this.setRotateAngle(right_middle_leg_3, 0.0F, 0.0F, 1.1527899709422544F);
         this.left_claw_3 = new AdvancedModelRenderer(this, 0, 23);
         this.left_claw_3.setRotationPoint(5.3F, -0.1F, 0.0F);
         this.left_claw_3.addBox(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
         this.setRotateAngle(left_claw_3, 0.0F, 0.0F, 0.38798669271833947F);
         this.left_front_leg_2 = new AdvancedModelRenderer(this, 0, 24);
         this.left_front_leg_2.setRotationPoint(6.6F, -0.1F, 0.0F);
         this.left_front_leg_2.addBox(0.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         this.setRotateAngle(left_front_leg_2, 0.0F, 0.0F, 0.6263986685407649F);
         this.left_claw_4 = new AdvancedModelRenderer(this, 0, 8);
         this.left_claw_4.setRotationPoint(8.0F, -3.2F, -2.9F);
         this.left_claw_4.addBox(0.0F, 0.0F, 0.0F, 8, 6, 6, 0.0F);
         this.setRotateAngle(left_claw_4, 0.0F, -0.03717551306747922F, 0.0F);
         this.left_claw_5_1 = new AdvancedModelRenderer(this, 0, 0);
         this.left_claw_5_1.setRotationPoint(7.9F, 1.3F, 0.8F);
         this.left_claw_5_1.addBox(0.0F, 0.0F, 0.0F, 7, 3, 1, 0.0F);
         this.setRotateAngle(left_claw_5_1, 0.0F, 0.13002702927357757F, 0.0F);
         this.left_middle_leg_2 = new AdvancedModelRenderer(this, 0, 25);
         this.left_middle_leg_2.setRotationPoint(6.6F, -0.1F, 0.0F);
         this.left_middle_leg_2.addBox(0.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         this.setRotateAngle(left_middle_leg_2, 0.0F, 0.0F, 0.6263986685407649F);
         this.body_1 = new AdvancedModelRenderer(this, 0, 21);
         this.body_1.setRotationPoint(0.0F, 15.3F, -5.0F);
         this.body_1.addBox(-9.3F, -4.0F, -8.0F, 19, 9, 24, 0.0F);
         this.left_claw_1_1 = new AdvancedModelRenderer(this, 0, 30);
         this.left_claw_1_1.setRotationPoint(8.3F, 0.5F, -4.5F);
         this.left_claw_1_1.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
         this.setRotateAngle(left_claw_1_1, -1.5988961277520053F, -0.17383479349863523F, 0.09005898940290741F);
         this.head = new AdvancedModelRenderer(this, 31, 0);
         this.head.setRotationPoint(0.0F, -1.3F, -8.0F);
         this.head.addBox(-4.5F, -4.0F, -5.5F, 9, 8, 6, 0.0F);
         this.tail_5 = new AdvancedModelRenderer(this, 36, 36);
         this.tail_5.setRotationPoint(0.5F, -6.0F, 1.3F);
         this.tail_5.addBox(-3.0F, -3.0F, 1.0F, 5, 7, 4, 0.0F);
         this.setRotateAngle(tail_5, 0.06265732014659643F, 0.0F, 0.0F);
         this.left_claw_4_1 = new AdvancedModelRenderer(this, 0, 8);
         this.left_claw_4_1.setRotationPoint(8.0F, -3.2F, -2.9F);
         this.left_claw_4_1.addBox(0.0F, 0.0F, 0.0F, 8, 6, 6, 0.0F);
         this.setRotateAngle(left_claw_4_1, 0.0F, -0.03717551306747922F, 0.0F);
         this.tail7 = new AdvancedModelRenderer(this, 24, 36);
         this.tail7.setRotationPoint(0.5F, -5.1F, -0.2F);
         this.tail7.addBox(-3.0F, -3.0F, 1.0F, 3, 7, 4, 0.0F);
         this.setRotateAngle(tail7, 0.4389503068765739F, 0.0F, 0.0F);
         this.stinger_base = new AdvancedModelRenderer(this, 0, 35);
         this.stinger_base.setRotationPoint(-1.0F, -1.1F, 1.2F);
         this.stinger_base.addBox(-3.0F, -3.0F, 1.0F, 4, 6, 4, 0.0F);
         this.setRotateAngle(stinger_base, 1.0793116094332933F, 0.0F, 0.0F);
         this.left_middle_leg_3 = new AdvancedModelRenderer(this, 0, 24);
         this.left_middle_leg_3.setRotationPoint(5.4F, -0.8F, 0.0F);
         this.left_middle_leg_3.addBox(0.0F, -1.0F, -1.0F, 10, 2, 2, 0.0F);
         this.setRotateAngle(left_middle_leg_3, 0.0F, 0.0F, 1.1527899709422544F);
         this.left_claw_5 = new AdvancedModelRenderer(this, 0, 0);
         this.left_claw_5.setRotationPoint(7.9F, 1.3F, 0.8F);
         this.left_claw_5.addBox(0.0F, 0.0F, 0.0F, 7, 3, 1, 0.0F);
         this.setRotateAngle(left_claw_5, 0.0F, 0.13002702927357757F, 0.0F);
         this.right_back_leg_3 = new AdvancedModelRenderer(this, 0, 26);
         this.right_back_leg_3.setRotationPoint(5.4F, -0.8F, 0.0F);
         this.right_back_leg_3.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
         this.setRotateAngle(right_back_leg_3, 0.0F, 0.0F, 1.1527899709422544F);
         this.tail_4 = new AdvancedModelRenderer(this, 40, 36);
         this.tail_4.setRotationPoint(0.0F, -4.1F, 3.7F);
         this.tail_4.addBox(-3.0F, -3.0F, 1.0F, 6, 7, 4, 0.0F);
         this.setRotateAngle(tail_4, 0.16720254234105675F, 0.0F, 0.0F);
         this.tail_8 = new AdvancedModelRenderer(this, 30, 36);
         this.tail_8.setRotationPoint(0.7F, -4.7F, -0.7F);
         this.tail_8.addBox(-3.0F, -3.0F, 1.0F, 2, 7, 3, 0.0F);
         this.setRotateAngle(tail_8, 0.4389503068765739F, 0.0F, 0.0F);
         this.right_front_leg_3 = new AdvancedModelRenderer(this, 0, 27);
         this.right_front_leg_3.setRotationPoint(5.4F, -0.8F, 0.0F);
         this.right_front_leg_3.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
         this.setRotateAngle(right_front_leg_3, 0.0F, 0.0F, 1.1527899709422544F);
         this.right_back_leg_2 = new AdvancedModelRenderer(this, 0, 31);
         this.right_back_leg_2.setRotationPoint(6.6F, -0.1F, 0.0F);
         this.right_back_leg_2.addBox(0.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         this.setRotateAngle(right_back_leg_2, 0.0F, 0.0F, 0.6263986685407649F);
         this.left_claw_3_1 = new AdvancedModelRenderer(this, 0, 24);
         this.left_claw_3_1.setRotationPoint(5.3F, -0.1F, 0.0F);
         this.left_claw_3_1.addBox(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
         this.setRotateAngle(left_claw_3_1, 0.0F, 0.0F, 0.38798669271833947F);
         this.left_claw_6_1 = new AdvancedModelRenderer(this, 0, 0);
         this.left_claw_6_1.setRotationPoint(7.6F, 1.4F, 3.3F);
         this.left_claw_6_1.addBox(0.0F, 0.0F, 0.0F, 7, 3, 1, 0.0F);
         this.setRotateAngle(left_claw_6_1, -0.018500490071139894F, -0.20455258833373544F, 0.0F);
         this.left_front_leg_1 = new AdvancedModelRenderer(this, 0, 30);
         this.left_front_leg_1.setRotationPoint(8.3F, -0.5F, 3.5F);
         this.left_front_leg_1.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
         this.setRotateAngle(left_front_leg_1, 0.0F, 0.0F, -0.25761059759436306F);
         this.tail_6 = new AdvancedModelRenderer(this, 31, 36);
         this.tail_6.setRotationPoint(0.5F, -5.1F, -0.2F);
         this.tail_6.addBox(-3.0F, -3.0F, 1.0F, 4, 7, 4, 0.0F);
         this.setRotateAngle(tail_6, 0.4389503068765739F, 0.0F, 0.0F);
         this.left_claw_2 = new AdvancedModelRenderer(this, 0, 22);
         this.left_claw_2.setRotationPoint(6.6F, -0.1F, 0.0F);
         this.left_claw_2.addBox(0.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         this.setRotateAngle(left_claw_2, 0.0F, 0.0F, 1.3390166021300496F);
         this.left_claw_1 = new AdvancedModelRenderer(this, 0, 23);
         this.left_claw_1.setRotationPoint(-5.7F, -0.5F, -4.5F);
         this.left_claw_1.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
         this.setRotateAngle(left_claw_1, -1.6732471538869638F, -0.136659280431156F, 3.119426972089465F);
         this.body_2 = new AdvancedModelRenderer(this, 17, 35);
         this.body_2.setRotationPoint(0.0F, -0.5F, 23.0F);
         this.body_2.addBox(-8.0F, -3.0F, -7.0F, 16, 8, 10, 0.0F);
         this.tail_2 = new AdvancedModelRenderer(this, 17, 36);
         this.tail_2.setRotationPoint(0.0F, -3.1F, 12.0F);
         this.tail_2.addBox(-4.5F, -3.0F, 1.0F, 9, 8, 9, 0.0F);
         this.stinger_1 = new AdvancedModelRenderer(this, 0, 0);
         this.stinger_1.setRotationPoint(1.5F, -1.9F, 1.1F);
         this.stinger_1.addBox(-3.0F, -3.0F, 1.0F, 1, 3, 1, 0.0F);
         this.setRotateAngle(stinger_1, 0.4389503068765739F, 0.0F, 0.0F);
         this.left_back_leg_3 = new AdvancedModelRenderer(this, 0, 30);
         this.left_back_leg_3.setRotationPoint(5.4F, -0.8F, 0.0F);
         this.left_back_leg_3.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
         this.setRotateAngle(left_back_leg_3, 0.0F, 0.0F, 1.1527899709422544F);
         this.stinger_2 = new AdvancedModelRenderer(this, 0, 0);
         this.stinger_2.setRotationPoint(0.0F, -2.3F, -0.3F);
         this.stinger_2.addBox(-3.0F, -3.0F, 1.0F, 1, 2, 1, 0.0F);
         this.setRotateAngle(stinger_2, -0.3647738136668149F, 0.0F, 0.0F);
         this.stinger_3 = new AdvancedModelRenderer(this, 0, 0);
         this.stinger_3.setRotationPoint(0.0F, -2.3F, -0.3F);
         this.stinger_3.addBox(-3.0F, -3.0F, 1.0F, 1, 2, 1, 0.0F);
         this.setRotateAngle(stinger_3, -0.3647738136668149F, 0.0F, 0.0F);
         this.tail_3 = new AdvancedModelRenderer(this, 32, 36);
         this.tail_3.setRotationPoint(0.0F, -4.1F, 7.3F);
         this.tail_3.addBox(-3.5F, -3.0F, 1.0F, 7, 7, 5, 0.0F);
         this.setRotateAngle(tail_3, 0.29269171555944906F, 0.0F, 0.0F);
         this.left_front_leg_3 = new AdvancedModelRenderer(this, 0, 23);
         this.left_front_leg_3.setRotationPoint(5.4F, -0.8F, 0.0F);
         this.left_front_leg_3.addBox(0.0F, -1.0F, -1.0F, 10, 2, 2, 0.0F);
         this.setRotateAngle(left_front_leg_3, 0.0F, 0.0F, 1.1527899709422544F);
         this.tail_base = new AdvancedModelRenderer(this, 17, 33);
         this.tail_base.setRotationPoint(0.0F, -0.7F, 1.7F);
         this.tail_base.addBox(-7.0F, -3.0F, 1.0F, 14, 8, 12, 0.0F);
         this.left_back_leg_1 = new AdvancedModelRenderer(this, 0, 26);
         this.left_back_leg_1.setRotationPoint(6.0F, -0.4F, 0.0F);
         this.left_back_leg_1.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
         this.setRotateAngle(left_back_leg_1, 0.0F, 0.0F, -0.25761059759436306F);
         this.tail_1 = new AdvancedModelRenderer(this, 17, 34);
         this.tail_1.setRotationPoint(0.0F, -1.4F, 12.0F);
         this.tail_1.addBox(-6.0F, -3.0F, 1.0F, 12, 8, 12, 0.0F);
         this.right_front_leg_2 = new AdvancedModelRenderer(this, 0, 27);
         this.right_front_leg_2.setRotationPoint(6.6F, -0.1F, 0.0F);
         this.right_front_leg_2.addBox(0.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         this.setRotateAngle(right_front_leg_2, 0.0F, 0.0F, 0.6263986685407649F);
         this.left_claw_2_1 = new AdvancedModelRenderer(this, 0, 25);
         this.left_claw_2_1.setRotationPoint(6.6F, -0.1F, 0.0F);
         this.left_claw_2_1.addBox(0.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         this.setRotateAngle(left_claw_2_1, 0.0F, 0.0F, 1.3390166021300496F);
         this.right_middle_leg_1 = new AdvancedModelRenderer(this, 0, 30);
         this.right_middle_leg_1.setRotationPoint(-7.0F, -0.4F, 11.9F);
         this.right_middle_leg_1.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
         this.setRotateAngle(right_middle_leg_1, 0.0F, 3.141592653589793F, 0.25761059759436306F);
         this.left_middle_leg_1 = new AdvancedModelRenderer(this, 0, 27);
         this.left_middle_leg_1.setRotationPoint(8.3F, 0.1F, 12.9F);
         this.left_middle_leg_1.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
         this.setRotateAngle(left_middle_leg_1, 0.0F, 0.0F, -0.25761059759436306F);
         this.right_middle_leg_2 = new AdvancedModelRenderer(this, 0, 30);
         this.right_middle_leg_2.setRotationPoint(6.6F, -0.1F, 0.0F);
         this.right_middle_leg_2.addBox(0.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         this.setRotateAngle(right_middle_leg_2, 0.0F, 0.0F, 0.6263986685407649F);
         this.left_claw_6 = new AdvancedModelRenderer(this, 0, 0);
         this.left_claw_6.setRotationPoint(7.6F, 1.4F, 3.3F);
         this.left_claw_6.addBox(0.0F, 0.0F, 0.0F, 7, 3, 1, 0.0F);
         this.setRotateAngle(left_claw_6, -0.018500490071139894F, -0.20455258833373544F, 0.0F);
         this.left_back_leg_2 = new AdvancedModelRenderer(this, 0, 23);
         this.left_back_leg_2.setRotationPoint(6.6F, -0.1F, 0.0F);
         this.left_back_leg_2.addBox(0.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         this.setRotateAngle(left_back_leg_2, 0.0F, 0.0F, 0.6263986685407649F);
         this.right_front_leg_1 = new AdvancedModelRenderer(this, 0, 30);
         this.right_front_leg_1.setRotationPoint(-7.0F, -0.4F, 1.3F);
         this.right_front_leg_1.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
         this.setRotateAngle(right_front_leg_1, 0.0F, 3.141592653589793F, 0.25761059759436306F);
         this.body_2.addChild(this.right_back_leg_1);
         this.right_middle_leg_2.addChild(this.right_middle_leg_3);
         this.left_claw_2.addChild(this.left_claw_3);
         this.left_front_leg_1.addChild(this.left_front_leg_2);
         this.left_claw_3.addChild(this.left_claw_4);
         this.left_claw_4_1.addChild(this.left_claw_5_1);
         this.left_middle_leg_1.addChild(this.left_middle_leg_2);
         this.body_1.addChild(this.left_claw_1_1);
         this.body_1.addChild(this.head);
         this.tail_4.addChild(this.tail_5);
         this.left_claw_3_1.addChild(this.left_claw_4_1);
         this.tail_6.addChild(this.tail7);
         this.tail_8.addChild(this.stinger_base);
         this.left_middle_leg_2.addChild(this.left_middle_leg_3);
         this.left_claw_4.addChild(this.left_claw_5);
         this.right_back_leg_2.addChild(this.right_back_leg_3);
         this.tail_3.addChild(this.tail_4);
         this.tail7.addChild(this.tail_8);
         this.right_front_leg_2.addChild(this.right_front_leg_3);
         this.right_back_leg_1.addChild(this.right_back_leg_2);
         this.left_claw_2_1.addChild(this.left_claw_3_1);
         this.left_claw_4_1.addChild(this.left_claw_6_1);
         this.body_1.addChild(this.left_front_leg_1);
         this.tail_5.addChild(this.tail_6);
         this.left_claw_1.addChild(this.left_claw_2);
         this.body_1.addChild(this.left_claw_1);
         this.body_1.addChild(this.body_2);
         this.tail_1.addChild(this.tail_2);
         this.stinger_base.addChild(this.stinger_1);
         this.left_back_leg_2.addChild(this.left_back_leg_3);
         this.stinger_1.addChild(this.stinger_2);
         this.stinger_2.addChild(this.stinger_3);
         this.tail_2.addChild(this.tail_3);
         this.left_front_leg_2.addChild(this.left_front_leg_3);
         this.body_2.addChild(this.tail_base);
         this.body_2.addChild(this.left_back_leg_1);
         this.tail_base.addChild(this.tail_1);
         this.right_front_leg_1.addChild(this.right_front_leg_2);
         this.left_claw_1_1.addChild(this.left_claw_2_1);
         this.body_1.addChild(this.right_middle_leg_1);
         this.body_1.addChild(this.left_middle_leg_1);
         this.right_middle_leg_1.addChild(this.right_middle_leg_2);
         this.left_claw_4.addChild(this.left_claw_6);
         this.left_back_leg_1.addChild(this.left_back_leg_2);
         this.body_1.addChild(this.right_front_leg_1);
        updateDefaultPose();
        setAnimations();
    }

    @Override
    public void render(EntityGiantScorpian entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body_1.render(f5);
    }
    
    Animation currentAnim = null;
    List<Animation> animations = new ArrayList<Animation>();
    
    void setAnimations() {
    	Animation sting = new Animation(Animations.SCORPIAN_STING, 20, this, false);
    	
    	KeyFrame start = new KeyFrame(0, new CustomRunnable() {
			public void run(Object mob) {
				Network.INSTANCE.sendToServer(new AnimationEventPKT("giantScorpianStingStart", (CustomMob) mob));
			}});
    	KeyFrame f1 = new KeyFrame(10, null);
    	KeyFrame f2 = new KeyFrame(13, new CustomRunnable() {
			public void run(Object mob) {
				Network.INSTANCE.sendToServer(new AnimationEventPKT("giantScorpianSting", (CustomMob)mob));
			}}
    	);
    	
    	KeyFrame end = new KeyFrame(20, new CustomRunnable() {
			public void run(Object mob) {
				Network.INSTANCE.sendToServer(new AnimationEventPKT("giantScorpianStingFinish", (CustomMob)mob));
			}
		});
    	
    	start.addPartOrientation(new PartOrientation(body_1, "body_1"));
    	start.addPartOrientation(new PartOrientation(tail_base, "tail_base"));
    	start.addPartOrientation(new PartOrientation(tail_1, "tail_1"));
    	start.addPartOrientation(new PartOrientation(tail_2, "tail_2"));
    	start.addPartOrientation(new PartOrientation(tail_3, "tail_3"));
    	start.addPartOrientation(new PartOrientation(tail_4, "tail_4"));
    	start.addPartOrientation(new PartOrientation(tail_5, "tail_5"));
    	start.addPartOrientation(new PartOrientation(tail_6, "tail_6"));
    	start.addPartOrientation(new PartOrientation(tail7, "tail7"));
    	start.addPartOrientation(new PartOrientation(stinger_base, "stinger_base"));
    	start.addPartOrientation(new PartOrientation(right_front_leg_1, "right_front_leg_1"));
    	start.addPartOrientation(new PartOrientation(right_middle_leg_1, "right_middle_leg_1"));
    	start.addPartOrientation(new PartOrientation(right_back_leg_1, "right_back_leg_1"));
    	start.addPartOrientation(new PartOrientation(left_front_leg_1, "left_front_leg_1"));
    	start.addPartOrientation(new PartOrientation(left_middle_leg_1, "left_middle_leg_1"));
    	start.addPartOrientation(new PartOrientation(left_back_leg_1, "left_back_leg_1"));
    	
    	//windup
    	f1.addPartOrientation(new PartOrientation("body_1", body_1, 0, 0, -1.5f, 0, 0, 0));

    	f1.addPartOrientation(new PartOrientation("tail_1",tail_1,                          0, 0, 0, -25, 0, 0));
    	f1.addPartOrientation(new PartOrientation("tail_2", tail_2,                         0, 0, 0, -15, 0, 0));
    	f1.addPartOrientation(new PartOrientation("tail_base", tail_base,                   0, 0, 0, -10, 0, 0));
    	f1.addPartOrientation(new PartOrientation("right_front_leg_1", right_front_leg_1,   0, 0, 0, -10, 0, 0));
    	f1.addPartOrientation(new PartOrientation("right_middle_leg_1", right_middle_leg_1, 0, 0, 0, -10, 0, 0));
    	f1.addPartOrientation(new PartOrientation("right_back_leg_1", right_back_leg_1,     0, 0, 0, -10, 0, 0));
    	f1.addPartOrientation(new PartOrientation("left_front_leg_1", left_front_leg_1,     0, 0, 0, -10, 0, 0));
    	f1.addPartOrientation(new PartOrientation("left_middle_leg_1", left_middle_leg_1,   0, 0, 0, -10, 0, 0));
    	f1.addPartOrientation(new PartOrientation("left_back_leg_1", left_back_leg_1,       0, 0, 0, -10, 0, 0));
    	
    	//strike
    	f2.addPartOrientation(new PartOrientation("body_1", body_1,                         0, 0, 1.5f, 0, 0, 0));
    	f2.addPartOrientation(new PartOrientation("tail_base", tail_base,       			0, 0, 0, 30, 0, 0));
    	f2.addPartOrientation(new PartOrientation("tail_1", tail_1,    			   			0, 0, 0, 70, 0, 0));
    	f2.addPartOrientation(new PartOrientation("tail_2", tail_2,       					0, 0, 0, 15, 0, 0));
    	f2.addPartOrientation(new PartOrientation("tail_3", tail_3,       					0, 0, 0, -30, 0, 0));
    	f2.addPartOrientation(new PartOrientation("tail_4", tail_4,       					0, 0, 0, -5, 0, 0));
    	f2.addPartOrientation(new PartOrientation("tail_5", tail_5,       					0, 0, 0, -5, 0, 0));
    	f2.addPartOrientation(new PartOrientation("tail_6", tail_6,       					0, 0, 0, -10, 0, 0));
    	f2.addPartOrientation(new PartOrientation("tail7", tail7,     						0, 0, 0, -20, 0, 0));
    	f2.addPartOrientation(new PartOrientation("stinger_base", stinger_base,       		0, 0, 0, -90, 0, 0));
    	f2.addPartOrientation(new PartOrientation("right_front_leg_1", right_front_leg_1,   0, 0, 0, +10, 0, 0));
    	f2.addPartOrientation(new PartOrientation("right_middle_leg_1", right_middle_leg_1, 0, 0, 0, +10, 0, 0));
    	f2.addPartOrientation(new PartOrientation("right_back_leg_1", right_back_leg_1,     0, 0, 0, +10, 0, 0));
    	f2.addPartOrientation(new PartOrientation("left_front_leg_1", left_front_leg_1,     0, 0, 0, -10, 0, 0));
    	f2.addPartOrientation(new PartOrientation("left_middle_leg_1", left_middle_leg_1,   0, 0, 0, -10, 0, 0));
    	f2.addPartOrientation(new PartOrientation("left_back_leg_1", left_back_leg_1,       0, 0, 0, -10, 0, 0));
    	
    	end.addPartOrientation(new PartOrientation(body_1, "body_1"));
    	end.addPartOrientation(new PartOrientation(tail_base, "tail_base"));
    	end.addPartOrientation(new PartOrientation(tail_1, "tail_1"));
    	end.addPartOrientation(new PartOrientation(tail_2, "tail_2"));
    	end.addPartOrientation(new PartOrientation(tail_3, "tail_3"));
    	end.addPartOrientation(new PartOrientation(tail_4, "tail_4"));
    	end.addPartOrientation(new PartOrientation(tail_5, "tail_5"));
    	end.addPartOrientation(new PartOrientation(tail_6, "tail_6"));
    	end.addPartOrientation(new PartOrientation(tail7, "tail7"));
    	end.addPartOrientation(new PartOrientation(stinger_base, "stinger_base"));
    	end.addPartOrientation(new PartOrientation(right_front_leg_1, "right_front_leg_1"));
    	end.addPartOrientation(new PartOrientation(right_middle_leg_1, "right_middle_leg_1"));
    	end.addPartOrientation(new PartOrientation(right_back_leg_1, "right_back_leg_1"));
    	end.addPartOrientation(new PartOrientation(left_front_leg_1, "left_front_leg_1"));
    	end.addPartOrientation(new PartOrientation(left_middle_leg_1, "left_middle_leg_1"));
    	end.addPartOrientation(new PartOrientation(left_back_leg_1, "left_back_leg_1"));
    	
    	sting.addFrame(start);
    	sting.addFrame(f1);
    	sting.addFrame(f2);
    	sting.addFrame(end);
    	
    	sting.lockInParts();
    	Animations.add(sting);
    }
    
    @Override
    public void setRotationAngles(EntityGiantScorpian entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    		float headPitch, float scaleFactor) {
    	resetToDefaultPose();
    	this.currentAnim = ((CustomMob)entityIn).currentAnim;
    	CustomMob customMob = (CustomMob)entityIn;
    	
    	boolean resetAnim = false;
    	if(resetAnim) {
    		setAnimations();
    	}
    	
    	if(currentAnim != null) {
    		AnimationState state = currentAnim.render(customMob, customMob.animationStartTime, ageInTicks);
    		if(state == AnimationState.FINISHED) {
    			customMob.currentAnim = null;
    		}else if(state == AnimationState.NOT_RUNNING) {
    			customMob.animationStartTime = ageInTicks;
    		}
    		return;
    	}
    	
    	float f = limbSwing;// = entityIn.ticksExisted;
        float f1 = limbSwingAmount;// = 1;
    	float speed = 1.6f;
        float height = .1f;
        float degree =  1.5f;
        float legHeight = 1f;
    	
    	
    	
    	
        //body movement
        bob(body_1, speed / 2, height, false, limbSwing, limbSwingAmount);
        
        
        //leg movement
        swing(left_back_leg_1, speed / 2, degree / 2, false, 0, 0, f, f1);
        flap(left_back_leg_1, speed / 2, legHeight / 4, false, -2f, 0, f, f1);
        
        swing(left_middle_leg_1, speed / 2, degree / 2, false, -2f, 0, f, f1);
        flap(left_middle_leg_1, speed / 2, legHeight / 4, false, -4f, 0, f, f1);
        
        swing(left_front_leg_1, speed / 2, degree / 2, false, -1, 0, f, f1);
        flap(left_front_leg_1, speed / 2, legHeight / 4, false, -3f, 0, f, f1);
     
        
        swing(right_back_leg_1, speed / 2, degree / 2, false, 0, 0, f, f1);
        flap(right_back_leg_1, speed / 2, legHeight / 4, false, -2f, 0, f, f1);
        
        swing(right_middle_leg_1, speed / 2, degree / 2, false, -2f, 0, f, f1);
        flap(right_middle_leg_1, speed / 2, legHeight / 4, false, -4f, 0, f, f1);
        
        swing(right_front_leg_1, speed / 2, degree / 2, false, -1, 0, f, f1);
        flap(right_front_leg_1, speed / 2, legHeight / 4, false, -3f, 0, f, f1);
        
        
        
        //claw movement
        flap(left_claw_1, speed / 4, degree / 4, false, 0, 0, f, f1);
        flap(left_claw_1_1, speed / 4, degree / 4, false, 1, 0, f, f1);
        
        //pinch
        	swing(left_claw_5, speed / 2, degree / 4, false, 0, 0, f, f1);
        	swing(left_claw_6, speed / 2, degree / 4, false, 2, 0, f, f1);
        
        	swing(left_claw_5_1, speed / 2, degree / 4, false, 2, 0, f, f1);
        	swing(left_claw_6_1, speed / 2, degree / 4, false, 4, 0, f, f1);
        
        //tail movement
        swing(tail_1, speed / 6, degree / 6, false, -1, 0, f, f1);
        bob(tail_1, speed / 2, height * 4, false, f, f1);
        bob(tail_2, speed / 2, height * 4 , false, f, f1);
        bob(tail_3, speed / 2, height * 4, false, f, f1);
        bob(tail_4, speed / 2, height * 4, false, f, f1);
        bob(tail_5, speed / 2, height * 4, false, f, f1);
        
    }
    
    

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(AdvancedModelRenderer RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
