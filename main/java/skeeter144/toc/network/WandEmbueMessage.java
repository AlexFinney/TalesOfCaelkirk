package skeeter144.toc.network;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.magic.Spells;

public class WandEmbueMessage implements IMessage{

	public WandEmbueMessage() {}
	
	String playerUUID;
	int wandSlot, embueSpellId, uuidLength;
	public WandEmbueMessage(EntityPlayer player, int wandSlot, int embueId) {
		playerUUID = player.getUniqueID().toString();
		this.wandSlot = wandSlot;
		this.embueSpellId = embueId;
		this.uuidLength = playerUUID.length();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(uuidLength);
		for(int i = 0; i < uuidLength; ++i) {
			buf.writeChar(playerUUID.charAt(i));
		}
		buf.writeInt(wandSlot);
		buf.writeInt(embueSpellId);
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		uuidLength = buf.readInt();
		for(int i = 0; i < uuidLength; ++i) {
			playerUUID += buf.readChar();
		}
		wandSlot = buf.readInt();
		embueSpellId = buf.readInt();
	}

	public static class WandEmbueMessageHandler implements IMessageHandler<WandEmbueMessage, IMessage> {
	
		public WandEmbueMessageHandler() {}
		
		@Override
		public IMessage onMessage(WandEmbueMessage message, MessageContext ctx) {
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
				public void run() {
					EntityPlayer player =  ctx.getServerHandler().player;
					int hand = message.wandSlot;
					
					DimensionManager.getWorld(0);
					
					ItemStack stack = (hand == 1 ? player.getHeldItemMainhand() : player.getHeldItemOffhand());
					
					NBTTagCompound nbt = null;
					if(stack.getTagCompound() == null) {
						nbt = new NBTTagCompound();
						stack.setTagCompound(nbt);
					}
					nbt = stack.getTagCompound();
					nbt.setInteger("embued_spell", message.embueSpellId);
					
					stack.setStackDisplayName("Wand: " + Spells.getSpell(message.embueSpellId).getName());
				}
			});
			return null;
		}
	}
}
