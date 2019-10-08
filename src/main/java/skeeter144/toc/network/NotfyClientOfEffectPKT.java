package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class NotfyClientOfEffectMessage	{

	public static void encode(NotfyClientOfEffectMessage pkt, PacketBuffer buf) {}
	public static NotfyClientOfEffectMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final NotfyClientOfEffectMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	String name;
	int nameLength;
	boolean newEffect;
	public NotfyClientOfEffectMessage(){}
	public NotfyClientOfEffectMessage(String name, boolean newEffect) {
		this.name = name;
		nameLength = name.length();
		this.newEffect = newEffect;
	}
//	
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		int l = buf.readInt();
//		char[] chars = new char[l];
//		for(int i = 0; i < l; ++i) {
//			chars[i] = buf.readChar();
//		}
//		this.newEffect = buf.readBoolean();
//		this.name = new String(chars);
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeInt(nameLength);
//		for(int i = 0; i < nameLength; ++i) {
//			buf.writeChar(name.toCharArray()[i]);
//		}
//		buf.writeBoolean(newEffect);
//	}
//
//	public static class NotfyClientOfEffectMessageHandlerHandler<NotfyClientOfEffectMessage, IMessage>{
//
//		@Override
//		public IMessage onMessage(NotfyClientOfEffectMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					if(message.newEffect)
//						HUD.activeEffects.add(message.name);
//					else
//						HUD.activeEffects.remove(message.name);
//				}
//				
//			});
//			return null;
//		}
//	}
	
}
