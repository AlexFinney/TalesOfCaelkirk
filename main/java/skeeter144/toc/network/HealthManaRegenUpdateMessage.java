package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;

public class HealthManaRegenUpdateMessage implements IMessage {

	public HealthManaRegenUpdateMessage() {}
	
	float mana, health;
	public HealthManaRegenUpdateMessage(float health, float mana) {
	this.health = health;
	this.mana = mana;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		health = buf.readFloat();
		mana = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(health);
		buf.writeFloat(mana);
	}


	public static class HealthManaRegenUpdateHandler implements IMessageHandler<HealthManaRegenUpdateMessage, IMessage>{
		
		@Override
		public IMessage onMessage(HealthManaRegenUpdateMessage message, MessageContext ctx) {
			TOCMain.localPlayer.setHealthAndManaRegen(message.health, message.mana);
			return null;
		}
	
	}
	
}
