package skeeter144.toc.entity.mob.passive.questgiver;

import java.lang.reflect.Method;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.client.gui.NpcDialogResponse;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShowQuestDialogMessage;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;

public class EntityUlricWeston extends EntityNPCInteractable{
	
	public EntityUlricWeston(World worldIn) {
		super(worldIn, QuestManager.aNewAdventure);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/bob_rat_man.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		if(!qp.questStarted)
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "quest_offer"), (EntityPlayerMP)player);
		else 
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "robert_player_returned"), (EntityPlayerMP)player);
		
		return true;
	}

	@Override
	public void handleDialogResponse(UUID playerUUID, String dialogResponse) {
		for(NpcDialog dialog : QuestManager.aNewAdventure.questDialogs.values()) {
			for(NpcDialogResponse response : dialog.playerResponses) {
				if(response.serverEventFunc.equals(dialogResponse)) {
					try {
						Method m = this.getClass().getMethod(dialogResponse, UUID.class);
						m.invoke(this, playerUUID);
						return;
					}catch(Exception e) {
						System.out.println("ERROR! Server Function Not found: " + dialogResponse);
						e.printStackTrace();
					}
				}
			}
		}
		
		//CUSTOM DIALOGS DOWN HERE
	}
	
	public void onQuestStarted(UUID playerUUID) {
		QuestManager.startPlayerOnQuest(playerUUID, QuestManager.aNewAdventure);
		
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
		player.sendMessage(new TextComponentString(TextFormatting.GREEN  + "[" +  QuestManager.aNewAdventure.name + "]" + TextFormatting.BLUE +" Speak with Ulric"));
		//mark point on map
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(playerUUID, QuestManager.aNewAdventure);
		qp.questStarted = true;
	}
	
	
}
