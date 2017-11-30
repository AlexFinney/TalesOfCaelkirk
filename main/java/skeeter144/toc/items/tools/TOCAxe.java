package skeeter144.toc.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class TOCAxe extends ItemAxe {

	public TOCAxe(String name, float damage, float speed, ToolMaterial material) {
		super(material, damage, speed);
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Reference.MODID, name));
		setMaxStackSize(1);
		setMaxDamage(-1);
	}
}
