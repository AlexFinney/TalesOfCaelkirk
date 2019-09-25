package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlayerVitalsUpdateMessage {

	public static void encode(OpenShopGuiMessage pkt, PacketBuffer buf) {}
	public static OpenShopGuiMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final OpenShopGuiMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	public PlayerVitalsUpdateMessage() {}
//	
//	int health, mana;
//	public PlayerVitalsUpdateMessage(int health, int mana) {
//		this.health = health;
//		this.mana = mana;
//	}
//	
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeInt(health);
//		buf.writeInt(mana);
//		
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		health = buf.readInt();
//		mana = buf.readInt();
//	}
//	
//	public static class PlayerVitalsUpdateMessageHandlerHandler<PlayerVitalsUpdateMessage, IMessage> {
//
//		@Override
//		public IMessage onMessage(PlayerVitalsUpdateMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//
//				@Override
//				public void run() {
//					if(TOCMain.localPlayer != null) {
//						TOCMain.localPlayer.setHealthAndMana(message.health, message.mana);	
//					}else {
//					}
//				}
//				
//			});
//			
//			
//			return null;
//		}
//	}

}
