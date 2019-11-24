package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.client.gui.SmeltingGui;

public class ItemCraftingQueueAddedPKT{

	public static void encode(ItemCraftingQueueAddedPKT pkt, PacketBuffer buf) {}
	public static ItemCraftingQueueAddedPKT decode(PacketBuffer buf) {
		return new ItemCraftingQueueAddedPKT();
	}
	public static class Handler
	{
		public static void handle(final ItemCraftingQueueAddedPKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().deferTask(new Runnable() {
				public void run() {
					if(Minecraft.getInstance().currentScreen instanceof SmeltingGui) {
						((SmeltingGui)Minecraft.getInstance().currentScreen).incrementTotalCrafted();
					}
				}
			});
		}
	}
}
