package skeeter144.toc.items.weapons;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.util.Reference;

public class TOCSword extends ItemSword{
	
	float damage, speed;
	public final Levels xpLeveled;
	public TOCSword(String name, float damage, float speed, Levels xpLeveled) {
		super(TOCItems.BRONZE);
		this.damage = damage;
		this.speed = speed;
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setMaxDamage(-1);
		this.xpLeveled = xpLeveled;
	}

	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> map = HashMultimap.<String, AttributeModifier>create();
		 if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	     {
			 map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
			 map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speed, 0));
	     }
		return map;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.YELLOW +  "Base Damage: " + (int)(this.damage * CombatManager.LOWER_DAMAGE_PCT) + " - " + (int)this.damage);
	}
	
}
