package skeeter144.toc.network;

import java.util.Collection;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.snackbar.Snackbar;
import net.ilexiconn.llibrary.server.snackbar.SnackbarHandler;
import net.ilexiconn.llibrary.server.snackbar.SnackbarPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.HUD;
import skeeter144.toc.player.Level;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.TOCUtils;

public class SendIconUpdateMessage implements IMessage{

	public SendIconUpdateMessage() {}
	
	int x, y, z, dim;
	String name, rsLocDomain, rsLocPath;
	boolean shouldRemove;
	public SendIconUpdateMessage(String name, int x, int y, int z, int dim, String rsLocDomain, String rsLocPath) {
		this(name, x, y, z, dim, rsLocDomain, rsLocPath, false);
	}
	
	public SendIconUpdateMessage(String name, int x, int y, int z, int dim, String rsLocDomain, String rsLocPath, boolean shouldRemove) {
		this.name = name;
		this.rsLocDomain = rsLocDomain;
		this.rsLocPath = rsLocPath;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dim  = dim;
		this.shouldRemove = shouldRemove;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, name);
		ByteBufUtils.writeUTF8String(buf, rsLocDomain);
		ByteBufUtils.writeUTF8String(buf, rsLocPath);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(dim);
		buf.writeBoolean(shouldRemove);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		name = ByteBufUtils.readUTF8String(buf);
		rsLocDomain = ByteBufUtils.readUTF8String(buf);
		rsLocPath = ByteBufUtils.readUTF8String(buf);
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		dim = buf.readInt();
		shouldRemove = buf.readBoolean();
	}

	public static class SendIconUpdateMessageHandler implements IMessageHandler<SendIconUpdateMessage, IMessage> {
	
		public SendIconUpdateMessageHandler() {}
		
		@Override
		public IMessage onMessage(SendIconUpdateMessage msg, MessageContext ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					if(!msg.shouldRemove)
						TOCUtils.addIconMarkerToMap(msg.name, new ResourceLocation(msg.rsLocDomain, msg.rsLocPath), new BlockPos(msg.x, msg.y, msg.z), msg.dim);
					else
						TOCUtils.removeIconMarkerFromMap(msg.name, new BlockPos(msg.x, msg.y, msg.z), msg.dim);
				}	
			});
			return null;
		}
	}
}
