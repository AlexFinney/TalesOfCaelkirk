package skeeter144.toc.quest.quests;

import java.io.Serializable;
import java.util.UUID;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import skeeter144.toc.blocks.BlockHarvestableOre.BlockMinedEvent;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.handlers.PlayerInventoryHandler.ItemAddedToInventoryEvent;
import skeeter144.toc.items.tools.TOCShears.SheepShearedEvent;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.recipe.RecipeManager.ItemSmithedEvent;

public class ANewAdventureQuest extends Quest{
	
	public ANewAdventureQuest(int id) {
		super(id, "A New Adventure", 0);
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
		if(e.stack.getItem() != new ItemStack(TOCBlocks.oak_log).getItem())
			return;
		((EntityPlayerMP)e.getEntity()).sendMessage(new TextComponentString("Test"), ChatType.GAME_INFO);
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.getEntity().getUniqueID(), name);
		if(qp.ulricTalkedTo && qp.logsChopped < 10) {
			if(++qp.logsChopped == 10){
				((EntityPlayerMP)e.getEntity()).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [Task Completed] " + ChatFormatting.WHITE + ChatFormatting.STRIKETHROUGH +"Harvest 10 Oak Logs." ));
				((EntityPlayerMP)e.getEntity()).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [New Task] " + ChatFormatting.WHITE + "Return to Ulric." ));
			}
		}
	}
	
	@SubscribeEvent
	public void itemCrafted(ItemCraftedEvent e) {
		if(e.getPlayer().world.isRemote)
			return;
		
	/*	ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.player.getUniqueID(), this);
		if(qp.craftingStarted && (!qp.sticksCrafted || !qp.stringCrafted)){
			if(e.crafting.getItem() == Items.STRING) {
				qp.stringCrafted = true;
			}else if(e.crafting.getItem().equals(TOCItems.stick_oak)){
				qp.sticksCrafted = true;
			}
		}else if(qp.craftingStarted && !qp.rodCrafted ) {
			if(e.crafting.getItem().equals(Items.FISHING_ROD)) {
				qp.rodCrafted = true;
			}
		}
		*/
	}

	@SubscribeEvent
	public void sheepSheared(SheepShearedEvent e) {
		/*if(e.getEntity().world.isRemote)
			return;
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.shearer.getUniqueID(), this);
		if(!qp.sheepSheared) {
			qp.sheepSheared = true;
			((EntityPlayer)e.shearer).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [Task Completed] " + ChatFormatting.WHITE + ChatFormatting.STRIKETHROUGH +"Sheer a sheep." ));
			((EntityPlayer)e.shearer).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [New Task] " + ChatFormatting.WHITE + "Return to Eva Teffan."));
		}
		*/
	}
	
	@SubscribeEvent
	public void blockMined(BlockMinedEvent e) {
		/*ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.player.getUniqueID(), this);
		
		if(qp.kelvinStarted && !qp.kelvinFinished )  {
			if(e.getState().getBlock() == TOCBlocks.copper_ore) {
				qp.copperMined ++;
			}
			else if(e.getState().getBlock() == TOCBlocks.tin_ore) {
				qp.tinMined++;
			}
		}
		*/
	}
	
	@SubscribeEvent
	public void bronzeSmithed(ItemSmithedEvent e) {
	/*	ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.player.getUniqueID(), this);
	
		if(qp.bronzeSmelted < 5 && e.smithed.getItem() == TOCItems.ingot_bronze) {
			if(++qp.bronzeSmelted >= 5){
				((EntityPlayer)e.player).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [Task Completed] " + ChatFormatting.WHITE + "Smelt 5 bronze ingots." ));
				((EntityPlayer)e.player).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [New Task] " + ChatFormatting.WHITE + "Return to Kelvin Whitestone." ));
			}
			
		}
		
		if(!qp.swordSmithed && e.smithed.getItem() == TOCItems.bronze_short_sword) {
			qp.swordSmithed = true;
			((EntityPlayer)e.player).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [Task Completed] " + ChatFormatting.WHITE + "Smith a bronze short sword." ));
			((EntityPlayer)e.player).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [New Task] " + ChatFormatting.WHITE + "Return to Kelvin Whitestone." ));
		}
		*/
	}
	
	
	public String getTaskForStage(int stage) {
		String task = "";
		
		if(stage == 0)
			task = "Go speak to Ulric Weston";
		else if(stage == 1)
			task = "";
		
		
		return task;
	}
	
	@Override
	public QuestProgress getNewQuestProgressInstance() {
		return new ANewAdventureQuestProgress(null, id, 0, 0, 0, 0);
	}
	
	
	@Override
	protected void questFinished(EntityPlayerMP player) {
		System.out.println("Yay you finished da quest");		
	}
	
	public static class ANewAdventureQuestProgress extends QuestProgress implements Serializable{
		boolean ulricTalkedTo = false;
		int logsChopped = 0;
		int sheepsSheared = 0;
		boolean stringCrafted = false;
		boolean sticksCrafted = false;
		boolean fishingRodCrafted = false;
		int fishCaught = 0;
		int copperMined = 0;
		int tinMined = 0;
		int goblinEarsCollected = 0;
		int chickensKilled = 0;
		public ANewAdventureQuestProgress(UUID playerId, int questId, int stage, int qp1, int qp2, int qp3) {
			super(playerId, questId, stage, qp1, qp2, qp3);
		}
	}

}
