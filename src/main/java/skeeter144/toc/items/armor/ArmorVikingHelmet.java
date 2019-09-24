package skeeter144.toc.items.armor;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skeeter144.toc.TOCMain;
import skeeter144.toc.util.Reference;

public class ArmorVikingHelmet extends CustomArmor{

	public ArmorVikingHelmet(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn,float physR, float magicR, float rangedR) {
		super(materialIn, renderIndexIn, equipmentSlotIn, "viking_helmet", physR, magicR, rangedR);
		this.setMaxDamage(-1);
	}
	
	@Override 	
	@OnlyIn(Dist.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
		if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
			ModelBiped model = TOCMain.proxy.getModelForId(0);
			
			model.bipedHead.showModel = true;
			
			model.isSneak = defaultModel.isSneak;
			model.isRiding = defaultModel.isRiding;
			model.isChild = defaultModel.isChild;
			
			
			model.setModelAttributes(defaultModel);
			
			return model;
		}
		return null;
	}
	
}
