package skeeter144.toc.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class BlockMobSpawner extends BlockTileEntity<TileEntityMobSpawner> {

	protected static final AxisAlignedBB BB = new AxisAlignedBB(0, 0.0D, 0D, 1D, 1.0D, 1D);
	protected static final AxisAlignedBB REED_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
	public BlockMobSpawner(String name) {
		super(Properties.create(Material.ROCK).hardnessAndResistance(-1), name);
	}
	
	@Override
	public boolean isCollidable() {
		return true;
	}
	
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.isCreative()){
			//TODO: show gui
			//playerIn.show(TOCMain.instance, Guis.MOB_SPAWNER_GUI, worldIn, pos.getX(),
			//		pos.getY(), pos.getZ());
		}

		return true;
	}
	
	@Override
	public Class<TileEntityMobSpawner> getTileEntityClass() {
		return TileEntityMobSpawner.class;
	}

	@Override
	public TileEntityMobSpawner createTileEntity(World world, IBlockState state) {
		return new TileEntityMobSpawner(null);
	}

}
