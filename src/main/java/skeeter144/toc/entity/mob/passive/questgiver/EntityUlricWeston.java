package skeeter144.toc.entity.mob.passive.questgiver;

import java.lang.reflect.Method;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.client.gui.NpcDialogResponse;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SendIconUpdateMessage;
import skeeter144.toc.network.ShowQuestDialogMessage;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.TOCUtils;

public class EntityUlricWeston extends EntityNPCInteractable{
	
	public EntityUlricWeston(World worldIn) {
		super(worldIn, QuestManager.aNewAdventure);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/ulric_weston.png");
		
		this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(TOCItems.axe_steel));
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		//QuestManager.playerQuestProgresses.clear();
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		if(qp == null || !qp.questStarted) {
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "ulric_refuse"), (EntityPlayerMP)player);
		}
		else if(!qp.ulricTalkedTo){
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "ulric_intro"), (EntityPlayerMP)player);
		}else if(qp.logsChopped < 10){
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "ulric_not_done_yet"), (EntityPlayerMP)player);
		}else if(!qp.ulricFinished) {
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "ulric_finished"), (EntityPlayerMP)player);
		}else {
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "ulric_finished_finally"), (EntityPlayerMP)player);
		}
		
		return true;
	}
	
	public void ulricFinished(UUID playerUUID) {
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(playerUUID, QuestManager.aNewAdventure);
		if(qp.ulricFinished)
			return;
		
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
		player.addItemStackToInventory(new ItemStack(TOCItems.copper_coin, 20));
		TOCUtils.removeItemsFromInventory(new ItemStack(TOCBlocks.oak_log).getItem(), 5, player.inventory);
		
		qp.ulricFinished = true;
	}
	
	public void beginLogChopping(UUID playerUUID) {
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(playerUUID, QuestManager.aNewAdventure);
		qp.ulricTalkedTo = true;
		qp.logsChopped = 0;
		
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
		player.sendMessage(new TextComponentString(TextFormatting.GREEN  + "[" +  QuestManager.aNewAdventure.name + "] [New Task]" + TextFormatting.BLUE +" Chop down 10 Oak Logs."));
		
		player.addItemStackToInventory(new ItemStack(TOCItems.axe_bronze));
		//mark point on map
		Network.INSTANCE.sendTo(new SendIconUpdateMessage("Speak with Ulric", 719, 42, 811, player.world.provider.getDimension(), Reference.MODID, "textures/icons/map/quest_objective.png", true), (EntityPlayerMP)player);
		Network.INSTANCE.sendTo(new SendIconUpdateMessage("Chop down oak logs", 736, 42, 815, player.world.provider.getDimension(), Reference.MODID, "textures/icons/map/quest_objective.png"), (EntityPlayerMP)player);
		
	}
	
	
}
