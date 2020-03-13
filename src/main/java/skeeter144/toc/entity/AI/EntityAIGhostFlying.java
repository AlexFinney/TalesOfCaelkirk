package skeeter144.toc.entity.AI;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.mob.monster.EntityGhost;

public class EntityAIGhostFlying extends Goal{

	PlayerEntity target;
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
	public void tick() {
		super.tick();
		
		if(++ticksSinceLastTarget > 100) {
			ticksSinceLastTarget = 0;
			
			
			double newPosX = ghost.getPosX() + TOCMain.rand.nextInt(10) - 5;
			double newPosY = ghost.getPosY() + TOCMain.rand.nextInt(10) - 5;
			double newPosZ = ghost.getPosZ() + TOCMain.rand.nextInt(10) - 5;
			
			ghost.getNavigator().tryMoveToXYZ(newPosX, newPosY, newPosZ, .5);
		}
		
	}

}
