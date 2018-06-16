package skeeter144.toc.entity.mob.passive.questgiver;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.QuestManager;

public class EntityRobertCromwell extends EntityNPCInteractable{
	
	public EntityRobertCromwell(World worldIn) {
		super(worldIn, QuestManager.aNewAdventure);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/bob_rat_man.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			TOCMain.proxy.showDialogToPlayer(this, (NpcDialog) QuestManager.aNewAdventure.questDialogs.get("quest_offer"));
			return true;
		}
		
		
		return true;
	}

	@Override
	public void handleDialogResponse(UUID playerUUID, String dialogResponse) {
	}
}
