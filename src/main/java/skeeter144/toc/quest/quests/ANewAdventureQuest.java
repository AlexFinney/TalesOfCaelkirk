package skeeter144.toc.quest.quests;

import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skeeter144.toc.blocks.BlockHarvestableOre.BlockMinedEvent;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.handlers.PlayerInventoryHandler.ItemAddedToInventoryEvent;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.items.tools.TOCShears.SheepShearedEvent;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.recipe.RecipeManager.ItemSmithedEvent;
import skeeter144.toc.util.Util;

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
		
		if(e.getEntity() instanceof EntityGoblin && e.getSource().getTrueSource() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)e.getSource().getTrueSource();
			ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(e.getEntity().getUniqueID(), ANewAdventureQuestProgress.class);
			if(qp.goblinsKilled < 10) {
				Util.sendTaskUpdateMessage(player, this, String.format("Kill Goblins (%s/%s)", qp.goblinsKilled, 10));
				if(++qp.goblinsKilled == 10) {
					Util.sendNewTaskMessage(player, this, "Return to Kelvin Whitestone.");
				}
				qp.save();
			}
		}
	}
	
	@SubscribeEvent
	public void oakLogAdded(ItemAddedToInventoryEvent e) {
		if(e.stack.getItem() != new ItemStack(TOCBlocks.oak_log).getItem() || !(e.getEntity() instanceof PlayerEntity))
			return;
		
		PlayerEntity player = (PlayerEntity) e.getEntity();
		
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(e.getEntity().getUniqueID(), ANewAdventureQuestProgress.class);
		
		if(qp.ulricTalkedTo) {
			if(qp.logsChopped < 10) {
				Util.sendTaskUpdateMessage(player, this, String.format("Harvest Oak Logs (%s/%s)", ++qp.logsChopped, 10));
				if(qp.logsChopped == 10){
					Util.sendNewTaskMessage(player, this, "Return to Ulric.");
				}
				qp.save();
			}
		}
	}
	
	@SubscribeEvent
	public void itemCrafted(ItemCraftedEvent e) {
		if(e.getPlayer().world.isRemote)
			return;
	
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.getPlayer().getUniqueID(), getQuestProgressClass());
		if(qp.craftingStarted && (!qp.sticksCrafted || !qp.stringCrafted)){
			if(e.getCrafting().getItem() == Items.STRING) {
				Util.sendTaskUpdateMessage(e.getPlayer(), QuestManager.A_NEW_ADVENTURE, "Craft the components of a Fishing Rod: String (2/2), Oak Sticks (0/3)");
				qp.stringCrafted = true;
				qp.save();
			}else if(e.getCrafting().getItem().equals(TOCItems.stick_oak)){
				Util.sendTaskUpdateMessage(e.getPlayer(), QuestManager.A_NEW_ADVENTURE, "Craft the components of a Fishing Rod: String (2/2), Oak Sticks (3/3)");
				Util.sendNewTaskMessage(e.getPlayer(), QuestManager.A_NEW_ADVENTURE, "Return to Eva Teffan with your crafting components.");
				qp.sticksCrafted = true;
				qp.save();
			}
		}else if(qp.craftingStarted && !qp.fishingRodCrafted ) {
			if(e.getCrafting().getItem().equals(Items.FISHING_ROD)) {
				Util.sendTaskUpdateMessage(e.getPlayer(), QuestManager.A_NEW_ADVENTURE, "Craft a Fishing Rod. (Complete)");
				Util.sendNewTaskMessage(e.getPlayer(), QuestManager.A_NEW_ADVENTURE, "Return to Eva Teffan with your Fishing Rod.");
				qp.fishingRodCrafted = true;
				qp.save();
			}
		}
	}

	@SubscribeEvent
	public void sheepSheared(SheepShearedEvent e) {
		if(e.getEntity().world.isRemote)
			return;
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(e.shearer.getUniqueID(), ANewAdventureQuestProgress.class);
		if(qp.sheepsSheared++ == 0) {
			Util.sendTaskUpdateMessage(e.shearer, this, "Shear a sheep and collect its wool. (1/1)");
			Util.sendNewTaskMessage(e.shearer, this, "Return to Eva Teffan.");
			qp.save();
		}
	}
	
	@SubscribeEvent
	public void blockMined(BlockMinedEvent e) {
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.player.getUniqueID(), getQuestProgressClass());
		
		if(qp.kelvinTalkedTo && !qp.kelvinFinished )  {
			if(e.getState().getBlock() == TOCBlocks.copper_ore && qp.copperMined < 5) {
				qp.copperMined++;
				qp.save();
				if(qp.copperMined < 5)
					Util.sendTaskUpdateMessage(e.player, this, String.format("Mine Copper Ore: (%s/5)", qp.copperMined));
				else {
					Util.sendTaskUpdateMessage(e.player, this, "Mine Copper Ore: Complete");
					if(qp.copperMined >= 5 && qp.tinMined >= 5) {
						Util.sendNewTaskMessage(e.player, this, "Mining task complete. Return to Kelvin Whitestone");
					}
				}
			}
			else if(e.getState().getBlock() == TOCBlocks.tin_ore && qp.tinMined < 5) {
				qp.tinMined++;
				if(qp.copperMined <= 5)
					Util.sendTaskUpdateMessage(e.player, this, String.format("Mine Tin Ore: (%s/5)", qp.tinMined));
				else {
					Util.sendTaskUpdateMessage(e.player, this, "Mine Tin Ore: Complete");
					if(qp.copperMined >= 5 && qp.tinMined >= 5) {
						Util.sendNewTaskMessage(e.player, this, "Mining task complete. Return to Kelvin Whitestone");
					}
				}
				qp.save();
			}
			
			
		}
		
	}
	
	@SubscribeEvent
	public void bronzeSmithed(ItemSmithedEvent e) {
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.player.getUniqueID(), getQuestProgressClass());
		if(!qp.smeltingStarted) return;
		
		if(qp.bronzeSmelted < 5 && e.smithed.getItem() == TOCItems.ingot_bronze) {
			if(++qp.bronzeSmelted >= 5){
				Util.sendTaskUpdateMessage(e.player, this, "Smelt 5 bronze ingots. (Complete)");
				Util.sendNewTaskMessage(e.player, this, "Return to Kelvin Whitestone.");
				qp.smeltingFinished = true;
			}
			qp.save();
		}
		
		if(!qp.smithingFinished && e.smithed.getItem() == TOCItems.bronze_short_sword) {
			qp.smithingFinished = true;
			Util.sendTaskUpdateMessage(e.player, this, "Smith a bronze short sword. (Complete)");
			Util.sendNewTaskMessage(e.player, this, "Return to Kelvin Whitestone.");
			qp.save();
		}
		
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
		return new ANewAdventureQuestProgress(null, id, 0);
	}
	
	
	@Override
	protected void questFinished(ServerPlayerEntity player) {
		System.out.println("Yay you finished da quest");		
	}
	
	public static class ANewAdventureQuestProgress extends QuestProgress{
		public int bronzeSmelted;
		private static final long serialVersionUID = 8751583222379746250L;
		public boolean robertTalkedTo = false;
		public boolean ulricTalkedTo = false;
		public int logsChopped = 0;
		public boolean ulricFinished = false;
		public boolean evaTalkedTo = false;
		public int sheepsSheared = 0;
		public boolean craftingStarted;
		public boolean stringCrafted = false;
		public boolean sticksCrafted = false;
		public boolean fishingRodCrafted = false;
		public boolean evaFinished = false;
		public boolean craftingFinished = false;
		public boolean marlinTalkedTo = false;
		public int fishCaught = 0;
		public boolean marlinFinished = false;
		public boolean kelvinTalkedTo = false;
		public int copperMined = 0;
		public int tinMined = 0;
		public boolean smeltingStarted = false;
		public boolean smeltingFinished = false;
		public boolean smithingStarted = false;
		public boolean smithingFinished = false;
		public boolean combatStarted = false;
		public int goblinsKilled = 0;
		public boolean kelvinFinished = false;
		public boolean seloviusTalkedTo = false;
		public int chickensKilled = 0;
		public boolean seloviusFinished = false;
		public ANewAdventureQuestProgress(UUID playerId, int questId, int stage) {
			super(playerId, questId, stage);
		}
	}

	@Override
	public Class<ANewAdventureQuestProgress> getQuestProgressClass() {
		return ANewAdventureQuestProgress.class;
	}

}
