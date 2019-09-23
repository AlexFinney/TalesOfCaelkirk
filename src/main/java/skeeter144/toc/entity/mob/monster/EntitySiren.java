package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.sounds.Sounds;

public class EntitySiren extends CustomMob{

	boolean isRapidSwim = false; 
	public EntitySiren(World worldIn) {
		super(worldIn);
		
		this.attackLevel = 10;
		this.strengthLevel = 25;
		this.hpLevel = 17;
		this.defenseLevel = 13;
		this.magicLevel = 1;
		this.xpGiven = 170;
		
		
		this.setDropChance(EntityEquipmentSlot.CHEST, .05f);
		this.setDropChance(EntityEquipmentSlot.HEAD, .05f);
		this.setDropChance(EntityEquipmentSlot.LEGS, .05f);
		this.setDropChance(EntityEquipmentSlot.FEET, .05f);
		this.setDropChance(EntityEquipmentSlot.MAINHAND, .05f);
		this.setDropChance(EntityEquipmentSlot.OFFHAND, .05f);

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.25);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.hpLevel * EntityLevels.HP_PER_LEVEL);
		this.setHealth(this.hpLevel * EntityLevels.HP_PER_LEVEL);
		this.setSize(0.7f, 2f);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(2, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
		this.tasks.addTask(3, new  EntityAIMoveTowardsRestriction(this, 1.0));
		this.tasks.addTask(4, new  EntityAIWander(this, 1.0));
		this.tasks.addTask(5, new  EntityAIWatchClosest(this, EntityPlayer.class, 20));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true));

	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		
	}
	
	@Override 
	public boolean isAIDisabled() {
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();		
		
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
