package skeeter144.toc.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.client.gui.RegionsRendering;
import skeeter144.toc.regions.Region;

public class ShouldShowRegionsPKT {
	boolean val = false;
	Map<String, Region> regions;
	long regionsSize = 0;

	public ShouldShowRegionsPKT() {
	}

	public ShouldShowRegionsPKT(boolean val, Map<String, Region> regions) {
		this.val = val;
		this.regions = regions;
	}

	public static void encode(ShouldShowRegionsPKT pkt, PacketBuffer buf) {
		buf.writeBoolean(pkt.val);
		if (pkt.regions == null) {
			buf.writeLong(0);
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(pkt.regions);
				byte[] bytes = baos.toByteArray();
				buf.writeInt(bytes.length);
				buf.writeBytes(bytes);
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static ShouldShowRegionsPKT decode(PacketBuffer buf) {
		ShouldShowRegionsPKT pkt = new ShouldShowRegionsPKT();

		pkt.val = buf.readBoolean();
		int numBytes = buf.readInt();
		if (numBytes != 0) {
			try {
				ByteBuf bbuf = buf.readBytes(numBytes);
				byte[] bytes = new byte[numBytes];
				bbuf.readBytes(bytes);
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
				Map<String, Region> regions = (Map<String, Region>) ois.readObject();
				pkt.regions = regions;
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pkt;
	}

	public static class Handler {
		public static void handle(final ShouldShowRegionsPKT message, Supplier<NetworkEvent.Context> ctx) {
			RegionsRendering.doRender = message.val;
		}
	}
}
