package skeeter144.toc.entity.mob.npc.shopkeeper;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.util.Reference;

public class EntityHumanShopKeeper extends EntityShopKeeper {

	public EntityHumanShopKeeper(EntityType<?> type, World world) {
		super(type, world, "general_store");
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/sam_derric.png");
	}
	
	public EntityHumanShopKeeper(EntityType<?> type, World worldIn, String shopFileName, String textureName) {
		super(type, worldIn, shopFileName);
		if(texture == null)
			texture = new ResourceLocation(Reference.MODID, "textures/entity/" + textureName + ".png");
	}

}
