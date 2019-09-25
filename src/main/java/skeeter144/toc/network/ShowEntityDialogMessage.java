package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ShowEntityDialogMessage{

	public static void encode(ShowEntityDialogMessage pkt, PacketBuffer buf) {}
	public static ShowEntityDialogMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final ShowEntityDialogMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
//	
//	String dialogName;
//	UUID uuid;
//	public ShowEntityDialogMessage() {}
//	public ShowEntityDialogMessage(UUID entityId, String dialogName) {
//		this.uuid = entityId;
//		this.dialogName = dialogName;
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		dialogName = ByteBufUtils.readUTF8String(buf);
//		uuid = new UUID(buf.readLong(), buf.readLong());
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		ByteBufUtils.writeUTF8String(buf, dialogName);
//		buf.writeLong(uuid.getMostSignificantBits());
//		buf.writeLong(uuid.getLeastSignificantBits());
//	}
//	
//	public static class ShowQuestDialogMessageHandlerHandler<ShowEntityDialogMessage, IMessage>{
//		public IMessage onMessage(ShowEntityDialogMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					EntityLivingBase ent = null;
//					for(Entity e : Minecraft.getInstance().world.loadedEntityList) {
//						if(e.getUniqueID().equals(message.uuid)) {
//							ent = (EntityLivingBase)e;
//							break;
//						}
//					}
//					
//					if(ent == null) {
//						System.out.println("\n\n*** ERRROR EntityDialog got a null Entity on Client Side *** \n\n");
//						return;
//					}
//					
//					Minecraft.getInstance().displayGuiScreen(new DialogGui(ent, ((EntityNPCInteractable)ent).getDialog(message.dialogName)));
//				}
//			});
//			return null;
//		}
//	}

}
