package skeeter144.toc.entity.AI;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAICrazyRunIdle extends EntityAIBase{

	EntityCreature e;
	float speed = 1;
    protected double randPosX;
    protected double randPosY;
    protected double randPosZ;
    
    protected BlockPos[] prevPoses = new BlockPos[3];
    int posIndex = 0;
    boolean posesFull = false;
    
	public EntityAICrazyRunIdle(EntityCreature e, float speed) {
		this.e = e;
		this.speed = speed;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		 if (this.e.getRevengeTarget() == null)
	     {
			 for(int i = 0; i < 5; ++i) {
				 if(this.findRandomPosition())
					 return true;
			 }
	     }
		 
		return false;
	}

	 public void startExecuting()
	 {
		 this.e.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
	 }
	 
	 protected boolean findRandomPosition()
	 {
	        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.e, 7, 4);

	        if (vec3d == null)
	        {
	            return false;
	        }
	     else
	     {
	         this.randPosX = vec3d.x;
	         this.randPosY = vec3d.y;
	         this.randPosZ = vec3d.z;
	         return true;
	     }
	}
	
	 public boolean shouldContinueExecuting()
	{
		 if(e.getAttackTarget() != null || e.getRevengeTarget() != null)
			 return false;
		 
		 if(this.e.getNavigator().noPath()) {
			 if(prevPoses[0] == null) {
				 for(int i = 0; i < 5; ++i) {
					 if(this.findRandomPosition()) {
						 prevPoses[1] = new BlockPos(randPosX, randPosY, randPosZ);
						 break;
					 }
				 }
			 }
			 if(prevPoses[1] == null) {
				 for(int i = 0; i < 5; ++i) {
					 if(this.findRandomPosition()) {
						 prevPoses[1] = new BlockPos(randPosX, randPosY, randPosZ);
						 break;
					 }
				 }
			 }
			 
			 if(prevPoses[2] == null) {
				 for(int i = 0; i < 5; ++i) {
					 if(this.findRandomPosition()) {
						 prevPoses[2] = new BlockPos(randPosX, randPosY, randPosZ);
						 break;
					 }
				 }
			 }
			 
			 
			 if(++posIndex >= 3) {
				 posIndex = 0;
				 prevPoses[0] = prevPoses[1];
				 prevPoses[1] = prevPoses[2];
				 boolean found = false;
				 for(int i = 0; i < 5; ++i) {
					 found = this.findRandomPosition();
					 if(found)
						 break;
				 }
				 prevPoses[2] = found ? new BlockPos(randPosX, randPosY, randPosZ) : prevPoses[0];
				 this.e.getNavigator().tryMoveToXYZ(prevPoses[0].getX(), prevPoses[0].getY(), prevPoses[0].getZ(), speed);
				 return true;
			 }else{
				 if(this.e.getNavigator() != null && this.prevPoses != null && this.prevPoses.length > posIndex && this.prevPoses[posIndex] != null)
					 this.e.getNavigator().tryMoveToXYZ(prevPoses[posIndex].getX(), prevPoses[posIndex].getY(), prevPoses[posIndex].getZ(), speed);
			 }
		 }
		 return false;
	}
	 
}
