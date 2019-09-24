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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.HUD;
import skeeter144.toc.player.Level;
import skeeter144.toc.player.EntityLevels.Levels;

public class AddLevelXpMessage implements IMessage{

	public AddLevelXpMessage() {}
	
	int xp;
	int nameLen;
	String name = "";
	public AddLevelXpMessage(String name, int xp) {
		this.name = name;
		this.xp = xp;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(name.getBytes().length);
		buf.writeBytes(name.getBytes());
		buf.writeInt(xp);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int bufLen = buf.readInt();
		byte[] bytes = new byte[bufLen];
		
		buf.readBytes(bytes);
		
		String s = new String(bytes);
		this.name = s;
		xp = buf.readInt();
	}

	public static class UpdateLevelXpMessageHandler implements IMessageHandler<AddLevelXpMessage, IMessage> {
	
		public UpdateLevelXpMessageHandler() {}
		
		@Override
		public IMessage onMessage(AddLevelXpMessage message, MessageContext ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					Collection<Level> levels = TOCMain.localPlayer.getPlayerLevels().getLevels();
					for(Level l : levels) {
						if(l.getName().equalsIgnoreCase(message.name)) {
							int prevLevel = l.getLevel();
							if(message.xp == 0) {
							}
							HUD.addNewXpMessage(l, message.xp);
							l.addExp(message.xp);
							
							if(prevLevel != l.getLevel()) {
								Snackbar bar = Snackbar.create("Congratulations! You've advanced to " + message.name + " level " + l.getLevel());
								bar.setPosition(SnackbarPosition.UP);
								bar.setDuration(200);
								SnackbarHandler.INSTANCE.showSnackbar(bar);
								World w = Minecraft.getInstance().player.world;
								EntityPlayer pl = Minecraft.getInstance().player;
								w.playSound(pl, pl.getPosition(), SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, SoundCategory.MASTER, 1, 1);
								w.playSound(pl, pl.getPosition(), SoundEvents.ENTITY_FIREWORK_TWINKLE, SoundCategory.MASTER, 1, 1);
							}
							return;
						}
					}
				}
			});
			return null;
		}
	}
}
