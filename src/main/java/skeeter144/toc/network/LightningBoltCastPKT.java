package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class LightningBoltCastPKT{
	public static void encode(LightningBoltCastPKT pkt, PacketBuffer buf) {
		buf.writeBoolean(pkt.didHitEntity);
		buf.writeDouble(pkt.x);
		buf.writeDouble(pkt.y);
		buf.writeDouble(pkt.z);
		buf.writeInt(pkt.hitId);
	}
	public static LightningBoltCastPKT decode(PacketBuffer buf) {
		LightningBoltCastPKT pkt = new LightningBoltCastPKT();
		pkt.didHitEntity = buf.readBoolean();
		pkt.x = buf.readDouble();
		pkt.y = buf.readDouble();
		pkt.z = buf.readDouble();
		pkt.hitId = buf.readInt();
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final LightningBoltCastPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	public LightningBoltCastPKT() {}
	
	boolean didHitEntity; double x, y,  z;
	int hitId;
	public LightningBoltCastPKT(boolean hitEntity, double x, double y, double z, Entity hit) {
		this.didHitEntity = hitEntity;
		this.x = x;
		this.y = y;
		this.z = z;
		if(hitEntity)
			this.hitId = hit.getEntityId();
	}
}
