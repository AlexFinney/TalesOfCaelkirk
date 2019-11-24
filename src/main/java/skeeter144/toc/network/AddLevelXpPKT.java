package skeeter144.toc.network;

import java.util.Collection;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.HUD;
import skeeter144.toc.player.Level;

public class AddLevelXpPKT {

	public AddLevelXpPKT() {}

	public int xp;
	public int nameLen;
	public String name = "";

	public AddLevelXpPKT(String name, int xp) {
		this.name = name;
		this.xp = xp;
	}

	public static void encode(AddLevelXpPKT pkt, PacketBuffer buf) {
		buf.writeInt(pkt.name.getBytes().length);
		buf.writeBytes(pkt.name.getBytes());
		buf.writeInt(pkt.xp);
	}

	public static AddLevelXpPKT decode(PacketBuffer buf) {
		AddLevelXpPKT pkt = new AddLevelXpPKT();
		int bufLen = buf.readInt();
		byte[] bytes = new byte[bufLen];

		buf.readBytes(bytes);

		String s = new String(bytes);
		pkt.name = s;
		pkt.xp = buf.readInt();
		return pkt;
	}

	public static class Handler {
		public static void handle(final AddLevelXpPKT message, Supplier<NetworkEvent.Context> ctx) {
			Minecraft.getInstance().deferTask(new Runnable() {
				public void run() {
					Collection<Level> levels = TOCMain.localPlayer.getPlayerLevels().getLevels();
					for (Level l : levels) {
						if (l.getName().equalsIgnoreCase(message.name)) {
							int prevLevel = l.getLevel();
							if (message.xp == 0) {
							}
							HUD.addNewXpMessage(l, message.xp);
							l.addExp(message.xp);

							if (prevLevel != l.getLevel()) {
//								Snackbar bar = Snackbar.create("Congratulations! You've advanced to " + message.name + " level " + l.getLevel());
//								bar.setPosition(SnackbarPosition.UP);
//								bar.setDuration(200);
//								SnackbarHandler.INSTANCE.showSnackbar(bar);
								World w = Minecraft.getInstance().player.world;
								PlayerEntity pl = Minecraft.getInstance().player;
								w.playSound(pl, pl.getPosition(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST,
										SoundCategory.MASTER, 1, 1);
								w.playSound(pl, pl.getPosition(), SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE,
										SoundCategory.MASTER, 1, 1);
							}
							return;
						}
					}
				}
			});
		}

	}
}
