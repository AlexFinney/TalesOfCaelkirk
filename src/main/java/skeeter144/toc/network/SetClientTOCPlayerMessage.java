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
	
	EntityLevels pl;
	int health, mHealth, mana, mMana;
	int bytes = 0;
	public SetClientTOCPlayerMessage(TOCPlayer player) {
		pl = player.levels;
		health = player.getHealth();
		mHealth = player.getMaxHealth();
		mana = player.getMana();
		mMana = player.getMaxMana();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(pl);
			byte[] b = baos.toByteArray();
			buf.writeInt(b.length);
			buf.writeBytes(b);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {baos.close();} 
			catch (IOException e) {	e.printStackTrace();}
		}
		
		buf.writeInt(health);
		buf.writeInt(mHealth);
		buf.writeInt(mana);
		buf.writeInt(mMana);
	}
	
	
	ByteBuf buf;
	@SideOnly(Side.CLIENT)
	public void fromBytes(ByteBuf buf) {
		int playerLevelsSize = buf.readInt();
		
		byte[] playerLevelData = new byte[playerLevelsSize];
		buf.readBytes(playerLevelData);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(playerLevelData);
		ObjectInputStream ois = null;
		pl = null;
		
		try {
			ois = new ObjectInputStream(bais);
			pl = (EntityLevels)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {bais.close();} 
			catch (Exception e) {	e.printStackTrace();}
		}
		
		
		health = buf.readInt();
		mHealth =  buf.readInt();
		mana =  buf.readInt();
		mMana =  buf.readInt();
		
	}


	public static class SetClientTOCPlayerMessageHandler implements IMessageHandler<SetClientTOCPlayerMessage, IMessage>{

		@Override
		public IMessage onMessage(SetClientTOCPlayerMessage message, MessageContext ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					TOCMain.localPlayer = new TOCPlayer(Minecraft.getInstance().player, message.pl, message.health, message.mana);
				}
			});
			
			return null;
		}
	}


}
