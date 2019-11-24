package skeeter144.toc.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.banking.BankInventory;
import skeeter144.toc.banking.ContainerBank;
import skeeter144.toc.client.gui.BankGui;
import skeeter144.toc.util.Util;

public class OpenGUIBankPKT{

	public static void encode(OpenGUIBankPKT pkt, PacketBuffer buf) {
		buf.writeInt(pkt.items.size());
		for(int i = 0; i < pkt.items.size(); ++i)
			Util.writeItemStack(pkt.items.get(i), buf);
			
	}
	public static OpenGUIBankPKT decode(PacketBuffer buf) {
		OpenGUIBankPKT pkt = new OpenGUIBankPKT();
		int cnt = buf.readInt();
		for(int i = 0; i < cnt; ++i) {
			pkt.items.add(Util.readItemStack(buf));
		}
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final OpenGUIBankPKT message, Supplier<NetworkEvent.Context> ctx){
			Minecraft.getInstance().deferTask(new Runnable() {
				public void run() {
					BankInventory bankInv = new BankInventory(new StringTextComponent("Bank"), message.items.size());
					for(ItemStack is : message.items)
						bankInv.addItem(is);
					PlayerInventory inv =  Minecraft.getInstance().player.inventory;
					Minecraft.getInstance().displayGuiScreen(new BankGui<ContainerBank>(inv, bankInv));
				}
			});
		}
	}
	
	List<ItemStack> items = new ArrayList<ItemStack>();
	
	public OpenGUIBankPKT() {}
	public OpenGUIBankPKT(List<ItemStack> items) {
		this.items = items;
	}
}
