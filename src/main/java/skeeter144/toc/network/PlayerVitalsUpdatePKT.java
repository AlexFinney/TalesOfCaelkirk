package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlayerVitalsUpdatePKT {

	public static void encode(PlayerVitalsUpdatePKT pkt, PacketBuffer buf) {}
	public static PlayerVitalsUpdatePKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final PlayerVitalsUpdatePKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	public PlayerVitalsUpdatePKT() {}
	
	int health, mana;
	public PlayerVitalsUpdatePKT(int health, int mana) {
		this.health = health;
		this.mana = mana;
	}
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
