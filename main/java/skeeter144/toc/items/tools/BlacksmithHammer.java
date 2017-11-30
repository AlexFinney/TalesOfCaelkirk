package skeeter144.toc.items.tools;

import com.google.common.collect.Multimap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.Reference;

public class BlacksmithHammer extends Item{
	public BlacksmithHammer() {
		this.setUnlocalizedName("blacksmith_hammer");
		this.setRegistryName(new ResourceLocation(Reference.MODID, "blacksmith_hammer"));
		this.setCreativeTab(CreativeTabs.TOOLS);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> mods =  super.getAttributeModifiers(slot, stack);
		 if (slot == EntityEquipmentSlot.MAINHAND)
	     {
			 mods.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.3f, 0));
	     }
		return mods;
	}
}
