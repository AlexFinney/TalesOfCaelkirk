package skeeter144.toc.entity.mob.passive.questgiver;

import net.minecraft.world.World;
import skeeter144.toc.quest.Quest;

public abstract class EntityHumanQuestGiver extends EntityQuestGiver{
	public EntityHumanQuestGiver(World worldIn, Quest ...quests) {
		super(worldIn, quests);
	}
}
