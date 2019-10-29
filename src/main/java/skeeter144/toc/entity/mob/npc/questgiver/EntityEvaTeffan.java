package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;
import skeeter144.toc.util.TOCUtils;

public class EntityEvaTeffan extends EntityNPCInteractable{
	
	public EntityEvaTeffan(World worldIn) {
		this(TOCEntityType.EVA_TEFFAN, worldIn);
	}
	
	public EntityEvaTeffan(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/eva_teffan.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		
		if(qp.stage == 3) {
			sendDialog("intro", player);
		}else if (qp.stage < 3) {
			sendDialog("not_expected", player);
		}else if(qp.stage == 4) {
			if(TOCUtils.getItemCountInInventory(Blocks.WHITE_WOOL.asItem(), player.inventory) >= 1) {
				sendDialog("tutorial_7", player);
			}else {
				sendDialog("returned", player);
			}
		}else if (qp.stage == 5) {
			if(TOCUtils.getItemCountInInventory(Blocks.WHITE_WOOL.asItem(), player.inventory) >= 1)
			{
				sendDialog("tutorial_7", player);
				qp.incStage();
			}else {
				sendDialog("returned", player);
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
		EntityPlayerMP player = (EntityPlayerMP)world.getPlayerEntityByUUID(playerUUID);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);

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
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);

		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Craft at least 3 sticks and two strings, then return to Eva."));
		qp.incStage();
	}
	
	
}
