package skeeter144.toc.entity.mob.passive.questgiver;

import java.lang.reflect.Method;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import skeeter144.toc.client.gui.NpcDialogResponse;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;

public abstract class EntityNPCInteractable extends EntityQuestGiver{
	public EntityNPCInteractable(World worldIn, Quest ...quests) {
		super(worldIn, quests);
	}
	
	protected abstract boolean processInteract(EntityPlayer player, EnumHand hand);
	
	public void handleDialogResponse(UUID playerUUID, String dialogResponse) {
		for(NpcDialog dialog : QuestManager.aNewAdventure.questDialogs.values()) {
			for(NpcDialogResponse response : dialog.playerResponses) {
				if(response.serverEventFunc.equals(dialogResponse)) {
					try {
						Method m = this.getClass().getMethod(dialogResponse, UUID.class);
						m.invoke(this, playerUUID);
						return;
					}catch(Exception e) {
						System.out.println("ERROR! Server Function Not found: " + dialogResponse);
						e.printStackTrace();
					}
				}
			}
		}
	}
}
