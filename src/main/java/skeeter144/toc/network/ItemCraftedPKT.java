package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.client.gui.SmeltingGui;

public class ItemCraftedPKT{
	public static void encode(ItemCraftedPKT pkt, PacketBuffer buf) {}
	public static ItemCraftedPKT decode(PacketBuffer buf) {return new ItemCraftedPKT();}
	public static class Handler
	{
		public static void handle(final ItemCraftedPKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					GuiScreen screen = Minecraft.getInstance().currentScreen;;
					if(screen instanceof SmeltingGui)
						((SmeltingGui)screen).incrementCrafted();
				}
			});
		}
	}
}
