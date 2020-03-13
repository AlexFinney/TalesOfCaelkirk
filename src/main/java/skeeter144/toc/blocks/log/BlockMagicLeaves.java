package skeeter144.toc.blocks.log;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlockMagicLeaves extends CustomBlockLeaves{

	public BlockMagicLeaves(String name) {
		super(name);
	}
	
	@Override
	public void tick(BlockState stateIn, ServerWorld worldIn, BlockPos pos, Random rand) {
		if(worldIn.rand.nextInt(5) != 0)
			return;
		//TODO
		//TOCMain.proxy.magicLeavesParticle(stateIn, worldIn, pos, rand);
	}
	

}
