package skeeter144.toc.network;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.client.entity.animation.Animations;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.event.AnimationEvents;

public class AnimationEventMessage implements IMessage{

	public AnimationEventMessage() {}
	
	UUID uuid;
	String functionName;
	public AnimationEventMessage(String functionName, CustomMob mob) {
		uuid = mob.getUniqueID();
		this.functionName = functionName;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		uuid = new UUID(buf.readLong(), buf.readLong());
		
		char[] funcName = new char[buf.readInt()];
		for(int i = 0; i < funcName.length; ++i) {
			funcName[i] = buf.readChar();
		}
		
		functionName = new String(funcName);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
		
		buf.writeInt(functionName.length());
		for(char c : functionName.toCharArray()) {
			buf.writeChar(c);
		}
	}
	
	public static class AnimationEventMessageHandler implements IMessageHandler<AnimationEventMessage, IMessage>{
		public IMessage onMessage(AnimationEventMessage message, MessageContext ctx) {
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
				@Override
				public void run() {
					Class c = AnimationEvents.class;
					try {
						Method[] methods = c.getMethods();
						for (Method m : methods) {
						    if (message.functionName.equals(m.getName())) {
						    	for(Entity e : ctx.getServerHandler().player.world.loadedEntityList) {
									if(e.getUniqueID().equals(message.uuid)) {
										m.invoke(null, e);
										break;
									}
								}
						    	
						        break;
						    }
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			return null;
		}
	}
}
