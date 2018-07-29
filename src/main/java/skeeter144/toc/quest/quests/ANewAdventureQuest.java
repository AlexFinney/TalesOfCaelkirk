package skeeter144.toc.quest.quests;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import skeeter144.toc.blocks.BlockHarvestableOre.BlockMinedEvent;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.handlers.PlayerInventoryHandler.ItemAddedToInventoryEvent;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.items.tools.TOCShears.SheepShearedEvent;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.recipe.RecipeManager.ItemSmithedEvent;

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
	
	@SubscribeEvent
	public void itemCrafted(ItemCraftedEvent e) {
		if(e.player.world.isRemote)
			return;
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.player.getUniqueID(), this);
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
	}

	@SubscribeEvent
	public void sheepSheared(SheepShearedEvent e) {
		if(e.getEntity().world.isRemote)
			return;
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.shearer.getUniqueID(), this);
		if(!qp.sheepSheared) {
			qp.sheepSheared = true;
			((EntityPlayer)e.shearer).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [Task Completed] " + ChatFormatting.WHITE + ChatFormatting.STRIKETHROUGH +"Sheer a sheep." ));
			((EntityPlayer)e.shearer).sendMessage(new TextComponentString(ChatFormatting.GREEN + "[" + this.name +"] [New Task] " + ChatFormatting.WHITE + "Return to Eva Teffan."));
		}
	}
	
	@SubscribeEvent
	public void blockMined(BlockMinedEvent e) {
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.player.getUniqueID(), this);
		
		if(qp.kelvinStarted && !qp.kelvinFinished )  {
			if(e.getState().getBlock() == TOCBlocks.copper_ore) {
				qp.copperMined ++;
			}
			else if(e.getState().getBlock() == TOCBlocks.tin_ore) {
				qp.tinMined++;
			}
		}
	}
	
	@SubscribeEvent
	public void bronzeSmithed(ItemSmithedEvent e) {
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(e.player.getUniqueID(), this);
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
		public int bronzeSmelted;
		public boolean questStarted = false;
		public boolean ulricTalkedTo = false;
		public int logsChopped = 0;
		public boolean ulricFinished = false;
		public boolean evaTalkedTo = false;
		public boolean sheepSheared = false;
		public boolean craftingStarted = false;
		public boolean stringCrafted = false;
		public boolean sticksCrafted = false;
		public boolean rodCrafted = false;
		public boolean evaFinished = false;
		public boolean marlinFinished = false;
		public boolean kelvinStarted = false;
		public int copperMined = 0;
		public int tinMined = 0;
		public int bronzeSmithed = 0;
		public boolean swordSmithed = false;
		public boolean kelvinFinished = false;
		
	}
}
