package skeeter144.toc.items.weapons;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;

public class TOCGreatAxe extends ItemSword implements ISpecialAttackWeapon{

	boolean hasSpecial = false;
	float damage, speed;
	public TOCGreatAxe(IItemTier tier, Item.Properties builder, float damage, float speed, boolean hasSpecial) {
		super(tier, (int) damage, speed, builder);
		this.hasSpecial = hasSpecial;
		this.damage = damage;
		this.speed = speed;	
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
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Base Damage: " + (int)(this.damage * CombatManager.LOWER_DAMAGE_PCT) + " - " + (int)this.damage));
	}
}
