package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData;

public class OpenShopGuiPKT{

	public static void encode(OpenShopGuiPKT pkt, PacketBuffer buf) {}
	public static OpenShopGuiPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final OpenShopGuiPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	public ShopData data;
	public OpenShopGuiPKT() {}
	public OpenShopGuiPKT(ShopData data) {
		this.data = data;
	}
//	
//	@Override
//	public void toBytes(ByteBuf buf) {
//		try {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ObjectOutputStream oos = new ObjectOutputStream(baos);
//			oos.writeObject(data);
//			buf.writeInt(baos.toByteArray().length);
//			buf.writeBytes(baos.toByteArray());
//			baos.close();
//			oos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		int len = buf.readInt();
//		byte[] bytes = new byte[len];
//		buf.readBytes(bytes);
//		try {
//			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
//			data = (ShopData) ois.readObject();
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	
//	public static class OpenShopGuiMessageHandlerHandler<OpenShopGuiMessage, IMessage>{
//		public IMessage onMessage(OpenShopGuiMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					Minecraft.getInstance().displayGuiScreen(new ShopGUI(message.data));
//				}
//			});
//			return null;
//		}
//	}

}
