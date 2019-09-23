package skeeter144.toc.blocks.log;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;

public class BlockMagicLeaves extends CustomBlockLeaves{

	public BlockMagicLeaves(String name) {
		super(name);
	}
	
	@Override
	public void tick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(worldIn.rand.nextInt(5) != 0)
			return;
		
		TOCMain.proxy.magicLeavesParticle(stateIn, worldIn, pos, rand);
	}

}
