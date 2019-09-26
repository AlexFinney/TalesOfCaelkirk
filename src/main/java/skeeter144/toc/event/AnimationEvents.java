package skeeter144.toc.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.effects.PoisonEffect;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Util;

public class AnimationEvents {

	public static void giantScorpianSting(Entity e) {
		CustomMob cm = (CustomMob)e;
		
		if(Util.isEntityWithinViewCone(cm.getAttackTarget(), cm, -20, 20)) {
			//cm.getAttackTarget().attackEntityFrom(new TOCDamageSource(DamageType.PHYSICAL), 
			//		(float) cm.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 2);
			ServerEffectHandler.attemptAddNewEffect(cm.getAttackTarget().getUniqueID(), new PoisonEffect(cm.getAttackTarget(), 10, 160));
			cm.playSound(Sounds.scorpian_sting, 2, 1);
		}
		
	}
	
	public static void giantScorpianStingStart(Entity e) {
		CustomMob cm = (CustomMob)e;
		cm.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0);
		
		double dx =  cm.getAttackTarget().posX - cm.posX;
		double dz =  cm.getAttackTarget().posZ - cm.posZ;
		
		double distance = Math.sqrt(dx * dx + dz * dz);
		double angle = Math.toDegrees(Math.acos(dz / distance));
		
	}
	
	public static void giantScorpianStingFinish(Entity e) {
		CustomMob cm = (CustomMob)e;
		cm.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.3f);
		cm.currentAnim = null;
	}
	
}
