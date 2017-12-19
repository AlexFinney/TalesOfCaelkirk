package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CloseGuisMessage implements IMessage{

	public CloseGuisMessage() {	}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	
	
	public static class CloseGuisMessageHandler implements IMessageHandler<CloseGuisMessage, IMessage>{
		public IMessage onMessage(CloseGuisMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					Minecraft.getMinecraft().displayGuiScreen(null);
				}
			});
			return null;
		}
	}
}
