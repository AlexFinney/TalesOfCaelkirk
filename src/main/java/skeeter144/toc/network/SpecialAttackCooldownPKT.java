package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SpecialAttackCooldownPKT {

	public static void encode(SpecialAttackCooldownPKT pkt, PacketBuffer buf) {}
	public static SpecialAttackCooldownPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final SpecialAttackCooldownPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	public SpecialAttackCooldownPKT(){}

	String name;
	byte cooldown, maxCooldown;
	public SpecialAttackCooldownPKT(String name, byte cooldown, byte maxCooldown) {
		this.cooldown = cooldown;
		this.maxCooldown = maxCooldown;
		this.name = name;
	}
//	
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		name = "";
//		while(true) {
//			char c = buf.readChar();
//			if(c != '\n')
//				name += c;
//			else
//				break;
//		}
//		cooldown = buf.readByte();
//		maxCooldown = buf.readByte();
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		for(int i = 0; i < name.length(); ++i) {
//			buf.writeChar(name.charAt(i));
//		}
//		buf.writeChar('\n');
//		buf.writeByte(cooldown);
//		buf.writeByte(maxCooldown);
//	}
//	
//	public static class SpecialAttackCooldownMessageHandlerHandler<SpecialAttackCooldownMessage, IMessage>{
//
//		@Override
//		public IMessage onMessage(SpecialAttackCooldownMessage message, MessageContext ctx) {
//			
//			TOCPlayer p = TOCMain.localPlayer;
//			if(message.cooldown <= 0) {
//				p.specialAttackCooldowns.remove(message.name);
//			}else{
//				p.specialAttackCooldowns.put(message.name, new MutablePair((int) message.cooldown, (int)message.maxCooldown));
//			}
//			
//			return null;
//		}
//		
//	}

}
