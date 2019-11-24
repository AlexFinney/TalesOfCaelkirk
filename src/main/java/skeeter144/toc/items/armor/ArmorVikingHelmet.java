package skeeter144.toc.items.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArmorVikingHelmet extends CustomArmor{

	public ArmorVikingHelmet(ArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, Item.Properties builder, String name, float physR, float magicR, float rangedR) {
		super(materialIn, equipmentSlotIn, builder, name, physR, magicR, rangedR);
	}
	
	@Override 	
	@OnlyIn(Dist.CLIENT)
	public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel defaultModel) {
		if (itemStack != null && itemStack.getItem() instanceof ArmorItem) {
			//TODO
			//ModelBiped model = TOCMain.proxy.getModelForId(0);
			
//			model.bipedHead.showModel = true;
//			
//			model.isSneak = defaultModel.isSneak;
//			model.isRiding = defaultModel.isRiding;
//			model.isChild = defaultModel.isChild;
//			
//			
//			model.setModelAttributes(defaultModel);
//			
//			return model;
		}
		return null;
	}
	
}
