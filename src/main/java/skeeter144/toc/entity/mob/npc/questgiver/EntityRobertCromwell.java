package skeeter144.toc.entity.mob.npc.questgiver;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.TOCUtils;

public class EntityRobertCromwell extends EntityNPCInteractable{
	
	public EntityRobertCromwell(World worldIn) {
		super(worldIn);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/bob_rat_man.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote)
			return true;
		
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		if(!qp.started)
			sendDialog("introduction", player);
		else
			sendDialog("player_returned", player);
		
		return true;
	}

	
	public void startPlayerOnQuest(UUID playerUUID) {
		EntityPlayer pl = this.world.getPlayerEntityByUUID(playerUUID);
		QuestProgress qp = QuestManager.getQuestProgressForPlayer(pl.getUniqueID(), QuestManager.aNewAdventure);
		qp.started = true;
		qp.stage = 1;
		pl.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.aNewAdventure.name + "] " + TextFormatting.GREEN + "[New Task] " + TextFormatting.WHITE + "Go talk to Ulric Weston about woodcutting"));
	
		//TOCUtils.addIconMarkerToMap("Ulric Weston", new ResourceLocation(Reference.MODID, "toc:textures/icons/map/quest_objective"), new BlockPos(720, 42, 815), this.world.provider.getDimension());
	}
	
}
