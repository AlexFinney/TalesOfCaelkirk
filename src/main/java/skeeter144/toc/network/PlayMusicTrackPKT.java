package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlayMusicTrackPKT{

	public static void encode(PlayMusicTrackPKT pkt, PacketBuffer buf) {}
	public static PlayMusicTrackPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final PlayMusicTrackPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	int id;
	public PlayMusicTrackPKT() {}
	public PlayMusicTrackPKT(int id) {
		this.id = id;
	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		id = buf.readInt();
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeInt(id);
//	}
//	
//	public static class PlayMusicTrackHandlerHandler<PlayMusicTrackMessage, IMessage>{
//
//		@Override
//		public IMessage onMessage(PlayMusicTrackMessage message, MessageContext ctx) {
//			MusicManager.playMusicTrack(message.id);
//			return null;
//		}
//		
//	}

}
