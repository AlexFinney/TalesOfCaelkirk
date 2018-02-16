package skeeter144.toc.entity.mob.monster;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.CustomMob;

public class EntityGhost extends CustomMob{

	public boolean inDiveAttack = false;
	
	public EntityGhost(World worldIn) {
		super(worldIn);
		
		this.setNoGravity(true);
		this.attackLevel = 10;
		this.strengthLevel = 20;
		this.defenseLevel = 10;
		this.magicLevel = 1;
		this.xpGiven = 120;
		
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		
		this.setSize(1f, 2.5f);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.3f);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10f);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(2f);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.8f);
		
		this.setHealth(50f);
		
	}
	
	
	@Override
	protected void initEntityAI() {
		this.navigator = new PathNavigateFlying(this, this.world);
		this.moveHelper = new EntityFlyHelper(this);
		
		 tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new AIGhostDiveAttack(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1, false));
		this.tasks.addTask(5, new AIRandomFly(this));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true));
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {
	}
	
	  public void travel(float strafe, float vertical, float forward)
	    {
	        if (this.isInWater())
	        {
	            this.moveRelative(strafe, vertical, forward, 0.02F);
	            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	            this.motionX *= 0.800000011920929D;
	            this.motionY *= 0.800000011920929D;
	            this.motionZ *= 0.800000011920929D;
	        }
	        else if (this.isInLava())
	        {
	            this.moveRelative(strafe, vertical, forward, 0.02F);
	            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	            this.motionX *= 0.5D;
	            this.motionY *= 0.5D;
	            this.motionZ *= 0.5D;
	        }
	        else
	        {
	            float f = 0.91F;

	            if (this.onGround)
	            {
	                BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
	                IBlockState underState = this.world.getBlockState(underPos);
	                f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
	            }

	            float f1 = 0.16277136F / (f * f * f);
	            this.moveRelative(strafe, vertical, forward, this.onGround ? 0.1F * f1 : 0.02F);
	            f = 0.91F;

	            if (this.onGround)
	            {
	                BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
	                IBlockState underState = this.world.getBlockState(underPos);
	                f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
	            }

	            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	            this.motionX *= (double)f;
	            this.motionY *= (double)f;
	            this.motionZ *= (double)f;
	        }

	        this.prevLimbSwingAmount = this.limbSwingAmount;
	        double d1 = this.posX - this.prevPosX;
	        double d0 = this.posZ - this.prevPosZ;
	        float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

	        if (f2 > 1.0F)
	        {
	            f2 = 1.0F;
	        }

	        this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
	        this.limbSwing += this.limbSwingAmount;
	    }
	
	static class AIRandomFly extends EntityAIBase {
		private final EntityGhost parentEntity;

		public AIRandomFly(EntityGhost ghost) {
			this.parentEntity = ghost;
			this.setMutexBits(3);
		}

		public boolean shouldExecute() {
			EntityMoveHelper entitymovehelper = this.parentEntity
					.getMoveHelper();

			if (!entitymovehelper.isUpdating()) {
				return true;
			} else {
				double d0 = entitymovehelper.getX() - this.parentEntity.posX;
				double d1 = entitymovehelper.getY() - this.parentEntity.posY;
				double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		public boolean shouldContinueExecuting() {
			return false;
		}

		public void startExecuting() {
			Random random = this.parentEntity.getRNG();
			double d0 = this.parentEntity.posX
					+ (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.parentEntity.posY
					+ (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d2 = this.parentEntity.posZ
					+ (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
		}
	}

	static class AIGhostDiveAttack extends EntityAIBase {
		EntityGhost ghost;
		BlockPos raisePos;
		public AIGhostDiveAttack(EntityGhost ghost) {
			this.ghost = ghost;
			this.setMutexBits(3);
		}

		int ticksSinceLastDive = 0;
		public boolean shouldExecute() {
			return ghost.getAttackTarget() != null;
		}

		@Override
		public void startExecuting() {
			ticksSinceLastDive = 0;
			ghost.inDiveAttack = true;

			System.out.println(ghost.getNavigator().tryMoveToXYZ(ghost.posX, ghost.posY + 10, ghost.posZ, 1));
		}

		@Override
		public void updateTask() {
			++ticksSinceLastDive;

		}

		@Override
		public void resetTask() {
			ghost.inDiveAttack = false;
		}

	}
}
