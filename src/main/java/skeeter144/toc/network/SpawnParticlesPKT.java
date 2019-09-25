package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SpawnParticlesPKT{

	public static void encode(SpawnParticlesPKT pkt, PacketBuffer buf) {}
	public static SpawnParticlesPKT decode(PacketBuffer buf) {return null;}
	
	public static class Handler
	{
		public static void handle(final SpawnParticlesPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	public SpawnParticlesPKT() {}
//	
//	int systemId;
//	double posX, posY, posZ;
//	double[] data;
//	//allows for 3 additional doubles to be passed
//	static final int dataLength = 7;
//	public SpawnParticlesPKT(int systemId, BlockPos pos, double...additionalParams) {
//		data = new double[dataLength];
//		data[0] = systemId;
//		data[1] = posX = pos.getX();
//		data[2] = posY = pos.getY();
//		data[3] = posZ = pos.getZ();
//		
//		for(int i = 4; i < dataLength; ++i) {
//			if(i - 4 < additionalParams.length) {
//				data[i] = additionalParams[i - 4];	
//			}else {
//				data[i] = -1;
//			}
//		}
//	}
//
//	public void toBytes(ByteBuf buf) {
//		for(int i = 0; i < data.length; ++i) {
//			buf.writeDouble(data[i]);
//		}
//	}
//	
//	public void fromBytes(ByteBuf buf) {
//		data = new double[dataLength];
//		for(int i = 0; i < dataLength; ++i) {
//			data[i] = buf.readDouble();
//		}
//	}
//
//	public static class SpawnParticlesMessageHandlerHandler<SpawnParticlesPKT, IMessage> {
//		@Override
//		public IMessage onMessage(SpawnParticlesPKT message, MessageContext ctx) {
//			int id = (int) message.data[0];
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					World w = Minecraft.getInstance().player.world;
//					ParticleSystem system = ParticleSystem.getNewParticleSystem(id);
//					system.updatePosition(w, message.data[1], message.data[2], message.data[3]);
//					system.setOptionalParams(message.data);
//					system.spawnParticles();
//				}
//			});
//			return null;
//		}
//	}

}
