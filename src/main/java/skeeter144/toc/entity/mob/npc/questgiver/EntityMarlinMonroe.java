package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;
import skeeter144.toc.util.Util;

public class EntityMarlinMonroe extends EntityNPCInteractable{
	
	public EntityMarlinMonroe(World worldIn) {
		this(TOCEntityType.MARLIN_MONROE, worldIn);
	}
	
	public EntityMarlinMonroe(EntityType<? extends EntityNpc> type, World worldIn) {
		super(type, worldIn);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/marlin_monroe.png");
	}
	
	@Override
	protected boolean processInteract(PlayerEntity player, Hand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);

		if(qp.evaFinished && !qp.marlinTalkedTo) {
			sendDialog("intro", player);
			qp.marlinTalkedTo = true;
			qp.save();
		}
		return true;
	}
	
	public void marlinFinished(UUID playerUUID) {
		PlayerEntity player = world.getPlayerByUuid(playerUUID);
		Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Meet Kelvin Whitestone in the mines to learn about Mining, Smithing, and Combat.");
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		qp.marlinFinished = true;
		qp.save();
	}
	
	

	
}
