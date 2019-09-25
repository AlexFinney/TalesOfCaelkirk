package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;

public class EntityMarlinMonroe extends EntityNPCInteractable{
	
	public EntityMarlinMonroe(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/marlin_monroe.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);

		if(qp.stage == 7) {
			sendDialog("intro", player);
		}
		return true;
	}
	
	public void marlinFinished(UUID playerUUID) {
		EntityPlayer player = world.getPlayerEntityByUUID(playerUUID);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.A_NEW_ADVENTURE);
		qp.incStage();
	}
	
	

	
}
