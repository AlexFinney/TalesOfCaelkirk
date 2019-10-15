package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CloseGuisPKT{

	public static void encode(CloseGuisPKT pkt, PacketBuffer buf) {}
	public static CloseGuisPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final CloseGuisPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
//	public CloseGuisMessage() {	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {}
//
//	@Override
//	public void toBytes(ByteBuf buf) {}
//
//	
//	
//	public static class CloseGuisMessageHandlerHandler<CloseGuisMessage, IMessage>{
//		public IMessage onMessage(CloseGuisMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					Minecraft.getInstance().displayGuiScreen(null);
//				}
//			});
//			return null;
//		}
//	}
}
