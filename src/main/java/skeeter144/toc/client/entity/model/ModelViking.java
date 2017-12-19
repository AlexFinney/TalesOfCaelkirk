package skeeter144.toc.client.entity.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import skeeter144.toc.entity.mob.monster.EntityViking;

public class ModelViking extends ModelBiped{

	public ModelViking(){
		this(0.0F, false);
	}

	public ModelViking(float modelSize, boolean b) {
		super(modelSize, 0.0F, 64, b ? 32 : 64);
	}
}
