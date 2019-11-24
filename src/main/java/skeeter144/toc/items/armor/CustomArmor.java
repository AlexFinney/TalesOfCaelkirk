package skeeter144.toc.items.armor;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import skeeter144.toc.util.Reference;

public class CustomArmor extends ArmorItem{

	public final float physicalResistance;
	public final float magicalResistance;
	public final float rangedResistance;
	
	public CustomArmor(IArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, Item.Properties builder, String name, float physicalResistance,
			float magicalResistance, float rangedResistance) {
		super(materialIn, equipmentSlotIn, builder);
		setRegistryName(Reference.MODID, name);
		this.physicalResistance = physicalResistance;
		this.magicalResistance = magicalResistance;
		this.rangedResistance = rangedResistance;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(""));
		tooltip.add(new StringTextComponent("Physical Resistance: %" + 100 * physicalResistance));
		tooltip.add(new StringTextComponent("Magical Resistance: %" + 100 * magicalResistance));
		tooltip.add(new StringTextComponent("Ranged Resistance: %" + 100 * rangedResistance));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}


	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		 return HashMultimap.<String, AttributeModifier>create();
	}
}
