package skeeter144.toc.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.entity.mob.CustomMob;

public class AnimationEventPKT{
	public static void encode(AnimationEventPKT pkt, PacketBuffer buf) {}
	public static AnimationEventPKT decode(PacketBuffer buf) {return null;}
	
	public static class Handler
	{
		public static void handle(final AnimationEventPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	public AnimationEventPKT() {}
	
	UUID uuid;
	String functionName;
	public AnimationEventPKT(String functionName, CustomMob mob) {
		uuid = mob.getUniqueID();
		this.functionName = functionName;
	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		uuid = new UUID(buf.readLong(), buf.readLong());
//		
//		char[] funcName = new char[buf.readInt()];
//		for(int i = 0; i < funcName.length; ++i) {
//			funcName[i] = buf.readChar();
//		}
//		
//		functionName = new String(funcName);
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeLong(uuid.getMostSignificantBits());
//		buf.writeLong(uuid.getLeastSignificantBits());
//		
//		buf.writeInt(functionName.length());
//		for(char c : functionName.toCharArray()) {
//			buf.writeChar(c);
//		}
//	}
//	
//	public static class AnimationEventMessageHandlerHandler<AnimationEventMessage, IMessage>{
//		public IMessage onMessage(AnimationEventMessage message, MessageContext ctx) {
//			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
//				@Override
//				public void run() {
//					Class<AnimationEvents> c = AnimationEvents.class;
//					try {
//						Method[] methods = c.getMethods();
//						for (Method m : methods) {
//						    if (message.functionName.equals(m.getName())) {
//						    	for(Entity e : ctx.getServerHandler().player.world.loadedEntityList) {
//									if(e.getUniqueID().equals(message.uuid)) {
//										m.invoke(null, e);
//										break;
//									}
//								}
//						    	
//						        break;
//						    }
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//			
//			return null;
//		}
//	}
}
