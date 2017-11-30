package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.particles.system.ParticleSystem;

public class SpawnParticlesMessage implements IMessage {

	public SpawnParticlesMessage() {}
	
	int systemId;
	double posX, posY, posZ;
	double[] data;
	//allows for 3 additional doubles to be passed
	static final int dataLength = 7;
	public SpawnParticlesMessage(int systemId, double posX, double posY, double posZ, double...additionalParams) {
		data = new double[dataLength];
		data[0] = systemId;
		data[1] = posX;
		data[2] = posY;
		data[3] = posZ;
		
		for(int i = 4; i < dataLength; ++i) {
			if(i - 4 < additionalParams.length) {
				data[i] = additionalParams[i - 4];	
			}else {
				data[i] = -1;
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		for(int i = 0; i < data.length; ++i) {
			buf.writeDouble(data[i]);
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		data = new double[dataLength];
		for(int i = 0; i < dataLength; ++i) {
			data[i] = buf.readDouble();
		}
	}

	public static class SpawnParticlesMessageHandler implements IMessageHandler<SpawnParticlesMessage, IMessage> {
		@Override
		public IMessage onMessage(SpawnParticlesMessage message, MessageContext ctx) {
			int id = (int) message.data[0];
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					World w = Minecraft.getMinecraft().player.world;
					ParticleSystem system = ParticleSystem.getNewParticleSystem(id);
					system.updatePosition(w, message.data[1], message.data[2], message.data[3]);
					system.setOptionalParams(message.data);
					system.spawnParticles();
				}
			});
			return null;
		}
	}

}
