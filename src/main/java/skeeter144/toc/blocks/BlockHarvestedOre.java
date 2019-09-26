package skeeter144.toc.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityHarvestedOre;

public class BlockHarvestedOre extends BlockTileEntity<TileEntityHarvestedOre>{

	public BlockHarvestedOre(Material materialIn, String name) {
		super(Properties.create(materialIn).hardnessAndResistance(-1), name);
	}

	@Override
	public Class<TileEntityHarvestedOre> getTileEntityClass() {
		return TileEntityHarvestedOre.class;
	}

	@Override
	public TileEntityHarvestedOre createTileEntity(World world, IBlockState state) {
		//TODO: create tileentity types
		return new TileEntityHarvestedOre(null);
	}

}
