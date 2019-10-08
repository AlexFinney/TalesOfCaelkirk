package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ShouldShowRegionsMessage {

	public static void encode(ShouldShowRegionsMessage pkt, PacketBuffer buf) {}
	public static ShouldShowRegionsMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final ShouldShowRegionsMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
//	
//	boolean val = false;
//	Map<String, Region> regions;
//	long regionsSize = 0;
//	public ShouldShowRegionsMessage() {}
//	public ShouldShowRegionsMessage(boolean val, Map<String, Region> regions) {
//		this.val = val;
//		this.regions = regions;
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		val = buf.readBoolean();
//		int numBytes = buf.readInt();
//		if(numBytes != 0) {
//			try {
//				ByteBuf bbuf = buf.readBytes(numBytes);
//				byte[] bytes= new byte[numBytes];
//				bbuf.readBytes(bytes);
//				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
//				Map<String, Region> regions = (Map<String, Region>) ois.readObject();
//				this.regions = regions;
//				ois.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeBoolean(val);
//		if(regions == null) {
//			buf.writeLong(0);
//		}else {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			try {
//				ObjectOutputStream oos = new ObjectOutputStream(baos);
//				oos.writeObject(regions);
//				byte[] bytes = baos.toByteArray();
//				buf.writeInt(bytes.length);
//				buf.writeBytes(bytes);
//				oos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public static class ShouldShowRegionsMessageHandlerHandler<ShouldShowRegionsMessage, IMessage>{
//		public IMessage onMessage(ShouldShowRegionsMessage message, MessageContext ctx) {
//			 RegionsRendering.doRender = message.val;
//			 return null;
//		}
//	}
}
