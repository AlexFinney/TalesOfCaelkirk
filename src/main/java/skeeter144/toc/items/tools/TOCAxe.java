package skeeter144.toc.items.tools;

import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import skeeter144.toc.util.Reference;

public class TOCAxe extends AxeItem {

	public TOCAxe(IItemTier tier, float damage, float speed, Item.Properties builder, String name) {
		super(tier, speed, speed, builder);
		setRegistryName(Reference.MODID, name);
	}

	
}
