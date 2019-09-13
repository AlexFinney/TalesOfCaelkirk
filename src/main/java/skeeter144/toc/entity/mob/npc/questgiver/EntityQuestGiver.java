package skeeter144.toc.entity.mob.npc.questgiver;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.quest.Quest;

public abstract class EntityQuestGiver extends EntityNPCInteractable{

	Quest[] quests;
	public EntityQuestGiver(World worldIn, Quest... quests) {
		super(worldIn);
		this.quests = quests;
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new  EntityAIMoveTowardsRestriction(this, .5f));
		this.tasks.addTask(5, new  EntityAIWander(this, .5f));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0);
		this.setSize(1f, 1f);
		this.setHealth(100f);
	}

	
	@Override
	public boolean attackable() {
		return false;
	}
	
	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}
}
