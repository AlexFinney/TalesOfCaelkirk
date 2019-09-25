package skeeter144.toc.network;

import javax.xml.ws.handler.MessageContext;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import skeeter144.toc.particles.system.ParticleSystem;

public class SpawnParticlesMessage implements IMessage {

	public SpawnParticlesMessage() {}
	
	int systemId;
	double posX, posY, posZ;
	double[] data;
	//allows for 3 additional doubles to be passed
	static final int dataLength = 7;
	public SpawnParticlesMessage(int systemId, BlockPos pos, double...additionalParams) {
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

	public void toBytes(ByteBuf buf) {
		for(int i = 0; i < data.length; ++i) {
			buf.writeDouble(data[i]);
		}
	}
	
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
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					World w = Minecraft.getInstance().player.world;
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
