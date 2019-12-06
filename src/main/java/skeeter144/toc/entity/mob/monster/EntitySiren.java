package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.AI.EntityAICrazyRunIdle;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.sounds.Sounds;

public class EntitySiren extends CustomMob{

	public EntitySiren(World worldIn) {
		this(TOCEntityType.SIREN, worldIn);
	}
	
	boolean isRapidSwim = false; 
	public EntitySiren(EntityType<? extends CustomMob> type, World worldIn) {
		super(type, worldIn);
		
		this.attackLevel = 10;
		this.strengthLevel = 25;
		this.hpLevel = 17;
		this.defenseLevel = 13;
		this.magicLevel = 1;
		this.xpGiven = 170;
		
		
		this.setDropChance(EquipmentSlotType.CHEST, .05f);
		this.setDropChance(EquipmentSlotType.HEAD, .05f);
		this.setDropChance(EquipmentSlotType.LEGS, .05f);
		this.setDropChance(EquipmentSlotType.FEET, .05f);
		this.setDropChance(EquipmentSlotType.MAINHAND, .05f);
		this.setDropChance(EquipmentSlotType.OFFHAND, .05f);

		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.25);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.hpLevel * EntityLevels.HP_PER_LEVEL);
		this.setHealth(this.hpLevel * EntityLevels.HP_PER_LEVEL);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(1, new FindWaterGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 0, true, true, null));
	}

//	@Override
//	protected void initEntityAI() {
//		this.tasks.addTask(2, new EntityAISwimming(this));
//		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
//		this.tasks.addTask(3, new  EntityAIMoveTowardsRestriction(this, 1.0));
//		this.tasks.addTask(4, new  EntityAIWander(this, 1.0));
//		this.tasks.addTask(5, new  EntityAIWatchClosest(this, PlayerEntity.class, 20));
//		this.tasks.addTask(6, new EntityAILookIdle(this));
//		
//		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true, true));
//
//	}
	
	@Override 
	public boolean isAIDisabled() {
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	
	
	@Override
	public void tick() {
		super.tick();		
		
		isRapidSwim = getAttackTarget() != null;
	}

 	public boolean getIsRapidSwim() {return isRapidSwim;}

	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		
		if(this.world.isRemote)
			return;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return Sounds.man_die;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource d) {
		return TOCMain.rand.nextInt(2) == 0 ? Sounds.man_hurt : Sounds.man_grunt;
	}
	
}
