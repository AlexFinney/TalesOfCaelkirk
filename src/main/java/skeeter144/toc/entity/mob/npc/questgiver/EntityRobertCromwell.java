package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.data.Database;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.quest.quests.ANewAdventureQuest;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;
import skeeter144.toc.util.Util;

public class EntityRobertCromwell extends EntityNPCInteractable{
	
	public EntityRobertCromwell(World worldIn) {
		this(TOCEntityType.ROBERT_CROMWELL, worldIn);
	}
	
	public EntityRobertCromwell(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/bob_rat_man.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote)
			return true;
		
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);

		if(!qp.robertTalkedTo)
			sendDialog("introduction", player);
		else
			sendDialog("player_returned", player);
		
		return true;
	}

	
	public void startPlayerOnQuest(UUID playerUUID) {
		EntityPlayerMP pl = (EntityPlayerMP)this.world.getPlayerEntityByUUID(playerUUID);
		Util.sendNewTaskMessage(pl, QuestManager.A_NEW_ADVENTURE, "Go speak with Ulric Weston to learn about woodcutting.");
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(playerUUID, ANewAdventureQuestProgress.class);
		qp.robertTalkedTo = true;
		qp.playerId = playerUUID;
		Database.insertQuestProgress(playerUUID, qp.questId, qp);
		
		//TOCUtils.addIconMarkerToMap("Ulric Weston", new ResourceLocation(Reference.MODID, "toc:textures/icons/map/quest_objective"), new BlockPos(720, 42, 815), this.world.provider.getDimension());
	}
	
}
