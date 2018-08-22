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
import skeeter144.toc.entity.mob.npc.questgiver.EntityNPCInteractable;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.QuestManager;

public class ShowEntityDialogMessage implements IMessage{

	String dialogName;
	UUID uuid;
	public ShowEntityDialogMessage() {}
	public ShowEntityDialogMessage(UUID entityId, String dialogName) {
		this.uuid = entityId;
		this.dialogName = dialogName;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		dialogName = ByteBufUtils.readUTF8String(buf);
		uuid = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, dialogName);
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}
	
	public static class ShowQuestDialogMessageHandler implements IMessageHandler<ShowEntityDialogMessage, IMessage>{
		public IMessage onMessage(ShowEntityDialogMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					EntityLivingBase ent = null;
					for(Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
						if(e.getUniqueID().equals(message.uuid)) {
							ent = (EntityLivingBase)e;
							break;
						}
					}
					
					if(ent == null) {
						System.out.println("\n\n*** ERRROR EntityDialog got a null Entity on Client Side *** \n\n");
						return;
					}
					
					Minecraft.getMinecraft().displayGuiScreen(new DialogGui(ent, ((EntityNPCInteractable)ent).getDialog(message.dialogName)));
				}
			});
			return null;
		}
	}

}
