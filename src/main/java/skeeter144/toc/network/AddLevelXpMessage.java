package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class AddLevelXpMessage{
	
	public static void encode(AddLevelXpMessage pkt, PacketBuffer buf) {}
	public static AddLevelXpMessage decode(PacketBuffer buf) {return null;}
	
	public static class Handler
	{
		public static void handle(final AddLevelXpMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
//
//	public AddLevelXpMessage() {}
//	
//	int xp;
//	int nameLen;
//	String name = "";
//	public AddLevelXpMessage(String name, int xp) {
//		this.name = name;
//		this.xp = xp;
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeInt(name.getBytes().length);
//		buf.writeBytes(name.getBytes());
//		buf.writeInt(xp);
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		int bufLen = buf.readInt();
//		byte[] bytes = new byte[bufLen];
//		
//		buf.readBytes(bytes);
//		
//		String s = new String(bytes);
//		this.name = s;
//		xp = buf.readInt();
//	}
//
//	public static class UpdateLevelXpMessageHandlerHandler<AddLevelXpMessage, IMessage> {
//	
//		public UpdateLevelXpMessageHandler() {}
//		
//		@Override
//		public IMessage onMessage(AddLevelXpMessage message, MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					Collection<Level> levels = TOCMain.localPlayer.getPlayerLevels().getLevels();
//					for(Level l : levels) {
//						if(l.getName().equalsIgnoreCase(message.name)) {
//							int prevLevel = l.getLevel();
//							if(message.xp == 0) {
//							}
//							HUD.addNewXpMessage(l, message.xp);
//							l.addExp(message.xp);
//							
//							if(prevLevel != l.getLevel()) {
//								Snackbar bar = Snackbar.create("Congratulations! You've advanced to " + message.name + " level " + l.getLevel());
//								bar.setPosition(SnackbarPosition.UP);
//								bar.setDuration(200);
//								SnackbarHandler.INSTANCE.showSnackbar(bar);
//								World w = Minecraft.getInstance().player.world;
//								EntityPlayer pl = Minecraft.getInstance().player;
//								w.playSound(pl, pl.getPosition(), SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, SoundCategory.MASTER, 1, 1);
//								w.playSound(pl, pl.getPosition(), SoundEvents.ENTITY_FIREWORK_TWINKLE, SoundCategory.MASTER, 1, 1);
//							}
//							return;
//						}
//					}
//				}
//			});
//			return null;
//		}
//	}
}
