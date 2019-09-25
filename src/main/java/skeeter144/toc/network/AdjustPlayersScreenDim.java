package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class AdjustPlayersScreenDim{

	public static void encode(AdjustPlayersScreenDim pkt, PacketBuffer buf) {}
	public static AdjustPlayersScreenDim decode(PacketBuffer buf) {return null;}
	
	public static class Handler
	{
		public static void handle(final AdjustPlayersScreenDim message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	float pct;
//	public AdjustPlayersScreenDim() {}
//	public AdjustPlayersScreenDim(float pct) {this.pct = pct;}
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
//	public static class AdjustPlayersScreenDimHandlerHandler<AdjustPlayersScreenDim, IMessage>{
//		public IMessage onMessage(AdjustPlayersScreenDim message, MessageContext ctx) {
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
