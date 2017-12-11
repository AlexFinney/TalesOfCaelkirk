package skeeter144.toc.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityHarvestedTree;

public class BlockHarvestedTree extends BlockTileEntity<TileEntityHarvestedTree> {

	public BlockHarvestedTree(Material materialIn, String name) {
		super(materialIn, name);
		this.setBlockUnbreakable();
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

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}


	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
		return false;
	}

	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_,
			EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

}
