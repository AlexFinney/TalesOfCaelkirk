package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ItemCraftingQueueAddedPKT{

	public static void encode(ItemCraftingQueueAddedPKT pkt, PacketBuffer buf) {}
	public static ItemCraftingQueueAddedPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final ItemCraftingQueueAddedPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	public ItemCraftingQueueAddedMessage() {}
//	public void fromBytes(ByteBuf buf) {}
//	public void toBytes(ByteBuf buf) {}
//	
//	public static class ItemCraftingQueueAddedMessageHandlerHandler<ItemCraftingQueueAddedMessage, IMessage>{
//		public IMessage onMessage(ItemCraftingQueueAddedMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					if(Minecraft.getInstance().currentScreen instanceof SmeltingGui) {
//						((SmeltingGui)Minecraft.getInstance().currentScreen).incrementTotalCrafted();
//					}
//				}
//			});
//			return null;
//		}
//		
//	}
	
}
