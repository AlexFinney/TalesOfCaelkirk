package skeeter144.toc.entity.mob.monster;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.sounds.Sounds;

public class EntityViking extends CustomMob{

	public boolean isVikingElite = false;
	
	public EntityViking(World world) {
		this(TOCEntityType.VIKING, world);
	}
	
	public EntityViking(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		
		this.attackLevel = 10;
		this.strengthLevel = 25;
		this.hpLevel = 17;
		this.defenseLevel = 13;
		this.magicLevel = 1;
		this.xpGiven = 170;
		
//		this.tasks.addTask(1, new EntityAISwimming(this));
//		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
//		this.tasks.addTask(3, new  EntityAIMoveTowardsRestriction(this, 1.0));
//		this.tasks.addTask(4, new  EntityAIWander(this, 1.0));
//		this.tasks.addTask(5, new  EntityAIWatchClosest(this, PlayerEntity.class, 20));
//		this.tasks.addTask(6, new EntityAILookIdle(this));
//		
//		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true, true));
		
		this.setDropChance(EquipmentSlotType.CHEST, .05f);
		this.setDropChance(EquipmentSlotType.HEAD, .05f);
		this.setDropChance(EquipmentSlotType.LEGS, .05f);
		this.setDropChance(EquipmentSlotType.FEET, .05f);
		this.setDropChance(EquipmentSlotType.MAINHAND, .05f);
		this.setDropChance(EquipmentSlotType.OFFHAND, .05f);

		if(TOCMain.rand.nextInt(20) == 0) {
			isVikingElite = true;
			this.attackLevel = 15;
			this.strengthLevel = 35;
			this.hpLevel = 25;
			this.defenseLevel = 20;
			this.magicLevel = 1;
		}else {
			this.attackLevel = 10;
			this.strengthLevel = 25;
			this.hpLevel = 17;
			this.defenseLevel = 13;
			this.magicLevel = 1;
		}
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(isVikingElite ? .25 : .25);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(isVikingElite ? 20 : 15);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(isVikingElite ? 2 : 1);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.hpLevel * EntityLevels.HP_PER_LEVEL);
		this.setHealth(this.hpLevel * EntityLevels.HP_PER_LEVEL);
	}


	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		
		if(this.world.isRemote)
			return;
		
	}
	
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		if(isVikingElite) {
			this.setCustomName(new StringTextComponent("Viking Elite"));
			//TODO
//			this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(TOCItems.viking_helmet));
//			this.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(TOCItems.viking_chestplate));
//			this.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(TOCItems.viking_leggings));
//			this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(TOCItems.viking_boots));
		//	this.setHeldItem(Hand.MAIN_HAND, new ItemStack(TOCItems.getRandomWeaponForClass("steel", 1)));
			
		}else {
//			if(TOCMain.rand.nextInt(5) == 0)
//				this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(TOCItems.viking_helmet));
//			if(TOCMain.rand.nextInt(5) == 0)
//				this.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(TOCItems.viking_chestplate));
//			if(TOCMain.rand.nextInt(5) == 0)
//				this.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(TOCItems.viking_leggings));
//			if(TOCMain.rand.nextInt(5) == 0)
//				this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(TOCItems.viking_boots));
			
			String weaponType = TOCMain.rand.nextFloat() < .4f ? "steel" : "iron";
		//	this.setHeldItem(Hand.MAIN_HAND, new ItemStack(TOCItems.getRandomWeaponForClass(weaponType, .05f)));
		}
		
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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
