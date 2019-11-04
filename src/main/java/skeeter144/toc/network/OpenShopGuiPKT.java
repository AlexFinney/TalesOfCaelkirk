package skeeter144.toc.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.client.gui.ShopGUI;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData;

public class OpenShopGuiPKT {

	public ShopData data;

	public OpenShopGuiPKT() {
	}

	public OpenShopGuiPKT(ShopData data) {
		this.data = data;
	}

	public static void encode(OpenShopGuiPKT pkt, PacketBuffer buf) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(pkt.data);
			buf.writeInt(baos.toByteArray().length);
			buf.writeBytes(baos.toByteArray());
			baos.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static OpenShopGuiPKT decode(PacketBuffer buf) {
		OpenShopGuiPKT pkt = new OpenShopGuiPKT();
		int len = buf.readInt();
		byte[] bytes = new byte[len];
		buf.readBytes(bytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			pkt.data = (ShopData) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class Handler {
		public static void handle(final OpenShopGuiPKT message, Supplier<NetworkEvent.Context> ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					Minecraft.getInstance().displayGuiScreen(new ShopGUI(message.data));
				}
			});
		}
	}
}
