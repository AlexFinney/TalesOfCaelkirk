package skeeter144.toc.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityHarvestedOre;

public class BlockHarvestedOre extends BlockTileEntity<TileEntityHarvestedOre>{

	public BlockHarvestedOre(Material materialIn, String name) {
		super(materialIn, name);
		this.setBlockUnbreakable();
	}

	@Override
	public Class<TileEntityHarvestedOre> getTileEntityClass() {
		return TileEntityHarvestedOre.class;
	}

	@Override
	public TileEntityHarvestedOre createTileEntity(World world, IBlockState state) {
		return new TileEntityHarvestedOre();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
}
