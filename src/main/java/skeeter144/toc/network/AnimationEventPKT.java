package skeeter144.toc.network;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.event.AnimationEvents;

public class AnimationEventPKT {
	public static void encode(AnimationEventPKT pkt, PacketBuffer buf) {
		buf.writeLong(pkt.uuid.getMostSignificantBits());
		buf.writeLong(pkt.uuid.getLeastSignificantBits());

		buf.writeInt(pkt.functionName.length());
		for (char c : pkt.functionName.toCharArray()) {
			buf.writeChar(c);
		}
	}

	public static AnimationEventPKT decode(PacketBuffer buf) {
		AnimationEventPKT pkt = new AnimationEventPKT();
		pkt.uuid = new UUID(buf.readLong(), buf.readLong());

		char[] funcName = new char[buf.readInt()];
		for (int i = 0; i < funcName.length; ++i) {
			funcName[i] = buf.readChar();
		}

		pkt.functionName = new String(funcName);
		return pkt;
	}

	public static class Handler {
		public static void handle(final AnimationEventPKT message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(new Runnable() {
				@Override
				public void run() {
					Class<AnimationEvents> c = AnimationEvents.class;
					try {
						Method[] methods = c.getMethods();
						for (Method m : methods) {
							if (message.functionName.equals(m.getName())) {
								Entity e = ((ServerWorld) ctx.get().getSender().world).getEntityByUuid(message.uuid);
								m.invoke(null, e);
								break;
							}

							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

	}

	public AnimationEventPKT() {
	}

	UUID uuid;
	String functionName;

	public AnimationEventPKT(String functionName, CustomMob mob) {
		uuid = mob.getUniqueID();
		this.functionName = functionName;
	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {

//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		
//	}
//	
//	public static class AnimationEventMessageHandlerHandler<AnimationEventMessage, IMessage>{
//		public IMessage onMessage(AnimationEventMessage message, MessageContext ctx) {
//			
//			
//			return null;
//		}
//	}
}
