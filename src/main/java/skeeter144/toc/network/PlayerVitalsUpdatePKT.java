package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.TOCMain;

public class PlayerVitalsUpdatePKT {
	
	public PlayerVitalsUpdatePKT() {}
	int health, mana;
	public PlayerVitalsUpdatePKT(int health, int mana) {
		this.health = health;
		this.mana = mana;
	}
	
	public static void encode(PlayerVitalsUpdatePKT pkt, PacketBuffer buf) {
		buf.writeInt(pkt.health);
		buf.writeInt(pkt.mana);
	}
	
	public static PlayerVitalsUpdatePKT decode(PacketBuffer buf) {
		PlayerVitalsUpdatePKT pkt = new PlayerVitalsUpdatePKT();
		pkt.health = buf.readInt();
		pkt.mana = buf.readInt();
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final PlayerVitalsUpdatePKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().deferTask(new Runnable() {
				@Override
				public void run() {
					if (TOCMain.localPlayer != null) {
						TOCMain.localPlayer.setHealthAndMana(message.health, message.mana);
					}
				}
			});
		}
	}
}
