package skeeter144.toc.client.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;

public class ModelHumanNpc<T extends LivingEntity> extends BipedModel<T>{

	public ModelHumanNpc(){
		this(0.0F, false);
	}

	public ModelHumanNpc(float modelSize, boolean b) {
		super(modelSize, 0.0F, 64, b ? 32 : 64);
	}

}
