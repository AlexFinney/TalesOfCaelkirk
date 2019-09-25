package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SpawnBlockedMessage {

	public static void encode(SpawnBlockedMessage pkt, PacketBuffer buf) {}
	public static SpawnBlockedMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final SpawnBlockedMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	public SpawnBlockedMessage() {}
//	
//	long msb, lsb;
//	public SpawnBlockedMessage(Entity e) {
//		msb = e.getUniqueID().getMostSignificantBits();
//		lsb = e.getUniqueID().getLeastSignificantBits();
//	}
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
