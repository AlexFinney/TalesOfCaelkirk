package skeeter144.toc.quest.quests;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.entity.mob.monster.EntityRat;
import skeeter144.toc.network.CloseGuisMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShowQuestDialogMessage;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.quest.QuestUtil;

public class RatSlayerQuest extends Quest{

	public RatSlayerQuest(String name, int id) {
		super(name, id);
		this.experienceRewards.put(Levels.ATTACK, 50);
		this.experienceRewards.put(Levels.HITPOINTS, 40);
		this.itemRewards.put(Items.DIAMOND, 1);
	}

	@Override
	public boolean canPlayerStartQuest(TOCPlayer player) {
		return true;
	}

	
	@SubscribeEvent
	public void entityKilled(LivingDeathEvent e) {
		if(e.getEntity().world.isRemote)
			return;
		
		if(QuestUtil.wasEntityOfTypeAndKilledByPlayer(e, EntityRat.class)) {
			EntityPlayer player = QuestUtil.getPlayerFromEvent(e);
			if(QuestManager.hasPlayerFinishedQuest(player.getUniqueID(), this))
				return;
				
			if(!QuestManager.hasPlayerStartedQuest(player.getUniqueID(), this)) 
				return;
			
			QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), this);
				
			RatSlayerQuestProgress rsqp = (RatSlayerQuestProgress)qp;
			++rsqp.ratsKilled;
			if(rsqp.ratsKilled == rsqp.totalRats) {
				((EntityPlayerMP) player).sendMessage(new TextComponentString("[Rat Slayer] You've killed 5 of the poor guys. Go talk to Bob to claim the reward you traded your innocence for. I hope it was worth it."));
			}else if(rsqp.ratsKilled < rsqp.totalRats){
				((EntityPlayerMP) player).sendMessage(new TextComponentString("[Rat Slayer] " + rsqp.ratsKilled + "/" + rsqp.totalRats + " innocent rats slain."));
			}
		}
	}

	@Override
	public QuestProgress getNewQuestProgressInstance() {
		return new RatSlayerQuestProgress();
	}
	
	@Override
	public Class<? extends QuestProgress> getQuestProgressClass() {
		return RatSlayerQuestProgress.class;
	}
	
	@Override
	protected void questFinished(EntityPlayerMP player) {
		System.out.println("Yay you finished da quest");		
	}
	
	public static class RatSlayerQuestProgress extends QuestProgress{
		public final int totalRats = 5;
		public int ratsKilled = 0;
		public boolean startedAndRejected = false;
		public boolean actuallyStarted = false;
	}


	public final int DIALOG_QUEST_OFFER = 0;
	public final int DIALOG_PLAYER_ACCEPTED_QUEST = 1;
	public final int DIALOG_PLAYER_REJECTED_QUEST = 2;
	public final int DIALOG_PLAYER_IN_PROGRESS = 3;
	public final int DIALOG_PLAYER_FINISHED = 4;
	public final int DIALOG_PLAYER_CAME_BACK = 5;
	protected void createQuestDialogs() {
		questDialogs.put(DIALOG_QUEST_OFFER, new NpcDialog("Hi ho! It's my lucky day! You're so wimpy! A lot of strong warriors travel through here, "
											+ " but it's not very often I get to talk to someone as weak as you... All the strong warriors go on daring, adventurous quests, "
											+ " so they don't have time for me.  Can you slaughter some innocent rats, cut their tails off, and then come back to me with the tails?", 
				"Why?? No!.",
				"Yes! I would love to!"));
		questDialogs.put(DIALOG_PLAYER_ACCEPTED_QUEST, new NpcDialog("I knew you'd say yes.  Someone who is as poor, weak, and mentally deranged as you doesn't really have an option, "
																+ " now do you? Just kill 5 rats and then bring me their tails. Surely you can do that, right?", 
				"Yes...but...rude, much?"));
		
		questDialogs.put(DIALOG_PLAYER_REJECTED_QUEST, new NpcDialog("Oh, ho ho! Look at you! All HIGH and MIGHTY! \"I'm so tough\", \"I'm so strong!\","
											+ " \"I'm too good to kill some rats!\" Well guess what buddy?! You're not! You're weak, and you're a frickin' NOOB!!! You'll come crawling back to me... I know it... come talk to me when you're ready.", 
				"Erm...Okay then..."));
	
		questDialogs.put(DIALOG_PLAYER_IN_PROGRESS, new NpcDialog("What're you doing back so soon? Are you that dullwitted? You still need to kill some more rats!", 
				"Fine..."));
		
		questDialogs.put(DIALOG_PLAYER_CAME_BACK, new NpcDialog("I knew you'd come back, wimp! Now, are you ready to slaughter some rats and cut some tails?", 
				"I, guess?", 
				"No, not yet..."));
		
		questDialogs.put(DIALOG_PLAYER_FINISHED, new NpcDialog("Good job! That's 5 tails! Hahaha! I don't want them! I just had a fun time watching you "
											+ " fight rats! I didn't think you'd actually do it! You cut their tails off?! You're sick!! "
											+ " Heh. Anyway, here's your reward, as promised. Now get out of here, noob.", 
				"..."));
	}

	@Override
	public void handleDialogResponse(EntityPlayerMP player, UUID questGiverUUID, int dialogId, int responseIndex) {
		switch(dialogId) {
		case DIALOG_QUEST_OFFER:
			if(responseIndex == 0) {
				Network.INSTANCE.sendTo(new ShowQuestDialogMessage(questGiverUUID, this.id, DIALOG_PLAYER_REJECTED_QUEST), player);
				QuestManager.startPlayerOnQuest(player.getUniqueID(), this);
				((RatSlayerQuestProgress) QuestManager.getQuestProgressForPlayer(player.getUniqueID(), this)).startedAndRejected = true;
			}else if(responseIndex == 1) {
				Network.INSTANCE.sendTo(new ShowQuestDialogMessage(questGiverUUID, this.id, DIALOG_PLAYER_ACCEPTED_QUEST), player);
				QuestManager.startPlayerOnQuest(player.getUniqueID(), this);
				player.sendMessage(new TextComponentString("You've embarked on the quest, \"" + this.name + "\"!"));
				((RatSlayerQuestProgress) QuestManager.getQuestProgressForPlayer(player.getUniqueID(), this)).startedAndRejected = false;
				((RatSlayerQuestProgress) QuestManager.getQuestProgressForPlayer(player.getUniqueID(), this)).actuallyStarted = true;
			}
			break;
		case DIALOG_PLAYER_ACCEPTED_QUEST:
		case DIALOG_PLAYER_REJECTED_QUEST:
		case DIALOG_PLAYER_IN_PROGRESS:
		case DIALOG_PLAYER_FINISHED:
			Network.INSTANCE.sendTo(new CloseGuisMessage(), player);
			break;
		case DIALOG_PLAYER_CAME_BACK:
			if(responseIndex == 0) {
				Network.INSTANCE.sendTo(new ShowQuestDialogMessage(questGiverUUID, this.id, DIALOG_PLAYER_ACCEPTED_QUEST), player);
				QuestManager.startPlayerOnQuest(player.getUniqueID(), this);
				player.sendMessage(new TextComponentString("You've embarked on the quest, \"" + this.name + "\"!"));
				((RatSlayerQuestProgress) QuestManager.getQuestProgressForPlayer(player.getUniqueID(), this)).startedAndRejected = false;
				((RatSlayerQuestProgress) QuestManager.getQuestProgressForPlayer(player.getUniqueID(), this)).actuallyStarted = true;
			}else if(responseIndex == 1) {
				Network.INSTANCE.sendTo(new ShowQuestDialogMessage(questGiverUUID, this.id, DIALOG_PLAYER_REJECTED_QUEST), player);
			}
			break;
		}
	}

}
