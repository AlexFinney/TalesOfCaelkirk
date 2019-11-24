package skeeter144.toc.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockReader;
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
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityHarvestedOre(TOCBlocks.te_harvested_ore);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
