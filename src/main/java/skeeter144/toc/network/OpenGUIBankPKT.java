package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class OpenBankGUIMessage{

	public static void encode(OpenBankGUIMessage pkt, PacketBuffer buf) {}
	public static OpenBankGUIMessage decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final OpenBankGUIMessage message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	List<ItemStack> items = new ArrayList<ItemStack>();
//	
//	public OpenBankGUIMessage() {}
//	public OpenBankGUIMessage(List<ItemStack> items) {
//		this.items = items;
//	}
//	
//	
//	
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeInt(items.size());
//		for(int i = 0; i < items.size(); ++i) {
//			ByteBufUtils.writeItemStack(buf, items.get(i));
//		}
//		
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		int bankSize = buf.readInt();
//		for(int i = 0; i < bankSize; ++i) {
//			items.add(ByteBufUtils.readItemStack(buf));
//		}
//	}
//	
//	public static class OpenBankGUIMessageHandlerHandler<OpenBankGUIMessage, IMessage>{
//		public IMessage onMessage(OpenBankGUIMessage message,
//				MessageContext ctx) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				public void run() {
//					BankInventory bankInv = new BankInventory("Bank", true, message.items.size());
//					for(ItemStack is : message.items)
//						bankInv.addItem(is);
//					Minecraft.getInstance().displayGuiScreen(new GuiChest(Minecraft.getInstance().player.inventory, bankInv));
//				}
//			});
//			return null;
//		}
//		
//	}

}
