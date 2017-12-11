package skeeter144.toc.entity.tile;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import skeeter144.toc.TOCMain;

public class TileEntityHarvestedTree extends TileEntity implements ITickable{
	
	public Map<BlockPos, IBlockState> treeBlocks = new HashMap<BlockPos, IBlockState>();
	public int minSecs, maxSecs;
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("secsRemaining", secsRemaining);
		compound.setInteger("numTreeBlocks", treeBlocks.size());
		
		int counter = 0;
		for(Map.Entry<BlockPos, IBlockState> entry : treeBlocks.entrySet()) {
			String blockName = entry.getValue().getBlock().getRegistryName().toString();
			String blockMeta = entry.getValue().getBlock().getMetaFromState(entry.getValue()) + "";
			compound.setString("Block:" + counter , entry.getKey().getX() + "_" + entry.getKey().getY() + "_" + entry.getKey().getZ()  + " " + blockName + "_" + blockMeta);
			++counter;
		}
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		treeBlocks.clear();
		secsRemaining = compound.getInteger("secsRemaining");
		int numBlocks = compound.getInteger("numTreeBlocks");
		for(int i = 0; i < numBlocks; ++i) {
			String str = compound.getString("Block:" + i);
			
			String pos =  str.split(" ")[0];
			String block =  str.split(" ")[1];
			
			
			String[] posTokens = pos.split("_");
			int x = Integer.parseInt(posTokens[0]);
			int y = Integer.parseInt(posTokens[1]);
			int z = Integer.parseInt(posTokens[2]);
			
			
			String blockName = block.split("_")[0];
			int blockMeta = Integer.parseInt(block.split("_")[1]);
			
			treeBlocks.put(new BlockPos(x,y,z), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName)).getStateFromMeta(blockMeta));
		}
		
	}

	private int ticksAlive = 1;
	private int secsRemaining = -1;
	@Override
	public void update() {
		if(this.world.isRemote)
			return;

		if(secsRemaining == -1) {
			if(maxSecs - minSecs <= 0) {
				maxSecs = 2;
				minSecs = 1;
			}
			secsRemaining = TOCMain.rand.nextInt(maxSecs - minSecs) + minSecs;
		}
		
		if(ticksAlive % 20 == 0) {
			if(--secsRemaining <= 0)
				restoreTree();
		}
		++ticksAlive;	
	}
	
	private void restoreTree() {
		IBlockState state = treeBlocks.remove(this.pos);
		for(Map.Entry<BlockPos, IBlockState> entry : treeBlocks.entrySet()) {
			world.setBlockState(entry.getKey(), entry.getValue());
		}
		world.setBlockState(this.pos, state);
	}
}
