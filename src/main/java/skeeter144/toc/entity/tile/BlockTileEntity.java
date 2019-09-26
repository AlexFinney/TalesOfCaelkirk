package skeeter144.toc.entity.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import skeeter144.toc.util.Reference;

public abstract class BlockTileEntity<TE extends TileEntity> extends Block{

	public BlockTileEntity(Properties props, String name) {
		super(props);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}

	public abstract Class<TE> getTileEntityClass();
	
	public TE getTileEntity(IWorld world, BlockPos pos) {
		return (TE)world.getTileEntity(pos);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	public abstract TE createTileEntity(World world, IBlockState state);
	
}
