package skeeter144.toc.network;

import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.MutablePair;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.player.TOCPlayer;

public class SpecialAttackCooldownPKT {

	String name;
	byte cooldown, maxCooldown;
	public SpecialAttackCooldownPKT(){}
	public SpecialAttackCooldownPKT(String name, byte cooldown, byte maxCooldown) {
		this.cooldown = cooldown;
		this.maxCooldown = maxCooldown;
		this.name = name;
	}
	
	public static void encode(SpecialAttackCooldownPKT pkt, PacketBuffer buf) {
		for(int i = 0; i < pkt.name.length(); ++i) {
			buf.writeChar(pkt.name.charAt(i));
		}
		buf.writeChar('\n');
		buf.writeByte(pkt.cooldown);
		buf.writeByte(pkt.maxCooldown);
	}
	public static SpecialAttackCooldownPKT decode(PacketBuffer buf) {
		SpecialAttackCooldownPKT pkt = new SpecialAttackCooldownPKT();
		
		pkt.name = "";
		while(true) {
			char c = buf.readChar();
			if(c != '\n')
				pkt.name += c;
			else
				break;
		}
		pkt.cooldown = buf.readByte();
		pkt.maxCooldown = buf.readByte();
		
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final SpecialAttackCooldownPKT message, Supplier<NetworkEvent.Context> ctx){
			TOCPlayer p = TOCMain.localPlayer;
			if(message.cooldown <= 0) {
				p.specialAttackCooldowns.remove(message.name);
			}else{
				p.specialAttackCooldowns.put(message.name, new MutablePair<Integer, Integer>((int) message.cooldown, (int)message.maxCooldown));
			}
		}
	}
}
