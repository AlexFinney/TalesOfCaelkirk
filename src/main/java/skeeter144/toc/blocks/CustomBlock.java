package skeeter144.toc.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class CustomBlock extends Block{

	public CustomBlock(Properties props, String name)
	{
		super(props);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setDefaultState(this.stateContainer.getBaseState());
	}
}
