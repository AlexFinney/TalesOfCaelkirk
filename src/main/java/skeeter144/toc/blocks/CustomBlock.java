package skeeter144.toc.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class CustomBlock extends Block{

	public CustomBlock(String name, Material mat)
	{
		super(mat);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setDefaultState(this.blockState.getBaseState());
	}

}
