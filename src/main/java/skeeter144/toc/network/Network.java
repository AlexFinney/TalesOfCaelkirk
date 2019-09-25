package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import skeeter144.toc.util.Reference;

public class Network {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannelWrapper INSTANCE = SimpleChannelWrapper.create(); 
	
	private static int id = 0;
	public static int getNextId() {
		return id++;
	}
	
	public static class SimpleChannelWrapper{
		SimpleChannel channel;
		private SimpleChannelWrapper(SimpleChannel channel) {
			this.channel = channel;
		}
		
		public static SimpleChannelWrapper create() {
			SimpleChannel channel = NetworkRegistry.newSimpleChannel(
				    new ResourceLocation(Reference.MODID, "main"),
				    () -> PROTOCOL_VERSION,
				    PROTOCOL_VERSION::equals,
				    PROTOCOL_VERSION::equals
				);
			return new SimpleChannelWrapper(channel);
		}
		
		public <MSG> void sendToAll(MSG msg) {
			channel.send(PacketDistributor.ALL.noArg(), msg);
		}
		public <MSG> void sendToAllAround(MSG msg, Chunk chunk) {
			channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
		}

		public <MSG> void sendTo(MSG msg, EntityPlayerMP player) {
			channel.sendTo(msg, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
		
		public <MSG> void sendToServer(MSG msg) {
			channel.sendToServer(msg);
		}
	}
	
}
