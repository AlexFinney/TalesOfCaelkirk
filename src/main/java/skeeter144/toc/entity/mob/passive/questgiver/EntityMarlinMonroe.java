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

public class EntityMarlinMonroe extends EntityNPCInteractable{
	
	public EntityMarlinMonroe(World worldIn) {
		super(worldIn, QuestManager.aNewAdventure);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/marlin_monroe.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		
		
		if(qp.evaFinished) {
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "marlin_intro"), (EntityPlayerMP)player);
		}else if(qp.marlinFinished) {
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "marlin_finished"), (EntityPlayerMP)player);
		}
		
		return true;
	}
	
	public void marlinFinished(UUID playerUUID) {
		EntityPlayer player = world.getPlayerEntityByUUID(playerUUID);
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		
		qp.marlinFinished = true;
		//mark-point on map
	}
	
	

	
}
