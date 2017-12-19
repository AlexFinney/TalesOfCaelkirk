package skeeter144.toc.items.weapons;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.Util;

public class TOCGreatSword extends ItemSword implements ISpecialAttackWeapon{
	String name;
	float damage, speed;
	boolean hasSpecial = false;
	public TOCGreatSword(String name, float damage, float speed, boolean hasSpecial) {
		super(TOCItems.STEEL);
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Reference.MODID, name));
		setMaxStackSize(1);
		this.damage = damage;
		this.speed = speed;
		this.hasSpecial = hasSpecial;
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
		
		return new ActionResult<ItemStack>(fireSpecialAttack(player, hand, null, 20, false) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL, 
				player.getHeldItem(hand));
	}


	@Override
	public void doAttack(EntityPlayer attacker, EnumHand hand, Entity attacked) {
		attacker.swingArm(hand);
		attacker.spawnSweepParticles();
		
		if(!attacker.world.isRemote) {
			List<Entity> entities = attacker.world.getEntitiesWithinAABBExcludingEntity(attacker, 
												new AxisAlignedBB(attacker.posX- 5, attacker.posY - 5, attacker.posZ - 5, 
																  attacker.posX + 5, attacker.posY + 5, attacker.posZ + 5));
			for(Entity e : entities) {
				boolean hit = Util.isEntityWithinViewCone(e, attacker, -45, 45);
				
				if(hit) {
					e.attackEntityFrom(new TOCDamageSource(DamageType.PHYSICAL, attacker), this.damage);
				}
			}
		}
	}
}
