package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;
import skeeter144.toc.util.TOCUtils;
import skeeter144.toc.util.Util;

public class EntityUlricWeston extends EntityNPCInteractable{
	
	public EntityUlricWeston(World worldIn) {
		this(TOCEntityType.ULRIC_WESTON, worldIn);
	}
	
	public EntityUlricWeston(EntityType<? extends EntityNpc> type, World worldIn) {
		super(type, worldIn);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/ulric_weston.png");
		
		this.setHeldItem(Hand.MAIN_HAND, new ItemStack(TOCItems.axe_steel));
	}
	
	@Override
	protected boolean processInteract(PlayerEntity player, Hand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		if(qp.ulricFinished) {
			sendDialog("finished4", player);
		}else if(!qp.robertTalkedTo) {
			sendDialog("refuse", player);
		}else if(!qp.ulricTalkedTo) {
			sendDialog("intro", player);
		}else if(qp.logsChopped >= 10) {
			if(TOCUtils.getItemCountInInventory(TOCBlocks.oak_log.asItem(), player.inventory) >= 10) {
				sendDialog("finished", player);
			}else {
				sendDialog("not_done_yet", player);
			}
		} 
		
		return true;
	}
	
	public void ulricFinished(UUID playerUUID) {
		PlayerEntity player = this.world.getPlayerByUuid(playerUUID);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		if(!qp.ulricFinished) {
			qp.ulricFinished = true;
			player.addItemStackToInventory(new ItemStack(TOCItems.copper_coin, 20));
			TOCUtils.removeItemsFromInventory(new ItemStack(TOCBlocks.oak_log).getItem(), 5, player.inventory);
			Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Talk to Eva Teffan to learn about crafting");
			qp.save();
		}
	}
	
	public void beginLogChopping(UUID playerUUID) {
		ServerPlayerEntity player = (ServerPlayerEntity)this.world.getPlayerByUuid(playerUUID);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		qp.ulricTalkedTo = true;
		
		Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Chop down 10 Oak Logs.");
		
		//TODO
		player.addItemStackToInventory(new ItemStack(TOCItems.axe_bronze));
		qp.save();
//		//mark point on map
//		Network.INSTANCE.sendTo(new SendIconUpdateMessage("Speak with Ulric", 719, 42, 811, player.world.provider.getDimension(), Reference.MODID, "textures/icons/map/quest_objective.png", true), (ServerPlayerEntity)player);
//		Network.INSTANCE.sendTo(new SendIconUpdateMessage("Chop down oak logs", 736, 42, 815, player.world.provider.getDimension(), Reference.MODID, "textures/icons/map/quest_objective.png"), (ServerPlayerEntity)player);
		
	}
	
	
}
