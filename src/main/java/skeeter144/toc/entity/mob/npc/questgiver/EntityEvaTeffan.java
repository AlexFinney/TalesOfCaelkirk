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
import skeeter144.toc.util.Util;

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
		
		if(qp.ulricFinished) {
			if(!qp.evaTalkedTo) sendDialog("intro", player);
			else {
				if(qp.sheepsSheared == 0) sendDialog("returned", player);
				else {
					if(!qp.stringCrafted || !qp.sticksCrafted) sendDialog("tutorial_2", player);	
					else {
						if(!qp.fishingRodCrafted) sendDialog("tutorial_3", player);
						else {
							sendDialog("finished", player);
						}
					}
				}
			}
		}else{
			sendDialog("not_expected", player);
		}
	
		return true;
	}
	
	public void beginShearSheep(UUID playerUUID) {
		EntityPlayerMP player = (EntityPlayerMP)world.getPlayerEntityByUUID(playerUUID);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		qp.evaTalkedTo = true;
		
		Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Shear a sheep and collect its wool.");
//		//TODO: `mark map for sheep
		player.inventory.addItemStackToInventory(new ItemStack(TOCItems.shears));
		
		qp.save();
	}
	
	public void evaFinished(UUID playerUUID) {
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		if(!qp.evaFinished) {
			Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Go across the bridge and talk to Marlin Monroe to learn about Fishing.");
			qp.evaFinished = true;
			qp.save();
		}
	}
	
	public void beginCrafting(UUID playerUUID) {
		EntityPlayer player = world.getPlayerEntityByUUID(playerUUID);
		ANewAdventureQuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), ANewAdventureQuestProgress.class);
		Util.sendNewTaskMessage(player, QuestManager.A_NEW_ADVENTURE, "Craft the components of a Fishing Rod: String (0/2), Oak Sticks (0/3)");
		qp.craftingStarted = true;
		qp.save();
	}
	
	
}
