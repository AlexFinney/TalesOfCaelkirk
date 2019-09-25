package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;

public class EntitySeloviusKamazz extends EntityNPCInteractable{
	
	public EntitySeloviusKamazz(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/selovius_kamazz.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote)
			return true;
		
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
		if(qp.stage == 13)
			sendDialog("intro", player);
		else if(qp.stage >= 14)
			sendDialog("finished", player);
		
		return true;
	}

	
	public void chickenKilling(UUID playerUUID) {
		EntityPlayer pl = this.world.getPlayerEntityByUUID(playerUUID);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(pl.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
		qp.incStage();
		pl.addItemStackToInventory(new ItemStack(TOCItems.wand_basic));
	}
	
	public void tutorialFinished(UUID playerUUID) {
		EntityPlayer pl = this.world.getPlayerEntityByUUID(playerUUID);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(pl.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
		qp.incStage();
		// QuestManager.A_NEW_ADVENTURE.onQuestFinished((EntityPlayerMP) pl);
	}
	
}
