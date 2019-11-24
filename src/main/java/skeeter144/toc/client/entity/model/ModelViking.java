package skeeter144.toc.client.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import skeeter144.toc.entity.mob.monster.EntityViking;

public class ModelViking extends BipedModel<EntityViking>{

	public ModelViking(){
		this(0.0F, false);
	}

	public ModelViking(float modelSize, boolean b) {
		super(modelSize, 0.0F, 64, b ? 32 : 64);
	}
}
