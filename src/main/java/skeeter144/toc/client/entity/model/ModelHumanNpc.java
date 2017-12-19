package skeeter144.toc.client.entity.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelVillager;

public class ModelHumanNpc extends ModelBiped{

	public ModelHumanNpc(){
		this(0.0F, false);
	}

	public ModelHumanNpc(float modelSize, boolean b) {
		super(modelSize, 0.0F, 64, b ? 32 : 64);
	}

}
