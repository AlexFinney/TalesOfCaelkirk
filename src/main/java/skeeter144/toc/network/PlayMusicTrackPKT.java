package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlayMusicTrackMessage{

	public static void encode(PlayMusicTrackMessage pkt, PacketBuffer buf) {}
	public static PlayMusicTrackMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final PlayMusicTrackMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	int id;
	public PlayMusicTrackMessage() {}
	public PlayMusicTrackMessage(int id) {
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
