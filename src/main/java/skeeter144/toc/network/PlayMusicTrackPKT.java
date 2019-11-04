package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.sounds.music.MusicManager;

public class PlayMusicTrackPKT{

	public static void encode(PlayMusicTrackPKT pkt, PacketBuffer buf) {
		buf.writeInt(pkt.id);
	}
	
	public static PlayMusicTrackPKT decode(PacketBuffer buf) {
		PlayMusicTrackPKT pkt = new PlayMusicTrackPKT();
		pkt.id = buf.readInt();
		return null;
	}
	public static class Handler
	{
		public static void handle(final PlayMusicTrackPKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().addScheduledTask(() -> {
				MusicManager.playMusicTrack(message.id);
			});
		}
	}
	
	int id;
	public PlayMusicTrackPKT() {}
	public PlayMusicTrackPKT(int id) {
		this.id = id;
	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {

//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		
//	}
//	
//	public static class PlayMusicTrackHandlerHandler<PlayMusicTrackMessage, IMessage>{
//
//		@Override
//		public IMessage onMessage(PlayMusicTrackMessage message, MessageContext ctx) {
//		
//			return null;
//		}
//		
//	}

}
