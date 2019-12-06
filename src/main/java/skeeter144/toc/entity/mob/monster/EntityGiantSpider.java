package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.AI.EntityAIScorpianSting;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.sounds.Sounds;

public class EntityGiantSpider extends CustomMob{

	public EntityGiantSpider(World worldIn) {
		this(TOCEntityType.GIANT_SPIDER, worldIn);
	}
	
	public EntityGiantSpider(EntityType<? extends CustomMob> type, World worldIn) {
		super(type, worldIn);

		this.attackLevel = 40;
		this.strengthLevel = 60;
		this.defenseLevel = 40;
		this.magicLevel = 1;
		this.xpGiven = 213;
		
		
//		this.tasks.addTask(1, new EntityAISwimming(this));
//		this.tasks.addTask(2, new EntityAIAttackMelee(this, .5f, false));
//		this.tasks.addTask(3, new  EntityAIMoveTowardsRestriction(this, .5f));
//		this.tasks.addTask(4, new  EntityAIWander(this, .5f));
//		this.tasks.addTask(6, new EntityAILookIdle(this));
//		
//		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true, true));
		
		
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(75);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.5f);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.2f);
	
		this.setHealth(75f);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 0, true, true, null));
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return Sounds.rat_squeak;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return Sounds.rat_die;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource d) {
		return Sounds.rat_hurt;
	}
}
