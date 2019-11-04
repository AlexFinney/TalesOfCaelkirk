package skeeter144.toc.network;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.client.entity.animation.Animations;
import skeeter144.toc.entity.mob.CustomMob;

public class PlayMobAnimationPKT {

	public PlayMobAnimationPKT() {}

	UUID uuid;
	String animationName;
	public PlayMobAnimationPKT(Entity entity, String animationName) {
		this.uuid = entity.getUniqueID();
		this.animationName = animationName;
	}

	public static void encode(PlayMobAnimationPKT pkt, PacketBuffer buf) {
		long msb = pkt.uuid.getMostSignificantBits();
		long lsb = pkt.uuid.getLeastSignificantBits();
		buf.writeLong(msb);
		buf.writeLong(lsb);
		buf.writeInt(pkt.animationName.length());
		for (char c : pkt.animationName.toCharArray()) {
			buf.writeChar(c);
		}
	}

	public static PlayMobAnimationPKT decode(PacketBuffer buf) {
		PlayMobAnimationPKT pkt = new PlayMobAnimationPKT();

		pkt.uuid = new UUID(buf.readLong(), buf.readLong());
		char[] chars = new char[buf.readInt()];
		for (int i = 0; i < chars.length; ++i) {
			chars[i] = buf.readChar();
		}
		pkt.animationName = new String(chars);
		return pkt;
	}

	public static class Handler {
		public static void handle(final PlayMobAnimationPKT message, Supplier<NetworkEvent.Context> ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					UUID uuid = message.uuid;
					for (Entity e : Minecraft.getInstance().world.loadedEntityList) {
						if (e.getUniqueID().equals(uuid)) {
							((CustomMob) e).playAnimation(Animations.get(message.animationName));
							break;
						}
					}
				}
			});
		}
	}
}