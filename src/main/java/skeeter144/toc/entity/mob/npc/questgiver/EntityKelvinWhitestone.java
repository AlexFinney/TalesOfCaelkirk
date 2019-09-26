package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ItemPrice;
import skeeter144.toc.handlers.PlayerInventoryHandler.CoinsAddedToInventoryEvent;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.util.TOCUtils;

public class EntityKelvinWhitestone extends EntityNPCInteractable{
	
	public EntityKelvinWhitestone(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/kelvin_whitestone.png");
		
		//this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(TOCItems.steel_pickaxe));
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
//		if(player.world.isRemote) {
//			return true;
//		}
//		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
//		if(qp.stage == 8) {
//			sendDialog("intro", player);
//		}else if (qp.stage == 9) {
//			if(TOCUtils.getItemCountInInventory(TOCItems.ore_copper, player.inventory) >= 5 &&
//			   TOCUtils.getItemCountInInventory(TOCItems.ore_tin, player.inventory) >= 5) {
//				sendDialog("tut_2", player);
//				qp.incStage();
//			}else {
//				sendDialog("more_ore", player);
//			}
//		}else if(qp.stage == 10) {
//			if(TOCUtils.getItemCountInInventory(TOCItems.ingot_bronze, player.inventory) >= 5) {
//				sendDialog("tut_3", player);
//			}
//		}else if(qp.stage == 11) {
//			if(TOCUtils.getItemCountInInventory(TOCItems.bronze_short_sword, player.inventory) >= 1) {
//				sendDialog("tut_4", player);
//			}
//		}else if(qp.stage == 12) {
//			if(TOCUtils.getItemCountInInventory(TOCItems.goblin_ear, player.inventory) >= 10) {
//				sendDialog("finished", player);
//			} else {
//				sendDialog("goblin_not_done", player);
//			}
//		} else if (qp.stage >= 13) {
//			sendDialog("returned", player);
//		}
		return true;
	}
	
	public void kelvinStart(UUID playerUUID) {
//		EntityPlayer player = world.getPlayerEntityByUUID(playerUUID);
//		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
//		qp.incStage();
//		player.addItemStackToInventory(new ItemStack(TOCItems.bronze_pickaxe));
//		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Mine 5 copper and 5 tin ore, then return to Kelvin."));
	}
	
	public void smithingStart(UUID uuid) {
		EntityPlayer player = world.getPlayerEntityByUUID(uuid);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
		qp.incStage();
		//player.addItemStackToInventory(new ItemStack(TOCItems.blacksmith_hammer));
		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Smelt a bronze longsword, and return to Kelvin."));
	}
	
	public void killGoblins(UUID uuid) {
		EntityPlayer player = world.getPlayerEntityByUUID(uuid);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
		qp.incStage();
		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Find the goblin camp, killing 10 of them and collecting an ear from each for the bounty."));
	//	player.addItemStackToInventory(new ItemStack(TOCItems.bronze_chestplate));
	}
	
	public void kelvinFinished(UUID uuid) {
		EntityPlayer player = world.getPlayerEntityByUUID(uuid);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
		qp.incStage();
		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Head out of the mine and to the magic temple."));
	//	int ears = TOCUtils.getItemCountInInventory(TOCItems.goblin_ear, player.inventory);
	//	int copper = ears * 2;
		//MinecraftForge.EVENT_BUS.post(new CoinsAddedToInventoryEvent(player, new ItemPrice(0, 0, copper)));
		//TOCUtils.removeItemsFromInventory(TOCItems.goblin_ear, 10, player.inventory);
	}
	
	
	
	
}
