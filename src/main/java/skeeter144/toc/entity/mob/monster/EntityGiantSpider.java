package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import skeeter144.toc.entity.AI.EntityAIScorpianSting;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.sounds.Sounds;

public class EntityGiantSpider extends CustomMob{

	public EntityGiantSpider(World worldIn) {
		super(worldIn);

		this.attackLevel = 40;
		this.strengthLevel = 60;
		this.defenseLevel = 40;
		this.magicLevel = 1;
		this.xpGiven = 213;
		
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, .5f, false));
		this.tasks.addTask(3, new  EntityAIMoveTowardsRestriction(this, .5f));
		this.tasks.addTask(4, new  EntityAIWander(this, .5f));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true));
		
		
		this.setSize(2f, 2f);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(75);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.5f);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.2f);
	
		this.setHealth(75f);
	}
	
	@Override
	public boolean isEntityInvulnerable(DamageSource source) {
		return super.isEntityInvulnerable(source) && source.equals(DamageSource.FALL);
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
