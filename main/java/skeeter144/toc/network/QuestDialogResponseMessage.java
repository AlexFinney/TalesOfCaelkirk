package skeeter144.toc.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;

public class QuestDialogResponseMessage implements IMessage{

	UUID questGiverUUID;
	int questId, dialogId, responseId;
	public QuestDialogResponseMessage() {}
	public QuestDialogResponseMessage(UUID questGiverUUID, int questId, int dialogId, int responseId) {
		this.questId = questId;
		this.dialogId = dialogId;
		this.responseId = responseId;
		this.questGiverUUID = questGiverUUID; 
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		questId = buf.readInt();
		dialogId = buf.readInt();
		responseId = buf.readInt(); 
		questGiverUUID = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(questId);
		buf.writeInt(dialogId);
		buf.writeInt(responseId);
		buf.writeLong(questGiverUUID.getMostSignificantBits());
		buf.writeLong(questGiverUUID.getLeastSignificantBits());
	}
	
	public static class QuestDialogResponseMessageHandler implements IMessageHandler<QuestDialogResponseMessage, IMessage>{
		public IMessage onMessage(QuestDialogResponseMessage message, MessageContext ctx) {
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
				public void run() {
					EntityPlayerMP player = ctx.getServerHandler().player;
					Quest q = QuestManager.getQuestById(message.questId);
					if(q != null) {
						}
						q.handleDialogResponse(player, message.questGiverUUID, message.dialogId, message.responseId);
					}
			});
			return null;
		}
	}

}
