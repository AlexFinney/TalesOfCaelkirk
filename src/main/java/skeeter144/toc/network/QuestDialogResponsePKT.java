package skeeter144.toc.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class QuestDialogResponsePKT{

	public static void encode(QuestDialogResponsePKT pkt, PacketBuffer buf) {}
	public static QuestDialogResponsePKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final QuestDialogResponsePKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	UUID questGiverUUID;
	String dialogResponse;
	public QuestDialogResponsePKT() {}
	public QuestDialogResponsePKT(UUID questGiverUUID, String dialogResponse) {
		this.questGiverUUID = questGiverUUID;
		this.dialogResponse = dialogResponse;
	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		questGiverUUID = new UUID(buf.readLong(), buf.readLong());
//		dialogResponse = ByteBufUtils.readUTF8String(buf);
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeLong(questGiverUUID.getMostSignificantBits());
//		buf.writeLong(questGiverUUID.getLeastSignificantBits());
//		ByteBufUtils.writeUTF8String(buf, dialogResponse);
//	}
//	
//	public static class QuestDialogResponseMessageHandlerHandler<QuestDialogResponseMessage, IMessage>{
//		public IMessage onMessage(QuestDialogResponseMessage message, MessageContext ctx) {
//			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
//				public void run() {
//					EntityPlayerMP player = ctx.getServerHandler().player;
//					for(Entity e : player.world.loadedEntityList) {
//						if(e.getUniqueID().equals(message.questGiverUUID)) {
//							EntityNPCInteractable npc = (EntityNPCInteractable)e;
//							npc.runServerFunction(player.getUniqueID(), message.dialogResponse);
//						}
//					}
//				}
//			});
//			return null;
//		}
//	}

}
