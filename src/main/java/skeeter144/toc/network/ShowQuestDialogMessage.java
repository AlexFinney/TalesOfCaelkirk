package skeeter144.toc.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.client.gui.DialogGui;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.QuestManager;

public class ShowQuestDialogMessage implements IMessage{

	int questId;
	String dialogName;
	UUID uuid;
	public ShowQuestDialogMessage() {}
	public ShowQuestDialogMessage(UUID entityId, int questId, String dialogName) {
		this.uuid = entityId;
		this.questId = questId;
		this.dialogName = dialogName;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		questId = buf.readInt();
		dialogName = ByteBufUtils.readUTF8String(buf);
		uuid = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(questId);
		ByteBufUtils.writeUTF8String(buf, dialogName);
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
					
					NpcDialog dialog = QuestManager.getQuestById(message.questId).questDialogs.get(message.dialogName);
					Minecraft.getMinecraft().displayGuiScreen(new DialogGui(ent, dialog));
				}
			});
			return null;
		}
	}

}
