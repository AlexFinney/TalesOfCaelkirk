package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class OpenShopGuiMessage{

	public static void encode(OpenShopGuiMessage pkt, PacketBuffer buf) {}
	public static OpenShopGuiMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final OpenShopGuiMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	public ShopData data;
//	public OpenShopGuiMessage() {}
//	public OpenShopGuiMessage(ShopData data) {
//		this.data = data;
//	}
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
