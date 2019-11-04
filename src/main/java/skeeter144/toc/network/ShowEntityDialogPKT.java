package skeeter144.toc.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.client.gui.DialogGui;
import skeeter144.toc.entity.mob.npc.questgiver.EntityNPCInteractable;

public class ShowEntityDialogPKT{
	
	int dialogNameLength;
	String dialogName;
	UUID uuid;
	public ShowEntityDialogPKT() {}
	public ShowEntityDialogPKT(UUID entityId, String dialogName) {
		this.uuid = entityId;
		this.dialogName = dialogName;
		this.dialogNameLength = dialogName.length();
	}
	
	public static void encode(ShowEntityDialogPKT pkt, PacketBuffer buf) {
		buf.writeInt(pkt.dialogName.length());
		buf.writeString(pkt.dialogName);
		buf.writeLong(pkt.uuid.getMostSignificantBits());
		buf.writeLong(pkt.uuid.getLeastSignificantBits());
	}
	
	public static ShowEntityDialogPKT decode(PacketBuffer buf) {
		ShowEntityDialogPKT pkt = new ShowEntityDialogPKT();
		pkt.dialogNameLength = buf.readInt();
		pkt.dialogName = buf.readString(pkt.dialogNameLength);
		pkt.uuid = new UUID(buf.readLong(), buf.readLong());
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final ShowEntityDialogPKT message, Supplier<NetworkEvent.Context> ctx)
		{
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					EntityLivingBase ent = null;
					for(Entity e : Minecraft.getInstance().world.loadedEntityList) {
						if(e.getUniqueID().equals(message.uuid)) {
							ent = (EntityLivingBase)e;
							break;
						}
					}
					
					if(ent == null) {
						System.out.println("\n\n*** ERRROR EntityDialog got a null Entity on Client Side *** \n\n");
						return;
					}
					
					Minecraft.getInstance().displayGuiScreen(new DialogGui(ent, ((EntityNPCInteractable)ent).getDialog(message.dialogName)));					
				}
			});
		}
	}
}
