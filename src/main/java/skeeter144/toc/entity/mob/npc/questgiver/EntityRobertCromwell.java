package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;

public class EntityRobertCromwell extends EntityNPCInteractable{
	
	public EntityRobertCromwell(World worldIn) {
		this(TOCEntityType.ROBERT_CROMWELL, worldIn);
	}
	
	public EntityRobertCromwell(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/bob_rat_man.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote)
			return true;
		
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), "A New Adventure");
		
		if(qp.stage == 0)
			sendDialog("introduction", player);
		else
			sendDialog("player_returned", player);
		
		return true;
	}

	
	public void startPlayerOnQuest(UUID playerUUID) {
		EntityPlayer pl = this.world.getPlayerEntityByUUID(playerUUID);
		pl.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.A_NEW_ADVENTURE + "] " + TextFormatting.GREEN + "[New Task] " + TextFormatting.WHITE + "Go talk to Ulric Weston about woodcutting"));
		
		QuestProgress qp = QuestManager.startPlayerOnQuest(playerUUID, "A New Adventure");
		qp.incStage();
	
		//TOCUtils.addIconMarkerToMap("Ulric Weston", new ResourceLocation(Reference.MODID, "toc:textures/icons/map/quest_objective"), new BlockPos(720, 42, 815), this.world.provider.getDimension());
	}
	
}
