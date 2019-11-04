package skeeter144.toc.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.proxy.ClientProxy;

public class SpawnBlockedPKT {

	long msb, lsb;
	public SpawnBlockedPKT() {}
	public SpawnBlockedPKT(Entity e) {
		msb = e.getUniqueID().getMostSignificantBits();
		lsb = e.getUniqueID().getLeastSignificantBits();
	}
	
	public static void encode(SpawnBlockedPKT pkt, PacketBuffer buf) {
		buf.writeLong(pkt.msb);
		buf.writeLong(pkt.lsb);
	}

	public static SpawnBlockedPKT decode(PacketBuffer buf) {
		SpawnBlockedPKT pkt = new SpawnBlockedPKT();
		pkt.msb = buf.readLong();
		pkt.lsb = buf.readLong();
		return pkt;
	}

	public static class Handler {
		public static void handle(final SpawnBlockedPKT message, Supplier<NetworkEvent.Context> ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					UUID uuid = new UUID(message.msb, message.lsb);
					for (Entity e : Minecraft.getInstance().world.loadedEntityList) {
						if (e.getUniqueID().equals(uuid)) {
							ClientProxy.displayParticle(e, "Blocked!");
						}
					}
				}
			});
		}
	}
}
