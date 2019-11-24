package skeeter144.toc.entity.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;
import skeeter144.toc.util.Reference;

public abstract class BlockTileEntity<TE extends TileEntity> extends Block{

	public BlockTileEntity(Properties props, String name) {
		super(props);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}

	public abstract Class<TE> getTileEntityClass();
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return super.createTileEntity(state, world);
	}
}
