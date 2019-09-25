package skeeter144.toc.entity.tile;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.TOCBlocks;

public class TileEntityHarvestedTree extends TileEntity implements ITickable{
	
	public TileEntityHarvestedTree() {
		super(TOCBlocks.te_harvested_tree);
	}
	
	public TileEntityHarvestedTree(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public Map<BlockPos, IBlockState> treeBlocks = new HashMap<BlockPos, IBlockState>();
	public int minSecs, maxSecs;
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInt("secsRemaining", secsRemaining);
		compound.setInt("numTreeBlocks", treeBlocks.size());
		
		int counter = 0;
		for(Map.Entry<BlockPos, IBlockState> entry : treeBlocks.entrySet()) {
			String blockName = entry.getValue().getBlock().getRegistryName().toString();
			compound.setString("Block:" + counter , entry.getKey().getX() + "_" + entry.getKey().getY() + "_" + entry.getKey().getZ()  + " " + blockName);
			++counter;
		}
		
		return compound;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		treeBlocks.clear();
		secsRemaining = compound.getInt("secsRemaining");
		int numBlocks = compound.getInt("numTreeBlocks");
		for(int i = 0; i < numBlocks; ++i) {
			String str = compound.getString("Block:" + i);
			
			String pos =  str.split(" ")[0];
			String block =  str.split(" ")[1];
			
			
			String[] posTokens = pos.split("_");
			int x = Integer.parseInt(posTokens[0]);
			int y = Integer.parseInt(posTokens[1]);
			int z = Integer.parseInt(posTokens[2]);
			
			
			String blockName = block;
			
			treeBlocks.put(new BlockPos(x,y,z), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName)).getDefaultState());
		}
		
	}

	private int ticksAlive = 1;
	private int secsRemaining = -1;
	@Override
	public void tick() {
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
