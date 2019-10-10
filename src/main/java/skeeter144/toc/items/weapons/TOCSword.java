package skeeter144.toc.items.weapons;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.util.Reference;

public class TOCSword extends ItemSword{
	
	float damage, speed;
	public final Levels xpLeveled;
	public TOCSword(IItemTier tier, Item.Properties builder, String name, float damage, float speed, Levels xpLeveled) {
		super(tier, (int) damage, speed, builder);
		this.damage = damage;
		this.speed = speed;
		this.xpLeveled = xpLeveled;
		setRegistryName(Reference.MODID, name);
	}

	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> map = HashMultimap.<String, AttributeModifier>create();
		 if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	     {
			 map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
			 map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speed, 0));
	     }
		return map;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString(TextFormatting.YELLOW +  "Base Damage: " + (int)(this.damage * CombatManager.LOWER_DAMAGE_PCT) + " - " + (int)this.damage));
	}
	
}
