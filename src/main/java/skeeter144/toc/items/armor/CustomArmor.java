package skeeter144.toc.items.armor;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.util.Reference;

public class CustomArmor extends ItemArmor{

	public final float physicalResistance;
	public final float magicalResistance;
	public final float rangedResistance;
	
	public CustomArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String name, float physicalResistance,
			float magicalResistance, float rangedResistance) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setMaxDamage(-1);
		this.physicalResistance = physicalResistance;
		this.magicalResistance = magicalResistance;
		this.rangedResistance = rangedResistance;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("");
		tooltip.add("Physical Resistance: %" + 100 * physicalResistance);
		tooltip.add("Magical Resistance: %" + 100 * magicalResistance);
		tooltip.add("Ranged Resistance: %" + 100 * rangedResistance);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		 return HashMultimap.<String, AttributeModifier>create();
	}
}
