package skeeter144.toc.items.misc;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class ItemGlassVial extends Item{

	public ItemGlassVial(String name) {
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setUnlocalizedName(name);
		this.maxStackSize = 1;
	}
	
}
