package skeeter144.toc.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.Reference;

public class CustomItem extends Item{
	
	public CustomItem(String name, int maxStackSize, CreativeTabs tab) {
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setMaxStackSize(maxStackSize);
		this.setMaxDamage(-1);
		this.setCreativeTab(tab);
	}
	
}
