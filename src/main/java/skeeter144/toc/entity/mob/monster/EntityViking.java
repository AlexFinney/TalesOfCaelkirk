package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.sounds.Sounds;

public class EntityViking extends CustomMob{

	public boolean isVikingElite = false;
	
	public EntityViking(World world) {
		this(TOCEntityType.VIKING, world);
	}
	
	public EntityViking(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		
		this.attackLevel = 10;
		this.strengthLevel = 25;
		this.hpLevel = 17;
		this.defenseLevel = 13;
		this.magicLevel = 1;
		this.xpGiven = 170;
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
		this.tasks.addTask(3, new  EntityAIMoveTowardsRestriction(this, 1.0));
		this.tasks.addTask(4, new  EntityAIWander(this, 1.0));
		this.tasks.addTask(5, new  EntityAIWatchClosest(this, EntityPlayer.class, 20));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true));
		
		this.setDropChance(EntityEquipmentSlot.CHEST, .05f);
		this.setDropChance(EntityEquipmentSlot.HEAD, .05f);
		this.setDropChance(EntityEquipmentSlot.LEGS, .05f);
		this.setDropChance(EntityEquipmentSlot.FEET, .05f);
		this.setDropChance(EntityEquipmentSlot.MAINHAND, .05f);
		this.setDropChance(EntityEquipmentSlot.OFFHAND, .05f);

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
		this.setSize(0.7f, 2f);
	}


	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		
		if(this.world.isRemote)
			return;
		
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData entityLivingData,
			NBTTagCompound itemNbt) {
		if(isVikingElite) {
			this.setCustomName(new TextComponentString("Viking Elite"));
			//TODO
//			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(TOCItems.viking_helmet));
//			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(TOCItems.viking_chestplate));
//			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(TOCItems.viking_leggings));
//			this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(TOCItems.viking_boots));
		//	this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(TOCItems.getRandomWeaponForClass("steel", 1)));
			
		}else {
//			if(TOCMain.rand.nextInt(5) == 0)
//				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(TOCItems.viking_helmet));
//			if(TOCMain.rand.nextInt(5) == 0)
//				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(TOCItems.viking_chestplate));
//			if(TOCMain.rand.nextInt(5) == 0)
//				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(TOCItems.viking_leggings));
//			if(TOCMain.rand.nextInt(5) == 0)
//				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(TOCItems.viking_boots));
			
			String weaponType = TOCMain.rand.nextFloat() < .4f ? "steel" : "iron";
		//	this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(TOCItems.getRandomWeaponForClass(weaponType, .05f)));
		}
		
		return super.onInitialSpawn(difficulty, entityLivingData, itemNbt);
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
