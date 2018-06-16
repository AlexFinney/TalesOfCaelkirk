package skeeter144.toc.quest.quests;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.network.CloseGuisMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShowQuestDialogMessage;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestProgress;

public class ANewAdventureQuest extends Quest{

	public ANewAdventureQuest(String name, int id) {
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
	}

	@Override
	public QuestProgress getNewQuestProgressInstance() {
		return new ANewAdventureQuestProgress();
	}
	
	@Override
	public Class<? extends QuestProgress> getQuestProgressClass() {
		return ANewAdventureQuestProgress.class;
	}
	
	@Override
	protected void questFinished(EntityPlayerMP player) {
		System.out.println("Yay you finished da quest");		
	}
	
	public static class ANewAdventureQuestProgress extends QuestProgress{
		public boolean questStarted = false;
		public boolean ulricTalkedTo = false;
		public int woodChopped = 0;
	}
}
