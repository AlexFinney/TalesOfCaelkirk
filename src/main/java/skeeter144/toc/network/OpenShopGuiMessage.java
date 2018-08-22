package skeeter144.toc.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.client.gui.ShopGUI;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData;

public class OpenShopGuiMessage implements IMessage{

	public ShopData data;
	public OpenShopGuiMessage() {}
	public OpenShopGuiMessage(ShopData data) {
		this.data = data;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(data);
			buf.writeInt(baos.toByteArray().length);
			buf.writeBytes(baos.toByteArray());
			baos.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readInt();
		byte[] bytes = new byte[len];
		buf.readBytes(bytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			data = (ShopData) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	public static class OpenShopGuiMessageHandler implements IMessageHandler<OpenShopGuiMessage, IMessage>{
		public IMessage onMessage(OpenShopGuiMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					Minecraft.getMinecraft().displayGuiScreen(new ShopGUI(message.data));
				}
			});
			return null;
		}
	}

}
