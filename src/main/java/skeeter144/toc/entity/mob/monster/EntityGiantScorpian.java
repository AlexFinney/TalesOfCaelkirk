package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
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
	
	public EntityGiantScorpian(EntityType<?> type, World worldIn) {
		super(type, worldIn);

		this.attackLevel = 10;
		this.strengthLevel = 20;
		this.defenseLevel = 10;
		this.magicLevel = 1;
		this.xpGiven = 120;
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIScorpianSting(this, 100, 5));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0, false));
		this.tasks.addTask(5, new  EntityAIWander(this, 1.0));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true));
		
		
		
		this.setSize(1f, 1f);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.3f);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.8f);
		this.setHealth(50f);
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
