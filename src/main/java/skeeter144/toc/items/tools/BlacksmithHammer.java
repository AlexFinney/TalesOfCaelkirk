package skeeter144.toc.items.tools;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import skeeter144.toc.util.Reference;

public class BlacksmithHammer extends Item{
	public BlacksmithHammer(Item.Properties builder, String name) {
		super(builder);
		setRegistryName(Reference.MODID, name);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Multimap<String, AttributeModifier> mods =  super.getAttributeModifiers(slot, stack);
		 if (slot == EquipmentSlotType.MAINHAND)
	     {
			 mods.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.3f, AttributeModifier.Operation.ADDITION));
	     }
		return mods;
	}
}
