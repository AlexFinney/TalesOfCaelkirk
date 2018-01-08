package skeeter144.toc.items.weapons;

import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpecialAttackCooldownMessage;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.util.PlayerManager;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.Util;

public class TOCGreatAxe extends ItemSword implements ISpecialAttackWeapon{

	boolean hasSpecial = false;
	float damage, speed;
	public TOCGreatAxe(String name, float damage, float speed, boolean hasSpecial) {
		super(TOCItems.BRONZE);
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Reference.MODID, name));
		setMaxStackSize(1);
		this.hasSpecial = hasSpecial;
		this.damage = damage;
		this.speed = speed;	
		this.setMaxDamage(-1);
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(hand == EnumHand.OFF_HAND || !hasSpecial)
			 return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(hand));
		
		return new ActionResult<ItemStack>(fireSpecialAttack(player, hand, null, 20, true) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL, 
				player.getHeldItem(hand));
	}


	@Override
	public void doAttack(EntityPlayer attacker, EnumHand hand, Entity attacked) {
		attacker.swingArm(hand);
		attacker.spawnSweepParticles();
		
		if(!attacker.world.isRemote) {
			attacked.attackEntityFrom(new TOCDamageSource(DamageType.PHYSICAL, attacker), this.damage * 2.5f);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.YELLOW + "Base Damage: " + (int)(this.damage * CombatManager.LOWER_DAMAGE_PCT) + " - " + (int)this.damage);
	}
}
