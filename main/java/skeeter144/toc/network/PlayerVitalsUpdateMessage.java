package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;

public class PlayerVitalsUpdateMessage implements IMessage {

	public PlayerVitalsUpdateMessage() {}
	
	int health, mana;
	public PlayerVitalsUpdateMessage(int health, int mana) {
		this.health = health;
		this.mana = mana;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(health);
		buf.writeInt(mana);
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		health = buf.readInt();
		mana = buf.readInt();
	}
	
	public static class PlayerVitalsUpdateMessageHandler implements IMessageHandler<PlayerVitalsUpdateMessage, IMessage> {

		@Override
		public IMessage onMessage(PlayerVitalsUpdateMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {

				@Override
				public void run() {
					if(TOCMain.localPlayer != null) {
						TOCMain.localPlayer.setHealthAndMana(message.health, message.mana);	
					}else {
					}
				}
				
			});
			
			
			return null;
		}
	}

}
