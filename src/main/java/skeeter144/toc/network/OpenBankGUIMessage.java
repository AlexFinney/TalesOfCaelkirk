package skeeter144.toc.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.banking.BankInventory;
import skeeter144.toc.client.gui.BankGui;

public class OpenBankGUIMessage implements IMessage{

	List<ItemStack> items = new ArrayList<ItemStack>();
	
	public OpenBankGUIMessage() {}
	public OpenBankGUIMessage(List<ItemStack> items) {
		this.items = items;
	}
	
	
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(items.size());
		for(int i = 0; i < items.size(); ++i) {
			ByteBufUtils.writeItemStack(buf, items.get(i));
		}
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int bankSize = buf.readInt();
		for(int i = 0; i < bankSize; ++i) {
			items.add(ByteBufUtils.readItemStack(buf));
		}
	}
	
	public static class OpenBankGUIMessageHandler implements IMessageHandler<OpenBankGUIMessage, IMessage>{
		public IMessage onMessage(OpenBankGUIMessage message,
				MessageContext ctx) {
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					BankInventory bankInv = new BankInventory("Bank", true, message.items.size());
					for(ItemStack is : message.items)
						bankInv.addItem(is);
					Minecraft.getInstance().displayGuiScreen(new GuiChest(Minecraft.getInstance().player.inventory, bankInv));
				}
			});
			return null;
		}
		
	}

}
