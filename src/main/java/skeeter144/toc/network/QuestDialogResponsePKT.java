package skeeter144.toc.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.entity.mob.npc.questgiver.EntityNPCInteractable;

public class QuestDialogResponsePKT{

	UUID questGiverUUID;
	String dialogResponse;
	public QuestDialogResponsePKT() {}
	public QuestDialogResponsePKT(UUID questGiverUUID, String dialogResponse) {
		this.questGiverUUID = questGiverUUID;
		this.dialogResponse = dialogResponse;
	}
	
	public static void encode(QuestDialogResponsePKT pkt, PacketBuffer buf) 
	{
		buf.writeInt(pkt.dialogResponse.length());
		buf.writeString(pkt.dialogResponse);
		buf.writeLong(pkt.questGiverUUID.getMostSignificantBits());
		buf.writeLong(pkt.questGiverUUID.getLeastSignificantBits());
	}
	
	public static QuestDialogResponsePKT decode(PacketBuffer buf) 
	{
		QuestDialogResponsePKT pkt = new QuestDialogResponsePKT();
		pkt.dialogResponse = buf.readString(buf.readInt());
		pkt.questGiverUUID = new UUID(buf.readLong(), buf.readLong());
		
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final QuestDialogResponsePKT message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(new Runnable() {
				public void run() {
					ServerPlayerEntity player = ctx.get().getSender();
					for (Entity e : ((ClientWorld)Minecraft.getInstance().world).getAllEntities()) {
						if(e.getUniqueID().equals(message.questGiverUUID)) {
							EntityNPCInteractable npc = (EntityNPCInteractable)e;
							npc.runServerFunction(player.getUniqueID(), message.dialogResponse);
						}
					}
				}
			});
		}
	}
}
