package skeeter144.toc.items;

import net.minecraft.item.Item;
import skeeter144.toc.util.Reference;

public class CustomItem extends Item{
	
	public CustomItem(Item.Properties properties, String name, int maxStackSize) {
		super(properties);
		setRegistryName(Reference.MODID, name);
	}
	
}
