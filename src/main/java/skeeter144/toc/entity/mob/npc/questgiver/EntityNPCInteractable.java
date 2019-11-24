package skeeter144.toc.entity.mob.npc.questgiver;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import skeeter144.toc.client.gui.NpcDialogResponse;
import skeeter144.toc.entity.mob.npc.DialogManager;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShowEntityDialogPKT;
import skeeter144.toc.quest.NpcDialog;

public abstract class EntityNPCInteractable extends EntityNpc{
	
	protected Map<String, NpcDialog> dialogs;
	public EntityNPCInteractable(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(type, worldIn);
		String s = this.getClass().getName();
		String[] split = s.split("\\.");
		dialogs = DialogManager.npcDialogs.get(split[split.length - 1] + ".class");
		
	}
	
	protected abstract boolean processInteract(PlayerEntity player, Hand hand);
	
	public void runServerFunction(UUID playerUUID, String dialogResponse) {
		for(Entry<String, NpcDialog> dialog : dialogs.entrySet()) {
			for(NpcDialogResponse response : dialog.getValue().playerResponses) {
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
	
	protected void sendDialog(String dialog, PlayerEntity player) {
		Network.INSTANCE.sendTo(new ShowEntityDialogPKT(this.getUniqueID(), dialog), (ServerPlayerEntity)player);
	}
	
	public NpcDialog getDialog(String dialogName) {
		return dialogs.get(dialogName);
	}
	
	
}
