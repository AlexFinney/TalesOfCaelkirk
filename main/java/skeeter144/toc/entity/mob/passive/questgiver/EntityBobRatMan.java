package skeeter144.toc.entity.mob.passive.questgiver;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import skeeter144.toc.client.gui.DialogGui;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShowQuestDialogMessage;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.quests.RatSlayerQuest;
import skeeter144.toc.quest.quests.RatSlayerQuest.RatSlayerQuestProgress;

public class EntityBobRatMan extends EntityHumanQuestGiver{
	
	public EntityBobRatMan(World worldIn) {
		super(worldIn, QuestManager.ratSlayer);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/bob_rat_man.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		UUID playerUUID = player.getUniqueID();
		if(!QuestManager.hasPlayerFinishedQuest(playerUUID, QuestManager.ratSlayer)) {
			if(QuestManager.hasPlayerStartedQuest(playerUUID, QuestManager.ratSlayer)) {
				RatSlayerQuestProgress rsqp = (RatSlayerQuestProgress) QuestManager.getQuestProgressForPlayer(playerUUID, QuestManager.ratSlayer);
				if(rsqp.actuallyStarted) {
					if(rsqp.ratsKilled >= rsqp.totalRats) {
						rsqp.finished = true;
						QuestManager.ratSlayer.onQuestFinished((EntityPlayerMP)player);
						Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.entityUniqueID, QuestManager.ratSlayer.id, 
								((RatSlayerQuest)QuestManager.ratSlayer).DIALOG_PLAYER_FINISHED), (EntityPlayerMP) player);
					}else {
						Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.entityUniqueID, QuestManager.ratSlayer.id, 
								((RatSlayerQuest)QuestManager.ratSlayer).DIALOG_PLAYER_IN_PROGRESS), (EntityPlayerMP) player);
					}
				}else {
					Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.entityUniqueID, QuestManager.ratSlayer.id, 
							((RatSlayerQuest)QuestManager.ratSlayer).DIALOG_PLAYER_CAME_BACK), (EntityPlayerMP) player);
				}
			}else {
				Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.entityUniqueID, QuestManager.ratSlayer.id, 
						((RatSlayerQuest)QuestManager.ratSlayer).DIALOG_QUEST_OFFER), (EntityPlayerMP) player);
			}
		}else {
			player.sendMessage(new TextComponentString("[Bob the Rat Man]: If it's not the sicko who slaughtered and mutilated all those innocent rats... go away. You disgust me."));
		}
		
		return true;
	}
}
