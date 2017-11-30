package skeeter144.toc.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skeeter144.toc.TOCMain;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.TOCPlayer;

public class SetClientTOCPlayerMessage implements IMessage{

	public SetClientTOCPlayerMessage() {}
	
	TOCPlayer player;
	int bytes = 0;
	public SetClientTOCPlayerMessage(TOCPlayer player) {
		this.player = player;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(player.getPlayerLevels());
			byte[] b = baos.toByteArray();
			buf.writeInt(b.length);
			buf.writeBytes(b);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {baos.close();} 
			catch (IOException e) {	e.printStackTrace();}
		}
		
		buf.writeInt(player.getHealth());
		buf.writeInt(player.getMaxHealth());
		buf.writeInt(player.getMana());
		buf.writeInt(player.getMaxMana());
	}
	
	
	ByteBuf buf;
	@SideOnly(Side.CLIENT)
	public void fromBytes(ByteBuf buf) {
		this.buf = buf;
	}


	public static class SetClientTOCPlayerMessageHandler implements IMessageHandler<SetClientTOCPlayerMessage, IMessage>{

		@Override
		public IMessage onMessage(SetClientTOCPlayerMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				public void run() {
					int playerLevelsSize = message.buf.readInt();
					
					byte[] playerLevelData = new byte[playerLevelsSize];
					message.buf.readBytes(playerLevelData);
					
					ByteArrayInputStream bais = new ByteArrayInputStream(playerLevelData);
					ObjectInputStream ois = null;
					EntityLevels pl = null;
					
					try {
						ois = new ObjectInputStream(bais);
						pl = (EntityLevels)ois.readObject();
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try {bais.close();} 
						catch (Exception e) {	e.printStackTrace();}
					}
					
					
					int plHealth = message.buf.readInt();
					int mHealth =  message.buf.readInt();
					int mana =  message.buf.readInt();
					int maxmana =  message.buf.readInt();
					UUID uuid = Minecraft.getMinecraft().player.getPersistentID();
					TOCMain.localPlayer = new TOCPlayer(Minecraft.getMinecraft().player, pl, plHealth, mHealth, mana, maxmana);
				}
			});
			
			return null;
		}
	}


}
