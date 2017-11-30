package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.sounds.Sounds;

public class EntityGoblin extends CustomMob{

	public EntityGoblin(World worldIn) {
		super(worldIn);
		
		this.attackLevel = 3;
		this.strengthLevel = 5;
		this.hpLevel = 4;
		this.defenseLevel = 1;
		this.magicLevel = 1;
		this.xpGiven = 32;
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1, false));
		this.tasks.addTask(4, new  EntityAIWander(this, 1.0));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		
		this.setSize(.5f, 1.5f);
		this.setHealth(10f);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.25);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.4f);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return TOCMain.rand.nextInt(10) == 0 ? Sounds.goblin_laugh : Sounds.goblin_breathe;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return Sounds.goblin_die;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource d) {
		return Sounds.goblin_snarl;
	}

}
