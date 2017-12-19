package skeeter144.toc.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;
import skeeter144.toc.proxy.ClientProxy;

public class SpawnBlockedMessage implements IMessage {

	public SpawnBlockedMessage() {}
	
	long msb, lsb;
	public SpawnBlockedMessage(Entity e) {
		msb = e.getUniqueID().getMostSignificantBits();
		lsb = e.getUniqueID().getLeastSignificantBits();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		msb = buf.readLong();
		lsb = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(msb);
		buf.writeLong(lsb);
	}
	
	public static class SpawnBlockedMessageHandler implements IMessageHandler<SpawnBlockedMessage, IMessage> {

		@Override
		public IMessage onMessage(SpawnBlockedMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					UUID uuid = new UUID(message.msb, message.lsb);
					for(Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
						if(e.getUniqueID().equals(uuid)) {
							ClientProxy.displayParticle(e, "Blocked!");
						}
					}
				}
			});
			
			return null;
		}
	}

}
