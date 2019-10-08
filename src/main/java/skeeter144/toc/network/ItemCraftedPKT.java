package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ItemCraftedMessage{
	public static void encode(ItemCraftedMessage pkt, PacketBuffer buf) {}
	public static ItemCraftedMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final ItemCraftedMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	public ItemCraftedMessage() {}
//	public void toBytes(ByteBuf buf) {}
//	public void fromBytes(ByteBuf buf) {}
//	
//	public static class ItemCraftedMessageHandlerHandler<ItemCraftedMessage, IMessage>{
//		public IMessage onMessage(ItemCraftedMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					GuiScreen screen = Minecraft.getInstance().currentScreen;;
//					if(screen instanceof SmeltingGui)
//						((SmeltingGui)screen).incrementCrafted();
//				}
//			});
//			return null;
//		}
//	}
}
