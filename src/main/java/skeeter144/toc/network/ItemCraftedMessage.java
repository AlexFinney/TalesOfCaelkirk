package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.client.gui.SmeltingGui;

public class ItemCraftedMessage implements IMessage{

	public ItemCraftedMessage() {}
	public void toBytes(ByteBuf buf) {}
	public void fromBytes(ByteBuf buf) {}
	
	public static class ItemCraftedMessageHandler implements IMessageHandler<ItemCraftedMessage, IMessage>{
		public IMessage onMessage(ItemCraftedMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					GuiScreen screen = Minecraft.getMinecraft().currentScreen;;
					if(screen instanceof SmeltingGui)
						((SmeltingGui)screen).incrementCrafted();
				}
			});
			return null;
		}
	}
}
