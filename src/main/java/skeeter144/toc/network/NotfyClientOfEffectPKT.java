package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.client.gui.HUD;

public class NotfyClientOfEffectPKT	{

	public static void encode(NotfyClientOfEffectPKT pkt, PacketBuffer buf) {
		buf.writeInt(pkt.nameLength);
		for(int i = 0; i < pkt.nameLength; ++i) {
			buf.writeChar(pkt.name.toCharArray()[i]);
		}
		buf.writeBoolean(pkt.newEffect);
	}
	public static NotfyClientOfEffectPKT decode(PacketBuffer buf) {
		NotfyClientOfEffectPKT pkt = new NotfyClientOfEffectPKT();
		int l = buf.readInt();
		char[] chars = new char[l];
		for(int i = 0; i < l; ++i) {
			chars[i] = buf.readChar();
		}
		pkt.newEffect = buf.readBoolean();
		pkt.name = new String(chars);
			return pkt;
		}
	public static class Handler
	{
		public static void handle(final NotfyClientOfEffectPKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					if(message.newEffect)
						HUD.activeEffects.add(message.name);
					else
						HUD.activeEffects.remove(message.name);
				}
				
			});
		}
	}
	
	String name;
	int nameLength;
	boolean newEffect;
	public NotfyClientOfEffectPKT(){}
	public NotfyClientOfEffectPKT(String name, boolean newEffect) {
		this.name = name;
		nameLength = name.length();
		this.newEffect = newEffect;
	}
//	
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//	
//	}
//
//	public static class NotfyClientOfEffectMessageHandlerHandler<NotfyClientOfEffectMessage, IMessage>{
//
//		@Override
//		public IMessage onMessage(NotfyClientOfEffectMessage message, MessageContext ctx) {
//			
//			return null;
//		}
//	}
	
}
