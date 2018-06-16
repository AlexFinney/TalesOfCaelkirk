package skeeter144.toc.entity.mob.passive.questgiver;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import skeeter144.toc.quest.Quest;

public abstract class EntityNPCInteractable extends EntityQuestGiver{
	public EntityNPCInteractable(World worldIn, Quest ...quests) {
		super(worldIn, quests);
	}
	
	protected abstract boolean processInteract(EntityPlayer player, EnumHand hand);
	public abstract void handleDialogResponse(UUID playerUUID, String dialogResponse);
}
