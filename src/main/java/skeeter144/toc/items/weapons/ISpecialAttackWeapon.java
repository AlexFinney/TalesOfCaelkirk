package skeeter144.toc.items.weapons;

import org.apache.commons.lang3.tuple.MutablePair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpecialAttackCooldownMessage;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.util.PlayerManager;
import skeeter144.toc.util.Util;

public interface ISpecialAttackWeapon {
	
	public void doAttack(EntityPlayer attacker, EnumHand hand, Entity attacked);
	
	default boolean fireSpecialAttack(EntityPlayer attacker, EnumHand hand, Entity attacked, int cooldown, boolean targetRequired) {
		if(hand == EnumHand.OFF_HAND)
			 return false;
		
		ItemStack item = attacker.getHeldItem(hand);
		Entity e = Util.getPointedEntity(attacker, 1, 4);
		
		if(!targetRequired || targetRequired && e != null) {
			if(!attacker.world.isRemote) {
				
				TOCPlayer pl = PlayerManager.instance().getPlayer(attacker);	
				if(!pl.specialAttackCooldowns.containsKey(item.getItem().getRegistryName().toString())) {
					doAttack(attacker, hand, e);
					pl.specialAttackCooldowns.put(item.getItem().getRegistryName().toString(), new MutablePair<Integer, Integer>(cooldown, cooldown));
					
					Network.INSTANCE.sendTo(new SpecialAttackCooldownMessage(item.getItem().getRegistryName().toString(), 
							(byte)cooldown, (byte)cooldown), (EntityPlayerMP) pl.mcEntity);
					
					return true;
				}else {
					return false;
				}
			}else {
				if(!TOCMain.localPlayer.specialAttackCooldowns.containsKey(item.getItem().getRegistryName().toString())) {
					doAttack(attacker, hand, e);
				}
				
			}
		}
		
		return false;
	}
	
	
}
