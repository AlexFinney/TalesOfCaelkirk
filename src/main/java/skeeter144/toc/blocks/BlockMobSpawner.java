package skeeter144.toc.blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skeeter144.toc.client.gui.MobSpawnerGUI;
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
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(player.isCreative() && worldIn.isRemote){
			Minecraft.getInstance().displayGuiScreen(new MobSpawnerGUI((TileEntityMobSpawner)worldIn.getTileEntity(pos)));
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
		return new TileEntityMobSpawner();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
}
