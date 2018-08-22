package skeeter144.toc.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.entity.mob.npc.questgiver.EntityNPCInteractable;

public class QuestDialogResponseMessage implements IMessage{

	UUID questGiverUUID;
	String dialogResponse;
	public QuestDialogResponseMessage() {}
	public QuestDialogResponseMessage(UUID questGiverUUID, String dialogResponse) {
		this.questGiverUUID = questGiverUUID;
		this.dialogResponse = dialogResponse;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		questGiverUUID = new UUID(buf.readLong(), buf.readLong());
		dialogResponse = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(questGiverUUID.getMostSignificantBits());
		buf.writeLong(questGiverUUID.getLeastSignificantBits());
		ByteBufUtils.writeUTF8String(buf, dialogResponse);
	}
	
	public static class QuestDialogResponseMessageHandler implements IMessageHandler<QuestDialogResponseMessage, IMessage>{
		public IMessage onMessage(QuestDialogResponseMessage message, MessageContext ctx) {
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
				public void run() {
					EntityPlayerMP player = ctx.getServerHandler().player;
					for(Entity e : player.world.loadedEntityList) {
						if(e.getUniqueID().equals(message.questGiverUUID)) {
							EntityNPCInteractable npc = (EntityNPCInteractable)e;
							npc.runServerFunction(player.getUniqueID(), message.dialogResponse);
						}
					}
				}
			});
			return null;
		}
	}

}
