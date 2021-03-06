package skeeter144.toc.entity.AI;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.mob.monster.EntityGhost;

public class EntityAIGhostFlying extends EntityAIBase{

	EntityPlayer target;
	EntityGhost ghost;
	
	int ticksSinceLastTarget = 0;
	
	public EntityAIGhostFlying(EntityGhost ghost) {
		this.ghost = ghost;
	}
	
	@Override
	public boolean shouldExecute() {
		return target == null;
	}
	
	@Override
	public void updateTask() {
		super.updateTask();
		
		if(++ticksSinceLastTarget > 100) {
			ticksSinceLastTarget = 0;
			
			
			double newPosX = ghost.posX + TOCMain.rand.nextInt(10) - 5;
			double newPosY = ghost.posY + TOCMain.rand.nextInt(10) - 5;
			double newPosZ = ghost.posZ + TOCMain.rand.nextInt(10) - 5;
			
			ghost.getNavigator().tryMoveToXYZ(newPosX, newPosY, newPosZ, .5);
		}
		
	}

}
