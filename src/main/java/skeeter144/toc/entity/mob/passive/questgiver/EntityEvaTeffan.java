package skeeter144.toc.entity.mob.passive.questgiver;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShowQuestDialogMessage;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;

public class EntityEvaTeffan extends EntityNPCInteractable{
	
	public EntityEvaTeffan(World worldIn) {
		super(worldIn, QuestManager.aNewAdventure);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/eva_teffan.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		if(qp == null)
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "eva_not_expected"), (EntityPlayerMP)player);
		
		if(qp.ulricFinished) {
			if(qp.evaTalkedTo) {
				if(!qp.sheepSheared)
					Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "eva_returned"), (EntityPlayerMP)player);
				else
					Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "eva_tutorial_7"), (EntityPlayerMP)player);
			}else {
				Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "eva_intro"), (EntityPlayerMP)player);
			}
		}else {
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "eva_not_expected"), (EntityPlayerMP)player);
		}
		
		return true;
	}
	
	public void beginShearSheep(UUID playerUUID) {
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(playerUUID, QuestManager.aNewAdventure);
		
		qp.evaTalkedTo = true;
		//TODO: `mark map for sheep
		
		EntityPlayer player = this.world.getPlayerEntityByUUID(playerUUID);
		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.aNewAdventure.name + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Shear a sheep and collect its wool."));
		player.inventory.addItemStackToInventory(new ItemStack(TOCItems.shears));
		
	}
	
	
}
