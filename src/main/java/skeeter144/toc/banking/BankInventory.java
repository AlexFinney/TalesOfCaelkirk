package skeeter144.toc.banking;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.text.ITextComponent;

public class BankInventory extends InventoryBasic{

	public BankInventory(String title, boolean customName, int slotCount) {
		super(title, customName, slotCount);
	}
	
	public BankInventory(ITextComponent title, int slotCount) {
		super(title, slotCount);
	}

}
