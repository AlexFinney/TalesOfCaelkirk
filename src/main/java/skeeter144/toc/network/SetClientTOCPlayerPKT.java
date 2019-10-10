package skeeter144.toc.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.TOCPlayer;

public class SetClientTOCPlayerPKT {

	public SetClientTOCPlayerPKT() {}

	EntityLevels pl;
	int health, mHealth, mana, mMana;
	public SetClientTOCPlayerPKT(TOCPlayer player) {
		pl = player.levels;
		health = player.getHealth();
		mHealth = player.getMaxHealth();
		mana = player.getMana();
		mMana = player.getMaxMana();
	}

	public static void encode(SetClientTOCPlayerPKT pkt, PacketBuffer buf) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(pkt.pl);
			byte[] b = baos.toByteArray();
			buf.writeInt(b.length);
			buf.writeBytes(b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		buf.writeInt(pkt.health);
		buf.writeInt(pkt.mHealth);
		buf.writeInt(pkt.mana);
		buf.writeInt(pkt.mMana);
	}

	public static SetClientTOCPlayerPKT decode(PacketBuffer buf) {
		SetClientTOCPlayerPKT msg = new SetClientTOCPlayerPKT();

		int playerLevelsSize = buf.readInt();

		byte[] playerLevelData = new byte[playerLevelsSize];
		buf.readBytes(playerLevelData);

		ByteArrayInputStream bais = new ByteArrayInputStream(playerLevelData);
		ObjectInputStream ois = null;
		msg.pl = null;

		try {
			ois = new ObjectInputStream(bais);
			msg.pl = (EntityLevels) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bais.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		msg.health = buf.readInt();
		msg.mHealth = buf.readInt();
		msg.mana = buf.readInt();
		msg.mMana = buf.readInt();

		return msg;
	}

	public static class Handler {
		public static void handle(final SetClientTOCPlayerPKT message, Supplier<NetworkEvent.Context> ctx) {
			TOCMain.localPlayer = new TOCPlayer(Minecraft.getInstance().player, message.pl, message.health, message.mana);
		}
	}

}
