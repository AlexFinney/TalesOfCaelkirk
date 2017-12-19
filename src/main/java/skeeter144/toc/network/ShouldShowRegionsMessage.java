package skeeter144.toc.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.RegionsRendering;
import skeeter144.toc.regions.Region;

public class ShouldShowRegionsMessage implements IMessage {

	boolean val = false;
	Map<String, Region> regions;
	long regionsSize = 0;
	public ShouldShowRegionsMessage() {}
	public ShouldShowRegionsMessage(boolean val, Map<String, Region> regions) {
		this.val = val;
		this.regions = regions;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		val = buf.readBoolean();
		int numBytes = buf.readInt();
		if(numBytes != 0) {
			try {
				ByteBuf bbuf = buf.readBytes(numBytes);
				byte[] bytes= new byte[numBytes];
				bbuf.readBytes(bytes);
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
				Map<String, Region> regions = (Map<String, Region>) ois.readObject();
				this.regions = regions;
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(val);
		if(regions == null) {
			buf.writeLong(0);
		}else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(regions);
				byte[] bytes = baos.toByteArray();
				buf.writeInt(bytes.length);
				buf.writeBytes(bytes);
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class ShouldShowRegionsMessageHandler implements IMessageHandler<ShouldShowRegionsMessage, IMessage>{
		public IMessage onMessage(ShouldShowRegionsMessage message, MessageContext ctx) {
			 RegionsRendering.doRender = message.val;
			 return null;
		}
	}
}
