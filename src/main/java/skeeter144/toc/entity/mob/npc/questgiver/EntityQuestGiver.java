package skeeter144.toc.entity.mob.npc.questgiver;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.quest.Quest;

public abstract class EntityQuestGiver extends EntityNPCInteractable{

	Quest[] quests;
	public EntityQuestGiver(EntityType<? extends EntityNpc> type, World worldIn, Quest... quests) {
		super(type, worldIn);
		this.quests = quests;
//		this.tasks.addTask(1, new EntityAISwimming(this));
//		this.tasks.addTask(3, new  EntityAIMoveTowardsRestriction(this, .5f));
//		this.tasks.addTask(5, new  EntityAIWander(this, .5f));
//		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0);
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
