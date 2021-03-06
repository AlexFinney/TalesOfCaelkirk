package skeeter144.toc.entity.mob;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import skeeter144.toc.util.Util;

public class TOCWaterMob extends CustomMob {
	int breath = 60;
	int ticksSinceDrown = 0;

	public TOCWaterMob(World worldIn) {
		super(worldIn);
		this.tasks.addTask(0, new AIMoveRandom(this));
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		if (!isInWater()) {
			--breath;
			if (breath <= 0) {
				if (ticksSinceDrown == 20) {
					ticksSinceDrown = 0;
					attackEntityFrom(DamageSource.DROWN, 1);
				} else
					++ticksSinceDrown;
			}
		}
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	void setMovementVector(double x, double y, double z) {
		System.out.println(x + " " + y + " " + z);
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
	}
	
	@Override
	public void travel(float strafe, float vertical, float forward) {
		this.move(MoverType.SELF, motionX, motionY, motionZ);
	}

	static class AIMoveRandom extends EntityAIBase {
		private final TOCWaterMob mob;
		int sleepTime = 60;
		
		public AIMoveRandom(TOCWaterMob e) {
			this.mob = e;
		}

		public boolean shouldExecute() {
			return true;
		}

		public void updateTask()
		{
			if(sleepTime == 0) {
				sleepTime = 60;
				BlockPos p = Util.getRandomBlockPosAround(mob.getPosition(), 4, 6, mob.getEntityWorld(), Blocks.WATER);
				if(p != mob.getPosition()) {
					
					Util.LookAt(p.getX(), p.getY(), p.getZ(), mob);
					Vec3d move = Util.getMovementVectorTo(mob.getPosition(),  p);
					mob.setMovementVector(move.x / 4, move.y / 4, move.z / 4);
					
				}
			}
			--sleepTime;
				
		}
	}
}
