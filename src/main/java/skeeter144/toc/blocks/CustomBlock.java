package skeeter144.toc.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skeeter144.toc.util.Reference;

public class CustomBlock extends Block{

	public CustomBlock(Properties props, String name)
	{
		super(props);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setDefaultState(this.stateContainer.getBaseState());
	}
}
