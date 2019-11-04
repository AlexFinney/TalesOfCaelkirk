package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SendIconUpdatePKT{

	public static void encode(SendIconUpdatePKT pkt, PacketBuffer buf) {
		buf.writeString(pkt.name);
		buf.writeString(pkt.rsLocDomain);
		buf.writeString(pkt.rsLocPath);
		buf.writeInt(pkt.x);
		buf.writeInt(pkt.y);
		buf.writeInt(pkt.z);
		buf.writeInt(pkt.dim);
		buf.writeBoolean(pkt.shouldRemove);
	}
	
	public static SendIconUpdatePKT decode(PacketBuffer buf) {
		SendIconUpdatePKT pkt = new SendIconUpdatePKT();
		pkt.name = buf.readString(1024);
		pkt.rsLocDomain = buf.readString(1024);
		pkt.rsLocPath = buf.readString(1024);
		pkt.x = buf.readInt();
		pkt.y = buf.readInt();
		pkt.z = buf.readInt();
		pkt.dim = buf.readInt();
		pkt.shouldRemove = buf.readBoolean();
		
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final SendIconUpdatePKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
//					if(!msg.shouldRemove)
//						TOCUtils.addIconMarkerToMap(msg.name, new ResourceLocation(msg.rsLocDomain, msg.rsLocPath), new BlockPos(msg.x, msg.y, msg.z), msg.dim);
//					else
//						TOCUtils.removeIconMarkerFromMap(msg.name, new BlockPos(msg.x, msg.y, msg.z), msg.dim);
				}	
			});
		}
	}
	
	public SendIconUpdatePKT() {}
	
	int x, y, z, dim;
	String name, rsLocDomain, rsLocPath;
	boolean shouldRemove;
	public SendIconUpdatePKT(String name, int x, int y, int z, int dim, String rsLocDomain, String rsLocPath) {
		this(name, x, y, z, dim, rsLocDomain, rsLocPath, false);
	}
	
	public SendIconUpdatePKT(String name, int x, int y, int z, int dim, String rsLocDomain, String rsLocPath, boolean shouldRemove) {
		this.name = name;
		this.rsLocDomain = rsLocDomain;
		this.rsLocPath = rsLocPath;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dim  = dim;
		this.shouldRemove = shouldRemove;
	}
}
