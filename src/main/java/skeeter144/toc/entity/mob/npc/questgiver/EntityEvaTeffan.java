package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.util.TOCUtils;

public class EntityEvaTeffan extends EntityNPCInteractable{
	
	public EntityEvaTeffan(World worldIn) {
		super(worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/eva_teffan.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getPersistentID(), QuestManager.A_NEW_ADVENTURE);
		
		if(qp.stage == 3) {
			sendDialog("intro", player);
		}else if (qp.stage < 3) {
			sendDialog("not_expected", player);
		}else if(qp.stage == 4) {
			if(TOCUtils.getItemCountInInventory(Item.getItemFromBlock(Blocks.WOOL), player.inventory) >= 1) {
				sendDialog("tutorial_7", player);
			}else {
				sendDialog("returned", player);
			}
		}else if (qp.stage == 5) {
			if(TOCUtils.getItemCountInInventory(Items.STRING, player.inventory) >= 2 &&
			   TOCUtils.getItemCountInInventory(TOCItems.stick_oak, player.inventory) >= 3) {
				sendDialog("tutorial_9", player);
				qp.incStage();
			}else {
				sendDialog("tutorial_7", player);
			}
		}
		else if (qp.stage == 6) {
			if(TOCUtils.getItemCountInInventory(Items.FISHING_ROD, player.inventory) >= 1) {
				sendDialog("finished", player);
				qp.incStage();
			}else {
				sendDialog("tutorial_9", player);
			}
		}else if (qp.stage >= 7) {
			sendDialog("finished", player);
		}
		return true;
	}
	
	public void beginShearSheep(UUID playerUUID) {
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getPersistentID(), QuestManager.A_NEW_ADVENTURE);

//		//TODO: `mark map for sheep
		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Shear a sheep and collect its wool."));
		player.inventory.addItemStackToInventory(new ItemStack(TOCItems.shears));
		qp.incStage();
	}
	
	public void evaFinished(UUID playerUUID) {
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Go across the bridge and talk to Marlin Monroe."));
	}
	
	public void beginCrafting(UUID playerUUID) {
		EntityPlayer player = world.getPlayerEntityByUUID(playerUUID);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getPersistentID(), QuestManager.A_NEW_ADVENTURE);

		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Craft at least 3 sticks and two strings, then return to Eva."));
		qp.incStage();
	}
	
	
}
