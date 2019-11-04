package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.particles.system.ParticleSystem;

public class SpawnParticlesPKT{
	
	public SpawnParticlesPKT() {}
	
	int systemId;
	double posX, posY, posZ;
	double[] data;
	//allows for 3 additional doubles to be passed
	static final int dataLength = 7;
	public SpawnParticlesPKT(int systemId, BlockPos pos, double...additionalParams) {
		data = new double[dataLength];
		data[0] = systemId;
		data[1] = posX = pos.getX();
		data[2] = posY = pos.getY();
		data[3] = posZ = pos.getZ();
		
		for(int i = 4; i < dataLength; ++i) {
			if(i - 4 < additionalParams.length) {
				data[i] = additionalParams[i - 4];	
			}else {
				data[i] = -1;
			}
		}
	}
	
	public static void encode(SpawnParticlesPKT pkt, PacketBuffer buf) {
		for(int i = 0; i < pkt.data.length; ++i) {
			buf.writeDouble(pkt.data[i]);
		}
	}

	public static SpawnParticlesPKT decode(PacketBuffer buf) {
		SpawnParticlesPKT pkt = new SpawnParticlesPKT();
		pkt.data = new double[dataLength];
		for(int i = 0; i < dataLength; ++i) {
			pkt.data[i] = buf.readDouble();
		}
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final SpawnParticlesPKT message, Supplier<NetworkEvent.Context> ctx){
			int id = (int) message.data[0];
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					World w = Minecraft.getInstance().player.world;
					ParticleSystem system = ParticleSystem.getNewParticleSystem(id);
					system.updatePosition(w, message.data[1], message.data[2], message.data[3]);
					system.setOptionalParams(message.data);
					system.spawnParticles();
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
	
	
}
