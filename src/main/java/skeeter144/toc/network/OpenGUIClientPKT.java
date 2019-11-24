package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.client.gui.GuiHandler;

public class OpenGUIClientPKT{

	int id;
	BlockPos pos;
	public OpenGUIClientPKT() {}
	public OpenGUIClientPKT(int id, BlockPos pos) {
		this.id = id;
		this.pos = pos;
	}
	
	public static void encode(OpenGUIClientPKT pkt, PacketBuffer buf) {
		buf.writeInt(pkt.id);
		buf.writeBlockPos(pkt.pos);
	}
	
	public static OpenGUIClientPKT decode(PacketBuffer buf) {
		OpenGUIClientPKT pkt = new OpenGUIClientPKT();
		pkt.id = buf.readInt();
		pkt.pos = buf.readBlockPos();
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final OpenGUIClientPKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().deferTask(new Runnable() {
				public void run() {
					Minecraft.getInstance().displayGuiScreen((Screen) GuiHandler.Instance()
							.getClientGuiElement(message.id, Minecraft.getInstance().player,  Minecraft.getInstance().world, 
									message.pos.getX(), message.pos.getY(), message.pos.getZ()));
				}
			});
		}
	}
}
