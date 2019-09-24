package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.client.gui.HUD;

public class NotfyClientOfEffectMessage implements IMessage	{

	String name;
	int nameLength;
	boolean newEffect;
	public NotfyClientOfEffectMessage(){}
	public NotfyClientOfEffectMessage(String name, boolean newEffect) {
		this.name = name;
		nameLength = name.length();
		this.newEffect = newEffect;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int l = buf.readInt();
		char[] chars = new char[l];
		for(int i = 0; i < l; ++i) {
			chars[i] = buf.readChar();
		}
		this.newEffect = buf.readBoolean();
		this.name = new String(chars);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(nameLength);
		for(int i = 0; i < nameLength; ++i) {
			buf.writeChar(name.toCharArray()[i]);
		}
		buf.writeBoolean(newEffect);
	}

	public static class NotfyClientOfEffectMessageHandler implements IMessageHandler<NotfyClientOfEffectMessage, IMessage>{

		@Override
		public IMessage onMessage(NotfyClientOfEffectMessage message, MessageContext ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					if(message.newEffect)
						HUD.activeEffects.add(message.name);
					else
						HUD.activeEffects.remove(message.name);
				}
				
			});
			return null;
		}
	}
	
}
