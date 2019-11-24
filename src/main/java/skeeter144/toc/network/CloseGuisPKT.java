package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CloseGuisPKT{

	public static void encode(CloseGuisPKT pkt, PacketBuffer buf) {}
	public static CloseGuisPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final CloseGuisPKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().deferTask(new Runnable() {
				public void run() {
					Minecraft.getInstance().displayGuiScreen(null);
				}
			});
		}
	}
}
