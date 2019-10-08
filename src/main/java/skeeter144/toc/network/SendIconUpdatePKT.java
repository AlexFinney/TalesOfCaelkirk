package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SendIconUpdateMessage{

	public static void encode(SendIconUpdateMessage pkt, PacketBuffer buf) {}
	public static SendIconUpdateMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final SendIconUpdateMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	public SendIconUpdateMessage() {}
//	
//	int x, y, z, dim;
//	String name, rsLocDomain, rsLocPath;
//	boolean shouldRemove;
//	public SendIconUpdateMessage(String name, int x, int y, int z, int dim, String rsLocDomain, String rsLocPath) {
//		this(name, x, y, z, dim, rsLocDomain, rsLocPath, false);
//	}
//	
//	public SendIconUpdateMessage(String name, int x, int y, int z, int dim, String rsLocDomain, String rsLocPath, boolean shouldRemove) {
//		this.name = name;
//		this.rsLocDomain = rsLocDomain;
//		this.rsLocPath = rsLocPath;
//		this.x = x;
//		this.y = y;
//		this.z = z;
//		this.dim  = dim;
//		this.shouldRemove = shouldRemove;
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		ByteBufUtils.writeUTF8String(buf, name);
//		ByteBufUtils.writeUTF8String(buf, rsLocDomain);
//		ByteBufUtils.writeUTF8String(buf, rsLocPath);
//		buf.writeInt(x);
//		buf.writeInt(y);
//		buf.writeInt(z);
//		buf.writeInt(dim);
//		buf.writeBoolean(shouldRemove);
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		name = ByteBufUtils.readUTF8String(buf);
//		rsLocDomain = ByteBufUtils.readUTF8String(buf);
//		rsLocPath = ByteBufUtils.readUTF8String(buf);
//		x = buf.readInt();
//		y = buf.readInt();
//		z = buf.readInt();
//		dim = buf.readInt();
//		shouldRemove = buf.readBoolean();
//	}
//
//	public static class SendIconUpdateMessageHandlerHandler<SendIconUpdateMessage, IMessage> {
//	
//		public SendIconUpdateMessageHandler() {}
//		
//		@Override
//		public IMessage onMessage(SendIconUpdateMessage msg, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					if(!msg.shouldRemove)
//						TOCUtils.addIconMarkerToMap(msg.name, new ResourceLocation(msg.rsLocDomain, msg.rsLocPath), new BlockPos(msg.x, msg.y, msg.z), msg.dim);
//					else
//						TOCUtils.removeIconMarkerFromMap(msg.name, new BlockPos(msg.x, msg.y, msg.z), msg.dim);
//				}	
//			});
//			return null;
//		}
//	}
}
