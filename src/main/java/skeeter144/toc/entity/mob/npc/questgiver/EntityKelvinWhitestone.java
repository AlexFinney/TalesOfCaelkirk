package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ItemPrice;
import skeeter144.toc.handlers.PlayerInventoryHandler.CoinsAddedToInventoryEvent;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;
import skeeter144.toc.util.TOCUtils;
import skeeter144.toc.util.Util;

public class EntityKelvinWhitestone extends EntityNPCInteractable{
	
	public EntityKelvinWhitestone(World worldIn) {
		this(TOCEntityType.KELVIN_WHITESTONE, worldIn);
	}
	
	public EntityKelvinWhitestone(EntityType<? extends EntityNpc> type, World worldIn) {
		super(type, worldIn);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/kelvin_whitestone.png");
		
		this.setHeldItem(Hand.MAIN_HAND, new ItemStack(TOCItems.steel_pickaxe));
	}
	
	@Override
	protected boolean processInteract(PlayerEntity player, Hand hand) {
		if(player.world.isRemote) {
			return true;
		}
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		
		if(qp.marlinFinished) {
			if(!qp.kelvinTalkedTo) sendDialog("intro", player);
			else {
				if(qp.tinMined < 5 || qp.copperMined < 5) sendDialog("more_ore", player);
				else {
					if(!qp.smeltingStarted) sendDialog("tut_2", player);
					else {
						if(!qp.smeltingFinished) sendDialog("tut_2", player);
						else {
							if(!qp.smithingStarted) sendDialog("tut_3", player);
							else {
								if(!qp.combatStarted) sendDialog("tut_4", player);
								else {
									if(qp.goblinsKilled < 10) sendDialog("goblin_not_done", player);
									else sendDialog("finished", player);
								}
							}
						}
					}
				}
			}
		}
		
		if(qp.stage == 8) {
			sendDialog("intro", player);
		}else if (qp.stage == 9) {
			if(TOCUtils.getItemCountInInventory(TOCItems.ore_copper, player.inventory) >= 5 &&
			   TOCUtils.getItemCountInInventory(TOCItems.ore_tin, player.inventory) >= 5) {
				sendDialog("tut_2", player);
				qp.incStage();
			}else {
				sendDialog("more_ore", player);
			}
		}else if(qp.stage == 10) {
			if(TOCUtils.getItemCountInInventory(TOCItems.ingot_bronze, player.inventory) >= 5) {
				sendDialog("tut_3", player);
			}
		}else if(qp.stage == 11) {
			if(TOCUtils.getItemCountInInventory(TOCItems.bronze_short_sword, player.inventory) >= 1) {
				sendDialog("tut_4", player);
			}
		}else if(qp.stage == 12) {
			if(TOCUtils.getItemCountInInventory(TOCItems.goblin_ear, player.inventory) >= 10) {
				sendDialog("finished", player);
			} else {
				sendDialog("goblin_not_done", player);
			}
		} else if (qp.stage >= 13) {
			sendDialog("returned", player);
		}
		return true;
	}
	
	public void kelvinStart(UUID playerUUID) {
		PlayerEntity player = world.getPlayerByUuid(playerUUID);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		qp.kelvinTalkedTo = true;
		
		player.addItemStackToInventory(new ItemStack(TOCItems.bronze_pickaxe));
		Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Mine 5 copper and 5 tin ore, then return to Kelvin.");
		qp.save();
	}
	
	public void smeltingStart(UUID uuid) {
		PlayerEntity player = world.getPlayerByUuid(uuid);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Smelt the Copper and Tin Ore into Bronze Ingots at the furnace.");
		qp.smeltingStarted = true;
		qp.save();
	}
	
	public void smithingStart(UUID uuid) {
		PlayerEntity player = world.getPlayerByUuid(uuid);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		player.addItemStackToInventory(new ItemStack(TOCItems.blacksmith_hammer));
		Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Smith a bronze longsword on an anvil, and return to Kelvin.");
		qp.smithingStarted = true;
		qp.save();
	}
	
	public void killGoblins(UUID uuid) {
		PlayerEntity player = world.getPlayerByUuid(uuid);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Find the goblin camp, killing 10 of them and collecting an ear from each for the bounty.");
		player.addItemStackToInventory(new ItemStack(TOCItems.bronze_chestplate));
		qp.combatStarted = true;
		qp.save();
	}
	
	public void kelvinFinished(UUID uuid) {
		PlayerEntity player = world.getPlayerByUuid(uuid);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		if (!qp.kelvinFinished) {
			qp.kelvinFinished = true;
			Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Head out of the mine and to the magic temple.");
			int ears = TOCUtils.getItemCountInInventory(TOCItems.goblin_ear, player.inventory);
			int copper = ears * 2;
			MinecraftForge.EVENT_BUS.post(new CoinsAddedToInventoryEvent(player, new ItemPrice(0, 0, copper)));
			TOCUtils.removeItemsFromInventory(TOCItems.goblin_ear, ears, player.inventory);
			qp.save();
		}
	}
	
	
	
	
}
