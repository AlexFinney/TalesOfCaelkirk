package skeeter144.toc.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.client.gui.DialogGui;

public class ShowQuestDialogMessage implements IMessage{

	int questId, dialogId;
	UUID uuid;
	public ShowQuestDialogMessage() {}
	public ShowQuestDialogMessage(UUID entityId, int questId, int dialogId) {
		this.uuid = entityId;
		this.questId = questId;
		this.dialogId = dialogId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		questId = buf.readInt();
		dialogId = buf.readInt();
		uuid = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(questId);
		buf.writeInt(dialogId);
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}
	
	public static class ShoqQuestDialogMessageHandler implements IMessageHandler<ShowQuestDialogMessage, IMessage>{
		public IMessage onMessage(ShowQuestDialogMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					EntityLivingBase ent = null;
					for(Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
						if(e.getUniqueID().equals(message.uuid)) {
							ent = (EntityLivingBase)e;
							break;
						}
					}
					Minecraft.getMinecraft().displayGuiScreen(new DialogGui(ent, message.questId, message.dialogId));
				}
			});
			return null;
		}
	}

}
