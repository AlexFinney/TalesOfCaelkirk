package skeeter144.toc.entity.mob.npc.questgiver;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import skeeter144.toc.client.gui.NpcDialogResponse;
import skeeter144.toc.entity.mob.npc.DialogManager;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShowEntityDialogMessage;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.QuestManager;

public abstract class EntityNPCInteractable extends EntityNpc{
	protected Map<String, NpcDialog> dialogs;
	public EntityNPCInteractable(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		String s = this.getClass().getName();
		String[] split = s.split("\\.");
		dialogs = DialogManager.npcDialogs.get(split[split.length - 1] + ".class");
		
	}
	
	protected abstract boolean processInteract(EntityPlayer player, EnumHand hand);
	
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
	
	protected void sendDialog(String dialog, EntityPlayer player) {
		Network.INSTANCE.sendTo(new ShowEntityDialogMessage(this.getUniqueID(), dialog), (EntityPlayerMP)player);
	}
	
	public NpcDialog getDialog(String dialogName) {
		return dialogs.get(dialogName);
	}
	
	
}
