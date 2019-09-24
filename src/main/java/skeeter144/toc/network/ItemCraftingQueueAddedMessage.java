package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.client.gui.SmeltingGui;

public class ItemCraftingQueueAddedMessage implements IMessage{

	public ItemCraftingQueueAddedMessage() {}
	public void fromBytes(ByteBuf buf) {}
	public void toBytes(ByteBuf buf) {}
	
	public static class ItemCraftingQueueAddedMessageHandler implements IMessageHandler<ItemCraftingQueueAddedMessage, IMessage>{
		public IMessage onMessage(ItemCraftingQueueAddedMessage message, MessageContext ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					if(Minecraft.getInstance().currentScreen instanceof SmeltingGui) {
						((SmeltingGui)Minecraft.getInstance().currentScreen).incrementTotalCrafted();
					}
				}
			});
			return null;
		}
		
	}
	
}
