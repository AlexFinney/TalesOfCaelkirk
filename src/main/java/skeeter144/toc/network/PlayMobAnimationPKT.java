package skeeter144.toc.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlayMobAnimationPKT{

	public static void encode(PlayMobAnimationPKT pkt, PacketBuffer buf) {}
	public static PlayMobAnimationPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final PlayMobAnimationPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	public PlayMobAnimationPKT() {}
	
	UUID uuid;
	String animationName;
	public PlayMobAnimationPKT(Entity entity, String animationName) {
		this.uuid = entity.getUniqueID();
		this.animationName = animationName;
	}
//	
//	@Override
//	public void toBytes(ByteBuf buf) {
//		long msb = uuid.getMostSignificantBits();
//		long lsb = uuid.getLeastSignificantBits();
//		buf.writeLong(msb);
//		buf.writeLong(lsb);
//		buf.writeInt(animationName.length());
//		for(char c : animationName.toCharArray()) {
//			buf.writeChar(c);
//		}
//	}
//	
//	
//	ByteBuf buf;
//	@OnlyIn(Dist.CLIENT)
//	public void fromBytes(ByteBuf buf) {
//		uuid = new UUID(buf.readLong(), buf.readLong());
//		char[] chars = new char[buf.readInt()];
//		for(int i = 0; i < chars.length; ++i) {
//			chars[i] = buf.readChar();
//		}
//		animationName = new String(chars);
//	}
//
//
//	public static class PlayMobAnimationMessageHandlerHandler<PlayMobAnimationMessage, IMessage>{
//
//		@Override
//		public IMessage onMessage(PlayMobAnimationMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					UUID uuid = message.uuid;
//					for(Entity e : Minecraft.getInstance().world.loadedEntityList) {
//						if(e.getUniqueID().equals(uuid)) {
//							((CustomMob)e).playAnimation(Animations.get(message.animationName));
//							break;
//						}
//					}
//				}
//			});
//			
//			return null;
//		}
//	}


}