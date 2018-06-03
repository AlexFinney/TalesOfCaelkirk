package skeeter144.toc.blocks;

import java.util.Random;

import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.Guis;
import skeeter144.toc.entity.tile.BlockTileEntity;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class BlockMobSpawner extends BlockTileEntity<TileEntityMobSpawner> {

	protected static final AxisAlignedBB BB = new AxisAlignedBB(0, 0.0D, 0D, 1D, 1.0D, 1D);
	protected static final AxisAlignedBB REED_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
	public BlockMobSpawner(String name) {
		super(Material.ROCK, name);
		this.setHardness(-1);
	}
	
	@Override
	public boolean isCollidable() {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return true;
	}

	@Override
	public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.capabilities.isCreativeMode)
			playerIn.openGui(TOCMain.instance, Guis.MOB_SPAWNER_GUI, worldIn, pos.getX(),
					pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		super.onBlockClicked(worldIn, pos, playerIn);
	}
	
	@Override
	public Class<TileEntityMobSpawner> getTileEntityClass() {
		return TileEntityMobSpawner.class;
	}

	@Override
	public TileEntityMobSpawner createTileEntity(World world, IBlockState state) {
		return new TileEntityMobSpawner();
	}

}
