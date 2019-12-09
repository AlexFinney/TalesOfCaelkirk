package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;

public class EntitySeloviusKamazz extends EntityNPCInteractable{
	
	public EntitySeloviusKamazz(World worldIn) {
		this(TOCEntityType.SELOVIUS_KAMAZZ, worldIn);
	}
	
	public EntitySeloviusKamazz(EntityType<? extends EntityNpc> type, World worldIn) {
		super(type, worldIn);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/selovius_kamazz.png");
	}
	
	@Override
	protected boolean processInteract(PlayerEntity player, Hand hand) {
		if(player.world.isRemote)
			return true;
		
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		if(qp.kelvinFinished) {
			if(!qp.seloviusTalkedTo) sendDialog("intro", player);
			else{
				if(!qp.seloviusFinished) sendDialog("finished", player);
				else sendDialog("finished", player);
			}
		}
		return true;
	}

	
	public void chickenKilling(UUID playerUUID) {
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(playerUUID, ANewAdventureQuestProgress.class);
		qp.seloviusTalkedTo = true;
		PlayerEntity pl = this.world.getPlayerByUuid(playerUUID);
		pl.addItemStackToInventory(new ItemStack(TOCItems.wand_basic));
		qp.save();
	}
	
	public void tutorialFinished(UUID playerUUID) {
		PlayerEntity pl = this.world.getPlayerByUuid(playerUUID);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(playerUUID, ANewAdventureQuestProgress.class);
		if (!qp.completed) {
			QuestManager.A_NEW_ADVENTURE.onQuestFinished((ServerPlayerEntity) pl);
			qp.completed = true;
			qp.save();
		}
	}
	
}
