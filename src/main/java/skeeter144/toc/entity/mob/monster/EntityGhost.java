package skeeter144.toc.entity.mob.monster;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.entity.mob.monster.EntityGhost.AIGhostDiveAttack.DiveStage;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.sounds.Sounds;

public class EntityGhost extends CustomMob{
	public int ticksInCurrentStage = 0;

	public static final IAttribute DIVE_STAGE = new IAttribute() {
		public String getName() {return "DiveStage";}
		public double clampValue(double value) {return value;}
		public double getDefaultValue() {return 0;}
		public boolean getShouldWatch() {return true;}
		public IAttribute getParent() {	return null;}
	};
	
	public EntityGhost(World worldIn) {
		this(TOCEntityType.GHOST, worldIn);
	}
	
	public EntityGhost(EntityType<? extends CustomMob> type, World worldIn) {
		super(type, worldIn);
		
		this.setNoGravity(true);
		this.attackLevel = 10;
		this.strengthLevel = 20;
		this.defenseLevel = 10;
		this.magicLevel = 1;
		this.xpGiven = 120;
		
		this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1f);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10f);
		this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1f);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.8f);
		
		this.setHealth(50f);	
	}
	
//	@Override
//	protected void initEntityAI() {
//		this.tasks.addTask(0, new EntityAISwimming(this));
//		this.tasks.addTask(1, new AIGhostDiveAttack(this));
//		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1, false));
//		this.tasks.addTask(5, new AIRandomFly(this));
//		this.tasks.addTask(6, new EntityAILookIdle(this));
//
//		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true, true));
//	}
	
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		 this.getAttributes().registerAttribute(DIVE_STAGE);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.navigator = new FlyingPathNavigator(this, world);
		this.moveController = new FlyingMovementController(this);
		
		this.goalSelector.addGoal(1, new AIRandomFly(this));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(EntityGhost.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}
	
	public void setDiveStage(DiveStage stage) {
		this.getAttribute(DIVE_STAGE).setBaseValue(stage.ordinal());
		this.ticksInCurrentStage = 0;
	}
	
	@Override
	public void tick() {
		super.tick();
		++ticksInCurrentStage;
	}
	
	
	@Override
	public boolean isAIDisabled() {
		return false;
	}
	
	public void fall(float distance, float damageMultiplier) {}
	
	public void travel(float strafe, float vertical, float forward) {

		if (this.isInWater()) {
			this.moveRelative(0.02F, new Vec3d(strafe, vertical, forward));
			this.move(MoverType.SELF, new Vec3d(this.getMotion().x, this.getMotion().y, this.getMotion().z));
			this.getMotion().scale(0.800000011920929D);
		} else if (this.isInLava()) {
			this.moveRelative(strafe, new Vec3d(vertical, forward, 0.02F));
			this.move(MoverType.SELF, new Vec3d(this.getMotion().x, this.getMotion().y, this.getMotion().z));
			this.getMotion().scale(0.5D);
		} else {
			float f = 0.91F;

			if (this.onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(this.posX),
						MathHelper.floor(this.getBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
				BlockState underState = this.world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
			}

			float f1 = 0.16277136F / (f * f * f);
			this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, new Vec3d(strafe, vertical, forward));
			f = 0.91F;

			if (this.onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(this.posX),
						MathHelper.floor(this.getBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
				BlockState underState = this.world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
			}

			this.move(MoverType.SELF, new Vec3d(this.getMotion().x, this.getMotion().y, this.getMotion().z));
			this.getMotion().scale((double) f);
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d1 = this.posX - this.prevPosX;
		double d0 = this.posZ - this.prevPosZ;
		float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	static class AIRandomFly extends Goal {
		private final EntityGhost parentEntity;

		public AIRandomFly(EntityGhost ghost) {
			this.parentEntity = ghost;
			//this.setMutexBits(3);
		}

		public boolean shouldExecute() {
			MovementController entitymovehelper = this.parentEntity.getMoveHelper();

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

	public static class AIGhostDiveAttack extends Goal {
		
		EntityGhost ghost;
		BlockPos raisePos;
		DiveStage currentStage = DiveStage.IDLE;
		
		public AIGhostDiveAttack(EntityGhost ghost) {
			this.ghost = ghost;
			//this.setMutexBits(3);
		}

		public boolean shouldExecute() {
			return ghost.getAttackTarget() != null && currentStage == DiveStage.IDLE 
					&& ghost.ticksInCurrentStage  >= 100 && TOCMain.rand.nextInt(100) == 0;
		}

		@Override
		public void startExecuting() {
			ghost.setDiveStage(DiveStage.ANGRY);
			
			ghost.world.playSound(null, ghost.getPosition(), Sounds.ghost_scream_angry, SoundCategory.MASTER, 1, 1);
			
			int max = 25;
			int highest = 0;
			for(int i = 0; i < max; ++i) {
				BlockPos pos = new BlockPos(ghost.posX, ghost.posY + i, ghost.posZ);
				if(!ghost.world.getBlockState(pos).isAir(ghost.world, pos)) {
					highest = (int)ghost.posY + i;
				}
			}
			
			raisePos = ghost.getPosition().add(0, highest - ghost.getPosition().getY(), 0);
		}
		
		@Override
		public void tick() {
			if(ghost.getAttackTarget() == null)
				return;
			
			int stage = (int)ghost.getAttribute(EntityGhost.DIVE_STAGE).getBaseValue();
			 
			if(stage == DiveStage.ANGRY.ordinal()) {
				if(ghost.ticksInCurrentStage > 30) {
					//teleport
					spawnTeleportParticles(ghost.getPosition());
					ghost.world.playSound(null, ghost.getPosition(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 1, 1);
					ghost.setPosition(raisePos.getX(), raisePos.getY(), raisePos.getZ());

					
					
					ghost.setDiveStage(DiveStage.SCREAMING);
				}
			}
			
			if(stage == DiveStage.SCREAMING.ordinal()) {
				if(ghost.ticksInCurrentStage > 30) {
					ghost.world.playSound(null, ghost.getAttackTarget().getPosition(), Sounds.ghost_scream, SoundCategory.MASTER, 1, 1);
					
					ghost.setDiveStage(DiveStage.DIVING);
				}
			}
			
			if(stage == DiveStage.DIVING.ordinal()) {
				BlockPos targetPos = ghost.getAttackTarget().getPosition();
				
				Vec3d moveVec = ghost.getPositionVector().subtract(ghost.getAttackTarget().getPositionVector());
				moveVec = moveVec.normalize();
				
				moveVec = moveVec.scale(.5);
				
				double dx = ghost.posX - ghost.getAttackTarget().posX;
				double dz = ghost.posZ - ghost.getAttackTarget().posZ;
				
				
				float f9 = (float)(MathHelper.atan2(dz, dx) * (180D / Math.PI)) + 90.0F;
		        ghost.rotationYaw = this.limitAngle(ghost.rotationYaw, f9, 90.0F);
				
				ghost.getMotion().scale(-moveVec.x);
				
				double dy = ghost.getAttackTarget().posY - ghost.posX;
            	
            	double dist = ghost.getAttackTarget().getPositionVector().distanceTo(ghost.getPositionVector());
            	ghost.rotationPitch = (float)Math.asin(dy / dist);
				
				
				if(ghost.getPosition().distanceSq(targetPos) < 2) {
					ghost.getAttackTarget().attackEntityFrom(DamageSource.GENERIC, 40);
					ghost.setDiveStage(DiveStage.IDLE);
					
				}
			}
			
		}

		@Override
		public void resetTask() {
			ghost.setDiveStage(DiveStage.IDLE);
		}
		
		@Override
		public boolean shouldContinueExecuting() {
			return ((int)ghost.getAttribute(EntityGhost.DIVE_STAGE).getBaseValue() != DiveStage.IDLE.ordinal());
		}
		
		public enum DiveStage{
			IDLE,
			ANGRY,
			SCREAMING,
			DIVING
		}
		
		void spawnTeleportParticles(BlockPos pos) {
			Network.INSTANCE.sendToAllAround(new SpawnParticlesPKT(ParticleSystem.GHOST_TELEPORT_SYSTEM,
																	   new BlockPos(pos.getX() + .5f, pos.getY() + 1.5f, pos.getZ() + .5f)),
											 (Chunk)ghost.world.getChunk(pos));
		}
		
		float limitAngle(float sourceAngle, float targetAngle, float maximumChange)
	    {
	        float f = MathHelper.wrapDegrees(targetAngle - sourceAngle);

	        if (f > maximumChange)
	        {
	            f = maximumChange;
	        }

	        if (f < -maximumChange)
	        {
	            f = -maximumChange;
	        }

	        float f1 = sourceAngle + f;

	        if (f1 < 0.0F)
	        {
	            f1 += 360.0F;
	        }
	        else if (f1 > 360.0F)
	        {
	            f1 -= 360.0F;
	        }

	        return f1;
	    }
		
	}
}
