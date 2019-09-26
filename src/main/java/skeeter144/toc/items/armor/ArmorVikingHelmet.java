package skeeter144.toc.items.armor;

import net.minecraft.client.renderer.entity.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skeeter144.toc.TOCMain;

public class ArmorVikingHelmet extends CustomArmor{

	public ArmorVikingHelmet(ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn, Item.Properties builder, float physR, float magicR, float rangedR) {
		super(materialIn, equipmentSlotIn, builder, physR, magicR, rangedR);
	}
	
	@Override 	
	@OnlyIn(Dist.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
		if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
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
