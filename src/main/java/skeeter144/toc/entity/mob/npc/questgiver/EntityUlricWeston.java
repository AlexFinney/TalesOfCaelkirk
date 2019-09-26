package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;

public class EntityUlricWeston extends EntityNPCInteractable{
	
	public EntityUlricWeston(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/ulric_weston.png");
		
		//this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(TOCItems.axe_steel));
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
		
		if(qp.stage == 1) {
			sendDialog("intro", player);
		}else if(qp.stage == 0) {
			sendDialog("refuse", player);
		}else if(qp.stage == 2) {
//			if(TOCUtils.getItemCountInInventory(Item.getItemFromBlock(TOCBlocks.oak_log), player.inventory) >= 10) {
//				sendDialog("finished", player);
//			}else {
//				sendDialog("not_done_yet", player);
//			}
		}else if(qp.stage >= 3) {
				sendDialog("finished4", player);
		}
		
		return true;
	}
	
	public void ulricFinished(UUID playerUUID) {
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
	    QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
	    if(qp.stage == 2) {
//	    	player.addItemStackToInventory(new ItemStack(TOCItems.copper_coin, 20));
//			TOCUtils.removeItemsFromInventory(new ItemStack(TOCBlocks.oak_log).getItem(), 5, player.inventory);
//			
			qp.incStage();
	    }
		
	}
	
	public void beginLogChopping(UUID playerUUID) {
		
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
	    QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
	    qp.incStage();

		
		player.sendMessage(new TextComponentString(TextFormatting.GREEN  + "[" +  QuestManager.A_NEW_ADVENTURE + "] [New Task]" + TextFormatting.BLUE +" Chop down 10 Oak Logs."));
		
		//TODO
		//player.addItemStackToInventory(new ItemStack(TOCItems.axe_bronze));
//		//mark point on map
//		Network.INSTANCE.sendTo(new SendIconUpdateMessage("Speak with Ulric", 719, 42, 811, player.world.provider.getDimension(), Reference.MODID, "textures/icons/map/quest_objective.png", true), (EntityPlayerMP)player);
//		Network.INSTANCE.sendTo(new SendIconUpdateMessage("Chop down oak logs", 736, 42, 815, player.world.provider.getDimension(), Reference.MODID, "textures/icons/map/quest_objective.png"), (EntityPlayerMP)player);
		
	}
	
	
}
