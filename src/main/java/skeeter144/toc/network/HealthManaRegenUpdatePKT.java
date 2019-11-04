package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class HealthManaRegenUpdatePKT {
	public HealthManaRegenUpdatePKT() {}

	float mana, health;
	public HealthManaRegenUpdatePKT(float health, float mana) {
		this.health = health;
		this.mana = mana;
	}

	public static void encode(HealthManaRegenUpdatePKT pkt, PacketBuffer buf) {
		buf.writeFloat(pkt.health);
		buf.writeFloat(pkt.mana);
	}

	public static HealthManaRegenUpdatePKT decode(PacketBuffer buf) {
		HealthManaRegenUpdatePKT pkt = new HealthManaRegenUpdatePKT();
		pkt.health = buf.readFloat();
		pkt.mana = buf.readFloat();
		return pkt;
	}

	public static class Handler {
		public static void handle(final HealthManaRegenUpdatePKT message, Supplier<NetworkEvent.Context> ctx) {
		}
	}
}
