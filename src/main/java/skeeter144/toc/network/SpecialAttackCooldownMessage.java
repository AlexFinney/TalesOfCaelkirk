package skeeter144.toc.network;

import org.apache.commons.lang3.tuple.MutablePair;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;
import skeeter144.toc.player.TOCPlayer;

public class SpecialAttackCooldownMessage implements IMessage {

	public SpecialAttackCooldownMessage(){}

	String name;
	byte cooldown, maxCooldown;
	public SpecialAttackCooldownMessage(String name, byte cooldown, byte maxCooldown) {
		this.cooldown = cooldown;
		this.maxCooldown = maxCooldown;
		this.name = name;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		name = "";
		while(true) {
			char c = buf.readChar();
			if(c != '\n')
				name += c;
			else
				break;
		}
		cooldown = buf.readByte();
		maxCooldown = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		for(int i = 0; i < name.length(); ++i) {
			buf.writeChar(name.charAt(i));
		}
		buf.writeChar('\n');
		buf.writeByte(cooldown);
		buf.writeByte(maxCooldown);
	}
	
	public static class SpecialAttackCooldownMessageHandler implements IMessageHandler<SpecialAttackCooldownMessage, IMessage>{

		@Override
		public IMessage onMessage(SpecialAttackCooldownMessage message, MessageContext ctx) {
			
			TOCPlayer p = TOCMain.localPlayer;
			if(message.cooldown <= 0) {
				p.specialAttackCooldowns.remove(message.name);
			}else{
				p.specialAttackCooldowns.put(message.name, new MutablePair((int) message.cooldown, (int)message.maxCooldown));
			}
			
			return null;
		}
		
	}

}
