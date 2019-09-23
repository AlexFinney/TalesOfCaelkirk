package skeeter144.toc.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
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
	public TileEntityHarvestedTree createTileEntity(World world, IBlockState state) {
		return new TileEntityHarvestedTree();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}


	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
		return false;
	}

	public boolean isReplaceable(IWorld worldIn, BlockPos pos) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

}
