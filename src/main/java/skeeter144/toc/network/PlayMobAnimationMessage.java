package skeeter144.toc.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skeeter144.toc.client.entity.animation.Animations;
import skeeter144.toc.entity.mob.CustomMob;

public class PlayMobAnimationMessage implements IMessage{

	public PlayMobAnimationMessage() {}
	
	UUID uuid;
	String animationName;
	public PlayMobAnimationMessage(Entity entity, String animationName) {
		this.uuid = entity.getUniqueID();
		this.animationName = animationName;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();
		buf.writeLong(msb);
		buf.writeLong(lsb);
		buf.writeInt(animationName.length());
		for(char c : animationName.toCharArray()) {
			buf.writeChar(c);
		}
	}
	
	
	ByteBuf buf;
	@SideOnly(Side.CLIENT)
	public void fromBytes(ByteBuf buf) {
		uuid = new UUID(buf.readLong(), buf.readLong());
		char[] chars = new char[buf.readInt()];
		for(int i = 0; i < chars.length; ++i) {
			chars[i] = buf.readChar();
		}
		animationName = new String(chars);
	}


	public static class PlayMobAnimationMessageHandler implements IMessageHandler<PlayMobAnimationMessage, IMessage>{

		@Override
		public IMessage onMessage(PlayMobAnimationMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					UUID uuid = message.uuid;
					for(Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
						if(e.getUniqueID().equals(uuid)) {
							((CustomMob)e).playAnimation(Animations.get(message.animationName));
							break;
						}
					}
				}
			});
			
			return null;
		}
	}


}