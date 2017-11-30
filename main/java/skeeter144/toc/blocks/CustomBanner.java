package skeeter144.toc.blocks;

import net.minecraft.block.BlockBanner;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.Reference;

public class CustomBanner extends BlockBanner{

	public CustomBanner(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}
	
}
