package skeeter144.toc.blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityHarvestedTree;

public class BlockHarvestedTree extends BlockTileEntity<TileEntityHarvestedTree> {

	public BlockHarvestedTree(Material materialIn, String name) {
		super(Properties.create(materialIn), name);
	}

	@Override
	public Class getTileEntityClass() {
		return TileEntityHarvestedTree.class;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityHarvestedTree(TOCBlocks.te_harvested_tree);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}


	public boolean canCollideCheck(BlockState state, boolean hitIfLiquid) {
		return false;
	}

	public boolean isReplaceable(IWorld worldIn, BlockPos pos) {
		return false;
	}

	public boolean isFullCube(BlockState state) {
		return false;
	}

}
