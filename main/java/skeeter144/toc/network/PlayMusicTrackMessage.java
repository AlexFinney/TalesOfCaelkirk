package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.sounds.music.MusicManager;

public class PlayMusicTrackMessage implements IMessage{

	int id;
	public PlayMusicTrackMessage() {}
	public PlayMusicTrackMessage(int id) {
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
	}
	
	public static class PlayMusicTrackHandler implements IMessageHandler<PlayMusicTrackMessage, IMessage>{

		@Override
		public IMessage onMessage(PlayMusicTrackMessage message, MessageContext ctx) {
			MusicManager.playMusicTrack(message.id);
			return null;
		}
		
	}

}
