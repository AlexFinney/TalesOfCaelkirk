package skeeter144.toc.items.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import skeeter144.toc.items.TOCItems;

public enum ArmorMaterialList implements IArmorMaterial{
	bronze("bronze", -1, 0, new int[] {4,6,5,3}, TOCItems.ingot_bronze, "", 0);

	public static final int[] max_damage_array = new int[] {13,15,16,11};
	private String name, equipSound;
	private int durability, enchantability;
	private Item repairItem;
	private int[] damageReductionAmount;
	private float toughmess;
	
	private ArmorMaterialList(String name, int durability, int enchantability, int[] damageReductionAmounts, Item repairItem, String equipSound, float toughness ) {
		this.name = name;
		this.durability = durability;
		this.enchantability = enchantability;
		this.damageReductionAmount = damageReductionAmounts;
		this.repairItem = repairItem;
		this.equipSound = equipSound;
		this.toughmess = toughness;
	}
	
	@Override
	public int getDurability(EntityEquipmentSlot slot) {
		return max_damage_array[slot.getIndex()]  * durability;
	}

	@Override
	public int getDamageReductionAmount(EntityEquipmentSlot slot) {
		return damageReductionAmount[slot.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public SoundEvent getSoundEvent() {
		return new SoundEvent(new ResourceLocation(equipSound));
	}

	@Override
	public Ingredient getRepairMaterial() {
		return Ingredient.fromItems(repairItem);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public float getToughness() {
		return toughmess;
	}

}
