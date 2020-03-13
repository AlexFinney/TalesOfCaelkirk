package skeeter144.toc.client.entity.model;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import skeeter144.toc.client.entity.animation.Animation;
import skeeter144.toc.client.entity.renderer.AdvancedModelRenderer;
import skeeter144.toc.entity.mob.monster.EntityGiantSpider;

/**
 * GiantSpider - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelGiantSpider extends EntityModel<EntityGiantSpider>
{
    public AdvancedModelRenderer body;
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer butt;
    public AdvancedModelRenderer left_front_leg_1;
    public AdvancedModelRenderer left_middle_1_leg_1;
    public AdvancedModelRenderer left_middle_2_leg_1;
    public AdvancedModelRenderer left_back_leg_1;
    public AdvancedModelRenderer right_front_leg_1;
    public AdvancedModelRenderer right_middle_1_leg_1;
    public AdvancedModelRenderer right_middle_2_leg_1;
    public AdvancedModelRenderer right_middle_2_leg_1_1;
    public AdvancedModelRenderer left_fang_1;
    public AdvancedModelRenderer right_fang_1;
    public AdvancedModelRenderer eye_1;
    public AdvancedModelRenderer eye_2;
    public AdvancedModelRenderer eye_3;
    public AdvancedModelRenderer eye_4;
    public AdvancedModelRenderer eye_5;
    public AdvancedModelRenderer eye_6;
    public AdvancedModelRenderer left_fang_2;
    public AdvancedModelRenderer left_fang_3;
    public AdvancedModelRenderer right_fang_2;
    public AdvancedModelRenderer right_fang_3;
    public AdvancedModelRenderer butt_1;
    public AdvancedModelRenderer left_front_leg_2;
    public AdvancedModelRenderer left_front_leg_3;
    public AdvancedModelRenderer left_front_leg_4;
    public AdvancedModelRenderer left_middle_1_leg_2;
    public AdvancedModelRenderer left_middle_1_leg_3;
    public AdvancedModelRenderer left_middle_1_leg_4;
    public AdvancedModelRenderer left_middle_2_leg_2;
    public AdvancedModelRenderer left_middle_2_leg_3;
    public AdvancedModelRenderer left_middle_2_leg_4;
    public AdvancedModelRenderer left_back_leg_2;
    public AdvancedModelRenderer left_back_leg_3;
    public AdvancedModelRenderer left_back_leg_4;
    public AdvancedModelRenderer right_front_leg_2;
    public AdvancedModelRenderer right_front_leg_3;
    public AdvancedModelRenderer right_front_leg_4;
    public AdvancedModelRenderer right_middle_1_leg_2;
    public AdvancedModelRenderer right_middle_1_leg_3;
    public AdvancedModelRenderer right_middle_1_leg_4;
    public AdvancedModelRenderer right_middle_2_leg_2;
    public AdvancedModelRenderer right_middle_2_leg_3;
    public AdvancedModelRenderer right_middle_2_leg_4;
    public AdvancedModelRenderer right_middle_2_leg_2_1;
    public AdvancedModelRenderer right_middle_2_leg_3_1;
    public AdvancedModelRenderer right_middle_2_leg_4_1;

    public ModelGiantSpider() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.left_middle_2_leg_4 = new AdvancedModelRenderer(this, 0, 0);
        this.left_middle_2_leg_4.setRotationPoint(19.6F, -0.3F, 0.0F);
        this.left_middle_2_leg_4.addBox(0.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(left_middle_2_leg_4, 0.0F, 0.0F, 0.40980330836826856F);
        this.left_middle_2_leg_1 = new AdvancedModelRenderer(this, 0, 0);
        this.left_middle_2_leg_1.setRotationPoint(10.0F, -2.0F, 13.0F);
        this.left_middle_2_leg_1.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(left_middle_2_leg_1, 0.0825540736193318F, -0.1467821900927231F, -0.31869712141416456F);
        this.right_middle_2_leg_1 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_2_leg_1.setRotationPoint(-12.0F, -3.0F, 14.0F);
        this.right_middle_2_leg_1.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_2_leg_1, 0.0F, -2.9710739856699466F, 0.22427480888127135F);
        this.left_front_leg_3 = new AdvancedModelRenderer(this, 0, 0);
        this.left_front_leg_3.setRotationPoint(15.6F, -0.3F, 0.0F);
        this.left_front_leg_3.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2, 0.0F);
        this.setRotateAngle(left_front_leg_3, 0.0F, 0.0F, 0.8168140899333463F);
        this.butt = new AdvancedModelRenderer(this, 0, 74);
        this.butt.setRotationPoint(0.0F, -2.0F, 36.0F);
        this.butt.addBox(-16.0F, -12.0F, -16.0F, 32, 26, 28, 0.0F);
        this.right_middle_2_leg_1_1 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_2_leg_1_1.setRotationPoint(-15.0F, -8.0F, 24.0F);
        this.right_middle_2_leg_1_1.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_2_leg_1_1, -0.14381513036433274F, -2.9497515042385164F, 0.34417623788182417F);
        this.eye_4 = new AdvancedModelRenderer(this, 0, 8);
        this.eye_4.setRotationPoint(3.0F, -5.0F, -6.5F);
        this.eye_4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.body = new AdvancedModelRenderer(this, 0, 23);
        this.body.setRotationPoint(0.0F, -2.0F, 3.0F);
        this.body.addBox(-12.0F, -10.0F, -12.0F, 24, 20, 32, 0.0F);
        this.right_fang_2 = new AdvancedModelRenderer(this, 10, 10);
        this.right_fang_2.setRotationPoint(0.0F, 0.0F, -3.8F);
        this.right_fang_2.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(right_fang_2, 0.0F, 0.4260348704118158F, 0.0F);
        this.left_back_leg_4 = new AdvancedModelRenderer(this, 0, 0);
        this.left_back_leg_4.setRotationPoint(19.6F, -0.3F, 0.0F);
        this.left_back_leg_4.addBox(0.0F, -1.0F, -1.0F, 22, 2, 2, 0.0F);
        this.setRotateAngle(left_back_leg_4, 0.0F, 0.0F, 0.6080727113948244F);
        this.right_middle_2_leg_2_1 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_2_leg_2_1.setRotationPoint(12.5F, 0.0F, 0.0F);
        this.right_middle_2_leg_2_1.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_2_leg_2_1, 0.0F, 0.0F, 0.5864306286700948F);
        this.left_fang_2 = new AdvancedModelRenderer(this, 10, 10);
        this.left_fang_2.setRotationPoint(0.0F, 0.0F, -3.8F);
        this.left_fang_2.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(left_fang_2, 0.0F, 0.4260348704118158F, 0.0F);
        this.left_front_leg_2 = new AdvancedModelRenderer(this, 0, 0);
        this.left_front_leg_2.setRotationPoint(12.5F, 0.0F, 0.0F);
        this.left_front_leg_2.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(left_front_leg_2, 0.0F, 0.0F, 0.5864306286700948F);
        this.right_middle_1_leg_2 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_1_leg_2.setRotationPoint(12.5F, 0.0F, 0.0F);
        this.right_middle_1_leg_2.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_1_leg_2, 0.0F, 0.0F, 0.5864306286700948F);
        this.left_back_leg_2 = new AdvancedModelRenderer(this, 0, 0);
        this.left_back_leg_2.setRotationPoint(12.5F, 0.0F, 0.0F);
        this.left_back_leg_2.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(left_back_leg_2, 0.0F, 0.0F, 0.5864306286700948F);
        this.left_middle_1_leg_4 = new AdvancedModelRenderer(this, 0, 0);
        this.left_middle_1_leg_4.setRotationPoint(19.6F, -0.3F, 0.0F);
        this.left_middle_1_leg_4.addBox(0.0F, -1.0F, -1.0F, 15, 2, 2, 0.0F);
        this.setRotateAngle(left_middle_1_leg_4, 0.0F, 0.0F, 0.40980330836826856F);
        this.right_front_leg_2 = new AdvancedModelRenderer(this, 0, 0);
        this.right_front_leg_2.setRotationPoint(12.5F, 0.0F, 0.0F);
        this.right_front_leg_2.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(right_front_leg_2, 0.0F, 0.0F, 0.5864306286700948F);
        this.eye_3 = new AdvancedModelRenderer(this, 0, 8);
        this.eye_3.setRotationPoint(-4.0F, -5.0F, -6.4F);
        this.eye_3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.head = new AdvancedModelRenderer(this, 80, 31);
        this.head.setRotationPoint(0.0F, -2.0F, -18.0F);
        this.head.addBox(-6.0F, -6.0F, -6.0F, 12, 12, 12, 0.0F);
        this.left_fang_1 = new AdvancedModelRenderer(this, 10, 10);
        this.left_fang_1.setRotationPoint(3.0F, 3.0F, -6.0F);
        this.left_fang_1.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(left_fang_1, 0.2839650692994774F, -0.5520476424058064F, 0.0F);
        this.right_front_leg_4 = new AdvancedModelRenderer(this, 0, 0);
        this.right_front_leg_4.setRotationPoint(19.6F, -0.3F, 0.0F);
        this.right_front_leg_4.addBox(0.0F, -1.0F, -1.0F, 10, 2, 2, 0.0F);
        this.setRotateAngle(right_front_leg_4, 0.0F, 0.0F, 0.40980330836826856F);
        this.right_front_leg_1 = new AdvancedModelRenderer(this, 0, 0);
        this.right_front_leg_1.setRotationPoint(-12.0F, -4.0F, -9.0F);
        this.right_front_leg_1.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(right_front_leg_1, 0.0F, -3.4987132866385253F, 0.22421200053537615F);
        this.right_front_leg_3 = new AdvancedModelRenderer(this, 0, 0);
        this.right_front_leg_3.setRotationPoint(15.6F, -0.3F, 0.0F);
        this.right_front_leg_3.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2, 0.0F);
        this.setRotateAngle(right_front_leg_3, 0.0F, 0.0F, 0.8168140899333463F);
        this.eye_2 = new AdvancedModelRenderer(this, 0, 8);
        this.eye_2.setRotationPoint(-3.0F, -3.0F, -6.5F);
        this.eye_2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.eye_5 = new AdvancedModelRenderer(this, 0, 8);
        this.eye_5.setRotationPoint(-5.0F, -3.6F, -6.4F);
        this.eye_5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.right_fang_3 = new AdvancedModelRenderer(this, 10, 10);
        this.right_fang_3.setRotationPoint(0.2F, 0.0F, -2.6F);
        this.right_fang_3.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(right_fang_3, 0.0F, 0.4260348704118158F, 0.12897983172238095F);
        this.right_middle_1_leg_3 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_1_leg_3.setRotationPoint(15.6F, -0.3F, 0.0F);
        this.right_middle_1_leg_3.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_1_leg_3, 0.0F, 0.0F, 0.8168140899333463F);
        this.right_middle_1_leg_1 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_1_leg_1.setRotationPoint(-12.0F, -7.0F, 2.0F);
        this.right_middle_1_leg_1.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_1_leg_1, 0.0F, -3.2108822248939677F, 0.22427480888127135F);
        this.left_back_leg_3 = new AdvancedModelRenderer(this, 0, 0);
        this.left_back_leg_3.setRotationPoint(15.6F, -0.3F, 0.0F);
        this.left_back_leg_3.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2, 0.0F);
        this.setRotateAngle(left_back_leg_3, 0.0F, 0.0F, 0.8168140899333463F);
        this.right_middle_2_leg_3_1 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_2_leg_3_1.setRotationPoint(15.6F, -0.3F, 0.0F);
        this.right_middle_2_leg_3_1.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_2_leg_3_1, 0.0F, 0.0F, 0.8168140899333463F);
        this.left_middle_1_leg_3 = new AdvancedModelRenderer(this, 0, 0);
        this.left_middle_1_leg_3.setRotationPoint(15.6F, -0.3F, 0.0F);
        this.left_middle_1_leg_3.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2, 0.0F);
        this.setRotateAngle(left_middle_1_leg_3, 0.0F, 0.0F, 0.8168140899333463F);
        this.left_front_leg_4 = new AdvancedModelRenderer(this, 0, 0);
        this.left_front_leg_4.setRotationPoint(19.6F, -0.3F, 0.0F);
        this.left_front_leg_4.addBox(0.0F, -1.0F, -1.0F, 12, 2, 2, 0.0F);
        this.setRotateAngle(left_front_leg_4, 0.0F, 0.0F, 0.40980330836826856F);
        this.right_middle_2_leg_3 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_2_leg_3.setRotationPoint(15.6F, -0.3F, 0.0F);
        this.right_middle_2_leg_3.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_2_leg_3, 0.0F, 0.0F, 0.8168140899333463F);
        this.left_middle_2_leg_2 = new AdvancedModelRenderer(this, 0, 0);
        this.left_middle_2_leg_2.setRotationPoint(12.5F, 0.0F, 0.0F);
        this.left_middle_2_leg_2.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(left_middle_2_leg_2, 0.0F, 0.0F, 0.5864306286700948F);
        this.left_front_leg_1 = new AdvancedModelRenderer(this, 0, 0);
        this.left_front_leg_1.setRotationPoint(10.0F, -4.0F, -9.0F);
        this.left_front_leg_1.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(left_front_leg_1, 0.0F, 0.4539601384437252F, -0.2876302607286655F);
        this.left_back_leg_1 = new AdvancedModelRenderer(this, 0, 0);
        this.left_back_leg_1.setRotationPoint(10.0F, -7.0F, 20.0F);
        this.left_back_leg_1.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(left_back_leg_1, 0.24277529895241123F, -0.25f, -0.5536184387326013F);
        this.eye_1 = new AdvancedModelRenderer(this, 0, 8);
        this.eye_1.setRotationPoint(1.0F, -3.0F, -6.5F);
        this.eye_1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.right_middle_2_leg_4 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_2_leg_4.setRotationPoint(19.6F, -0.3F, 0.0F);
        this.right_middle_2_leg_4.addBox(0.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_2_leg_4, 0.0F, 0.0F, 0.40980330836826856F);
        this.right_middle_2_leg_2 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_2_leg_2.setRotationPoint(12.5F, 0.0F, 0.0F);
        this.right_middle_2_leg_2.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_2_leg_2, 0.0F, 0.0F, 0.5864306286700948F);
        this.eye_6 = new AdvancedModelRenderer(this, 0, 8);
        this.eye_6.setRotationPoint(4.0F, -3.5F, -6.4F);
        this.eye_6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.left_middle_2_leg_3 = new AdvancedModelRenderer(this, 0, 0);
        this.left_middle_2_leg_3.setRotationPoint(15.6F, -0.3F, 0.0F);
        this.left_middle_2_leg_3.addBox(0.0F, -1.0F, -1.0F, 20, 2, 2, 0.0F);
        this.setRotateAngle(left_middle_2_leg_3, 0.0F, 0.0F, 0.8168140899333463F);
        this.right_middle_2_leg_4_1 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_2_leg_4_1.setRotationPoint(19.6F, -0.3F, 0.0F);
        this.right_middle_2_leg_4_1.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_2_leg_4_1, 0.0F, 0.0F, 0.40980330836826856F);
        this.left_middle_1_leg_1 = new AdvancedModelRenderer(this, 0, 0);
        this.left_middle_1_leg_1.setRotationPoint(10.0F, -6.0F, 2.0F);
        this.left_middle_1_leg_1.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(left_middle_1_leg_1, -0.045553093477052F, 0.045553093477052F, -0.31869712141416456F);
        this.left_middle_1_leg_2 = new AdvancedModelRenderer(this, 0, 0);
        this.left_middle_1_leg_2.setRotationPoint(12.5F, 0.0F, 0.0F);
        this.left_middle_1_leg_2.addBox(0.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
        this.setRotateAngle(left_middle_1_leg_2, 0.0F, 0.0F, 0.5864306286700948F);
        this.right_fang_1 = new AdvancedModelRenderer(this, 10, 10);
        this.right_fang_1.setRotationPoint(-2.0F, 3.0F, -6.0F);
        this.right_fang_1.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(right_fang_1, -0.28850292535466265F, -0.6218608124855797F, -3.1279790854242373F);
        this.left_fang_3 = new AdvancedModelRenderer(this, 10, 10);
        this.left_fang_3.setRotationPoint(0.2F, 0.0F, -2.6F);
        this.left_fang_3.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(left_fang_3, 0.0F, 0.4260348704118158F, 0.12897983172238095F);
        this.butt_1 = new AdvancedModelRenderer(this, 32, 101);
        this.butt_1.setRotationPoint(3.0F, -3.0F, 13.0F);
        this.butt_1.addBox(-6.0F, -6.0F, -6.0F, 18, 18, 8, 0.0F);
        this.setRotateAngle(butt_1, 0.0F, -3.141592653589793F, 0.0F);
        this.right_middle_1_leg_4 = new AdvancedModelRenderer(this, 0, 0);
        this.right_middle_1_leg_4.setRotationPoint(19.6F, -0.3F, 0.0F);
        this.right_middle_1_leg_4.addBox(0.0F, -1.0F, -1.0F, 13, 2, 2, 0.0F);
        this.setRotateAngle(right_middle_1_leg_4, 0.0F, 0.0F, 0.40980330836826856F);
        this.left_middle_2_leg_3.addChild(this.left_middle_2_leg_4);
        this.body.addChild(this.left_middle_2_leg_1);
        this.body.addChild(this.right_middle_2_leg_1);
        this.left_front_leg_2.addChild(this.left_front_leg_3);
        this.body.addChild(this.butt);
        this.body.addChild(this.right_middle_2_leg_1_1);
        this.head.addChild(this.eye_4);
        this.right_fang_1.addChild(this.right_fang_2);
        this.left_back_leg_3.addChild(this.left_back_leg_4);
        this.right_middle_2_leg_1_1.addChild(this.right_middle_2_leg_2_1);
        this.left_fang_1.addChild(this.left_fang_2);
        this.left_front_leg_1.addChild(this.left_front_leg_2);
        this.right_middle_1_leg_1.addChild(this.right_middle_1_leg_2);
        this.left_back_leg_1.addChild(this.left_back_leg_2);
        this.left_middle_1_leg_3.addChild(this.left_middle_1_leg_4);
        this.right_front_leg_1.addChild(this.right_front_leg_2);
        this.head.addChild(this.eye_3);
        this.body.addChild(this.head);
        this.head.addChild(this.left_fang_1);
        this.right_front_leg_3.addChild(this.right_front_leg_4);
        this.body.addChild(this.right_front_leg_1);
        this.right_front_leg_2.addChild(this.right_front_leg_3);
        this.head.addChild(this.eye_2);
        this.head.addChild(this.eye_5);
        this.right_fang_2.addChild(this.right_fang_3);
        this.right_middle_1_leg_2.addChild(this.right_middle_1_leg_3);
        this.body.addChild(this.right_middle_1_leg_1);
        this.left_back_leg_2.addChild(this.left_back_leg_3);
        this.right_middle_2_leg_2_1.addChild(this.right_middle_2_leg_3_1);
        this.left_middle_1_leg_2.addChild(this.left_middle_1_leg_3);
        this.left_front_leg_3.addChild(this.left_front_leg_4);
        this.right_middle_2_leg_2.addChild(this.right_middle_2_leg_3);
        this.left_middle_2_leg_1.addChild(this.left_middle_2_leg_2);
        this.body.addChild(this.left_front_leg_1);
        this.body.addChild(this.left_back_leg_1);
        this.head.addChild(this.eye_1);
        this.right_middle_2_leg_3.addChild(this.right_middle_2_leg_4);
        this.right_middle_2_leg_1.addChild(this.right_middle_2_leg_2);
        this.head.addChild(this.eye_6);
        this.left_middle_2_leg_2.addChild(this.left_middle_2_leg_3);
        this.right_middle_2_leg_3_1.addChild(this.right_middle_2_leg_4_1);
        this.body.addChild(this.left_middle_1_leg_1);
        this.left_middle_1_leg_1.addChild(this.left_middle_1_leg_2);
        this.head.addChild(this.right_fang_1);
        this.left_fang_2.addChild(this.left_fang_3);
        this.butt.addChild(this.butt_1);
        this.right_middle_1_leg_3.addChild(this.right_middle_1_leg_4);
       // updateDefaultPose();
        setAnimations();
    }

    @Override
    public void setRotationAngles(EntityGiantSpider entityGiantSpider, float v, float v1, float v2, float v3, float v4)
    {

    }

    Animation currentAnim = null;
    List<Animation> animations = new ArrayList<Animation>();
    void setAnimations() {
    
    }
    
/*
    @Override
    public void setRotationAngles(EntityGiantSpider entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
    		float headPitch, float scaleFactor) {
    	resetToDefaultPose();
    	
    	
    	float f = limbSwing;// = entityIn.ticksExisted;
        float f1 = limbSwingAmount;// = 1;
    	float speed = 1.47f;
        float height = .2f;
        float degree =  2f;
        float legHeight = .75f;
    
    
        //body movement
        bob(body, speed / 2, height * 2, false, limbSwing, limbSwingAmount);
        bob(butt, speed / 2, height * 3, false, limbSwing, limbSwingAmount);
        
        swing(left_front_leg_1, speed / 4, degree / 2, false, 0, 0, f, f1);
        flap(left_front_leg_1, speed / 4, legHeight / 4, false, -2f, 0, f, f1);
        
        swing(left_middle_1_leg_1, speed / 4, degree / 2, false, -1.3f, 0, f, f1);
        flap(left_middle_1_leg_1, speed / 4, legHeight / 4, false, -3.3f, 0, f, f1);
        
        swing(left_middle_2_leg_1, speed / 4, degree / 2, false, -2f, 0, f, f1);
        flap(left_middle_2_leg_1, speed / 4, legHeight / 4, false, -4f, 0, f, f1);
        
        swing(left_back_leg_1, speed / 4f, degree / 2f, false, -2.7f, 0, f, f1);
        flap(left_back_leg_1, speed / 4f, legHeight / 4, false, -4.7f, 0, f, f1);
        
        
        swing(right_front_leg_1, speed / 4, degree / 2, false, 0, 0, f, f1);
        flap(right_front_leg_1, speed / 4, legHeight / 4, false, -2f, 0, f, f1);
        
        swing(right_middle_1_leg_1, speed / 4, degree / 2, false, -1.3f, 0, f, f1);
        flap(right_middle_1_leg_1, speed / 4, legHeight / 4, false, -3.3f, 0, f, f1);
        
        swing(right_middle_2_leg_1, speed / 4, degree / 2, false, -2f, 0, f, f1);
        flap(right_middle_2_leg_1, speed / 4, legHeight / 4, false, -4f, 0, f, f1);
        
        swing(right_middle_2_leg_1_1, speed / 4f, degree / 2f, false, -2.7f, 0, f, f1);
        flap(right_middle_2_leg_1_1, speed / 4f, legHeight / 4, false, -4.7f, 0, f, f1);
        //leg movement
     
        swing(left_fang_1, speed / 2f, degree / 4f, false, -2.7f, 0, f, f1);
        swing(right_fang_1, speed / 2f, degree / 4f, false, -2.7f, 0, f, f1);
        
        swing(left_fang_3, speed / 2f, degree / 2f, false, -2.7f, 0, f, f1);
        swing(right_fang_3, speed / 2f, degree / 2f, false, -2.7f, 0, f, f1);
        */
/*
        flap(left_back_leg_1, speed / 2, legHeight / 4, false, -2f, 0, f, f1);
        
        swing(left_middle_leg_1, speed / 2, degree / 2, false, -2f, 0, f, f1);
        flap(left_middle_leg_1, speed / 2, legHeight / 4, false, -4f, 0, f, f1);
        
        swing(left_front_leg_1, speed / 2, degree / 2, false, -1, 0, f, f1);
        flap(left_front_leg_1, speed / 2, legHeight / 4, false, -3f, 0, f, f1);
        *//*

        
    
    }
*/
/*

    @Override
    public void render(EntityGiantSpider entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body.render(f5);
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
