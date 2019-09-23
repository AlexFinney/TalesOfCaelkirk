package skeeter144.toc.banking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class BankInventory extends InventoryBasic{

	public BankInventory(ITextComponent title, int slotCount) {
		super(title, slotCount);
	}
	
	
	public static class SerializableBankInventory implements Serializable{
		private static final long serialVersionUID = -8104266333309720974L;
		
		int slots;
		ITextComponent invName;
		List<SerializableItemStack> items = new ArrayList<SerializableItemStack>();
		
		public SerializableBankInventory(BankInventory inv) {
			invName = inv.getName();
			slots = inv.getSizeInventory();
			
			for(int i = 0; i < inv.getSizeInventory(); ++i) {
				items.add(new SerializableItemStack(inv.getStackInSlot(i)));
			}
		}
		
		
		public BankInventory toBankInventory() {
			BankInventory bi = new BankInventory(invName, slots);
			
			for(int i = 0; i < this.slots; ++i)
				bi.setInventorySlotContents(i, items.get(i).toItemStack());
			
			return bi;
		}
		
	}
	
	public static class SerializableItemStack implements Serializable{
		private static final long serialVersionUID = -2334185541927257463L;
		
		byte[] bytes;
		ITextComponent name;
		int count;
		
		public SerializableItemStack(ItemStack is) {
			ByteBuf buf = Unpooled.buffer();
			PacketBuffer pBuf = new PacketBuffer(buf);
			pBuf.writeItemStack(is);
			bytes = buf.array();
			
			name = is.getDisplayName();
			count = is.getCount();
		}
		
		
		public ItemStack toItemStack() {
			ByteBuf buf = Unpooled.buffer(bytes.length);
			PacketBuffer pBuf = new PacketBuffer(buf);
			pBuf.writeBytes(bytes);
			return pBuf.readItemStack();
		}
	}
	
}
