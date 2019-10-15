package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class AdjustScreenDimPKT{

	public static void encode(AdjustScreenDimPKT pkt, PacketBuffer buf) {}
	public static AdjustScreenDimPKT decode(PacketBuffer buf) {return null;}
	
	public static class Handler
	{
		public static void handle(final AdjustScreenDimPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	float pct;
	public AdjustScreenDimPKT() {}
	public AdjustScreenDimPKT(float pct) {this.pct = pct;}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		pct = buf.readFloat();
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeFloat(pct);
//	}
//
//	public static class AdjustScreenDimPKTHandlerHandler<AdjustScreenDimPKT, IMessage>{
//		public IMessage onMessage(AdjustScreenDimPKT message, MessageContext ctx) {
//			float startingLight = HUD.lightBlockedPct;
//			float endingLight = message.pct;
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					TOCMain.clientTaskManager.addTask(new TickableTask(40) {
//						public void tick(int worldTick) {
//							float delta = worldTick - start;
//							float fadeTime = 40;
//							
//							float light = startingLight + (endingLight - startingLight) * delta / fadeTime;
//							HUD.lightBlockedPct = light;
//						}
//					});
//				}
//			});
//			return null;
//		}
//		
//	}
//	
}
