package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class LightningBoltCastMessage{
	public static void encode(LightningBoltCastMessage pkt, PacketBuffer buf) {}
	public static LightningBoltCastMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final LightningBoltCastMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
//	
//	public LightningBoltCastMessage() {}
//	
//	boolean didHitEntity; double x, y,  z;
//	int hitId;
//	public LightningBoltCastMessage(boolean hitEntity, double x, double y, double z, Entity hit) {
//		this.didHitEntity = hitEntity;
//		this.x = x;
//		this.y = y;
//		this.z = z;
//		if(hitEntity)
//			this.hitId = hit.getEntityId();
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		this.didHitEntity = buf.readBoolean();
//		this.x = buf.readDouble();
//		this.y = buf.readDouble();
//		this.z = buf.readDouble();
//		this.hitId = buf.readInt();
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeBoolean(didHitEntity);
//		buf.writeDouble(x);
//		buf.writeDouble(y);
//		buf.writeDouble(z);
//		buf.writeInt(hitId);
//	}
//	
//	public static class LightningBoltCastHandlerHandler<LightningBoltCastMessage, IMessage>{
//		public IMessage onMessage(LightningBoltCastMessage message, MessageContext ctx) {
//			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
//				public void run() {
//					Entity player = ctx.getServerHandler().player;
//				}
//			});
//			
//			
//			return null;
//		}
//	
//		
//	}
	
}
