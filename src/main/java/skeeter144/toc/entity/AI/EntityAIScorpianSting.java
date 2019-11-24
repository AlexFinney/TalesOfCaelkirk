package skeeter144.toc.entity.AI;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.chunk.Chunk;
import skeeter144.toc.client.entity.animation.Animations;
import skeeter144.toc.entity.mob.monster.EntityGiantScorpian;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.PlayMobAnimationPKT;

public class EntityAIScorpianSting extends Goal{

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
		Network.INSTANCE.sendToAllAround(new PlayMobAnimationPKT(e,Animations.SCORPIAN_STING), 
																	 (Chunk)e.world.getChunk(e.getPosition()));
		
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
