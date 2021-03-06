package skeeter144.toc.entity.AI;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import skeeter144.toc.client.entity.animation.Animations;
import skeeter144.toc.entity.mob.monster.EntityGiantScorpian;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.PlayMobAnimationMessage;

public class EntityAIScorpianSting extends EntityAIBase{

	EntityGiantScorpian e;
	float lastAttackTime, ticksBetweenAttacks, maxDamage;
	public EntityAIScorpianSting(EntityGiantScorpian e, float ticksBetweenAttacks, float maxDamage) {
		this.e = e;
		this.ticksBetweenAttacks = ticksBetweenAttacks;
		this.ticksBetweenAttacks = 20;
		this.maxDamage = maxDamage;
	}
	
	@Override
	public boolean shouldExecute() {
		
		float distToSting = 3;
		float timeDelta = (e.ticksExisted - lastAttackTime);
		if(e.currentAnim == null &&  timeDelta > ticksBetweenAttacks && e.getAttackTarget() != null) {
			if(e.getPositionVector().distanceTo(e.getAttackTarget().getPositionVector()) < distToSting) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void startExecuting()
	{
		Network.INSTANCE.sendToAllAround(new PlayMobAnimationMessage(e,Animations.SCORPIAN_STING), 
			new TargetPoint(e.getEntityWorld().provider.getDimension(), e.posX, e.posY, e.posZ, 200));
		e.playAnimation(Animations.get(Animations.SCORPIAN_STING));
		lastAttackTime = e.ticksExisted;
	}
	
	@Override
	public boolean shouldContinueExecuting()
	{
		if(e.currentAnim != null) {
			return true;
		}
		return false;
	}
	
}
