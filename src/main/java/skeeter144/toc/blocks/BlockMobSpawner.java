package skeeter144.toc.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class BlockMobSpawner extends BlockTileEntity<TileEntityMobSpawner> {

	protected static final AxisAlignedBB BB = new AxisAlignedBB(0, 0.0D, 0D, 1D, 1.0D, 1D);
	protected static final AxisAlignedBB REED_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
	public BlockMobSpawner(String name) {
		super(Properties.create(Material.ROCK).hardnessAndResistance(-1), name);
	}
	
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(player.isCreative()){
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
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityMobSpawner(null);
	}
	
}
