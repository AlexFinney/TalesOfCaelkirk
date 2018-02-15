package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import skeeter144.toc.entity.AI.EntityAIScorpianSting;
import skeeter144.toc.entity.mob.CustomMob;

public class EntityGhost extends CustomMob{

	public EntityGhost(World worldIn) {
		super(worldIn);
		

		this.attackLevel = 10;
		this.strengthLevel = 20;
		this.defenseLevel = 10;
		this.magicLevel = 1;
		this.xpGiven = 120;
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0, false));
		this.tasks.addTask(5, new  EntityAIWander(this, 1.0));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true));
		
		
		
		this.setSize(1f, 1f);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.3f);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.8f);
		this.setHealth(50f);
	}

}
