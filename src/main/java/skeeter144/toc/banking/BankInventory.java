package skeeter144.toc.banking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class BankInventory extends InventoryBasic{

	public BankInventory(String title, boolean customName, int slotCount) {
		super(title, customName, slotCount);
	}
	
	public BankInventory(ITextComponent title, int slotCount) {
		super(title, slotCount);
	}
	
	
	public static class SerializableBankInventory implements Serializable{
		private static final long serialVersionUID = -8104266333309720974L;
		
		int slots;
		String invName;
		List<SerializableItemStack> items = new ArrayList<SerializableItemStack>();
		
		public SerializableBankInventory(BankInventory inv) {
			invName = inv.getName();
			slots = inv.getSizeInventory();
			
			for(int i = 0; i < inv.getSizeInventory(); ++i) {
				items.add(new SerializableItemStack(inv.getStackInSlot(i)));
			}
		}
		
		
		public BankInventory toBankInventory() {
			BankInventory bi = new BankInventory(invName, true, slots);
			
			for(int i = 0; i < this.slots; ++i)
				bi.setInventorySlotContents(i, items.get(i).toItemStack());
			
			return bi;
		}
		
	}
	
	public static class SerializableItemStack implements Serializable{
		private static final long serialVersionUID = -2334185541927257463L;
		
		byte[] bytes;
		String name;
		int count;
		
		public SerializableItemStack(ItemStack is) {
			ByteBuf buf = Unpooled.buffer();
			ByteBufUtils.writeItemStack(buf, is);
			bytes = buf.array();
			
			name = is.getDisplayName();
			count = is.getCount();
		}
		
		
		public ItemStack toItemStack() {
			ByteBuf buf = Unpooled.buffer(bytes.length);
			buf.writeBytes(bytes);
			return ByteBufUtils.readItemStack(buf);
		}
	}
	
}
