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

public class EntityGiantScorpian extends CustomMob{

	public EntityGiantScorpian(World worldIn) {
		this(TOCEntityType.GIANT_SCORPIAN, worldIn);
	}
	
	public EntityGiantScorpian(EntityType<? extends CustomMob> type, World worldIn) {
		super(type, worldIn);

		this.attackLevel = 10;
		this.strengthLevel = 20;
		this.defenseLevel = 10;
		this.magicLevel = 1;
		this.xpGiven = 120;

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.3f);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.8f);
		this.setHealth(50f);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(2, new EntityAIScorpianSting(this, 100, 20));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 0, true, true, null));
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return Sounds.scorpian_idle_1;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return Sounds.scorpian_die;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource d) {
		return Sounds.scorpian_hurt;
	}
}
