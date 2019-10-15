package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SpawnBlockedPKT {

	public static void encode(SpawnBlockedPKT pkt, PacketBuffer buf) {}
	public static SpawnBlockedPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final SpawnBlockedPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	public SpawnBlockedPKT() {}
	
	long msb, lsb;
	public SpawnBlockedPKT(Entity e) {
		msb = e.getUniqueID().getMostSignificantBits();
		lsb = e.getUniqueID().getLeastSignificantBits();
	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		msb = buf.readLong();
//		lsb = buf.readLong();
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeLong(msb);
//		buf.writeLong(lsb);
//	}
//	
//	public static class SpawnBlockedMessageHandlerHandler<SpawnBlockedMessage, IMessage> {
//
//		@Override
//		public IMessage onMessage(SpawnBlockedMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					UUID uuid = new UUID(message.msb, message.lsb);
//					for(Entity e : Minecraft.getInstance().world.loadedEntityList) {
//						if(e.getUniqueID().equals(uuid)) {
//							ClientProxy.displayParticle(e, "Blocked!");
//						}
//					}
//				}
//			});
//			
//			return null;
//		}
//	}

}
