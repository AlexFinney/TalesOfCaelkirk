package skeeter144.toc.quest.quests;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.handlers.PlayerInventoryHandler.ItemAddedToInventoryEvent;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;
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
	
	@SubscribeEvent
	public void oakLogAdded(ItemAddedToInventoryEvent e) {
		if(e.getEntity().world.isRemote)
			return;
		
		if(e.stack.getItem() != new ItemStack(TOCBlocks.oak_log).getItem())
			return;
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.getEntity().getUniqueID(), this);
		if(qp.ulricTalkedTo && qp.logsChopped < 10) {
			if(++qp.logsChopped == 10){
				((EntityPlayer)e.getEntity()).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [Task Completed] " + ChatFormatting.WHITE + ChatFormatting.STRIKETHROUGH +"Harvest 10 Oak Logs." ));
				((EntityPlayer)e.getEntity()).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [New Task] " + ChatFormatting.WHITE + "Return to Ulric." ));
			}
		}
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
		public int logsChopped = 0;
	}
}
