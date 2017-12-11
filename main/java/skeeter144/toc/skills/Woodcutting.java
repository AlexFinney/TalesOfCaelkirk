package skeeter144.toc.skills;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Woodcutting {

	public static float getChopChanceForWood(BlockLog wood) {
		return 1f;
	}
	
	public static int getMinRespawnSecsForWood(BlockLog wood) {
		return 5;
	}
	
	public static int getMaxRespawnSecsForWood(BlockLog wood) {
		return 10;
	}
	
	public static int getExpForWood(BlockLog wood) {
		return 10;
	}
	
	public static Item getHarvestItemForWood(BlockLog wood) {
		return Items.DIAMOND;
	}
	
	static final int maxHorizLeafDist = 5;
	static final int maxHorizLogDist = 1;
	public static Map<BlockPos, IBlockState> getTreeFromLog(World w, BlockPos pos){
		Block b = w.getBlockState(pos).getBlock();
		Map<BlockPos, IBlockState> blocks = new HashMap<BlockPos, IBlockState>(); 
		
		blocks.put(pos, w.getBlockState(pos));
		
		if(b instanceof BlockLog || b instanceof BlockLeaves) {
			Block bl = w.getBlockState(pos.add(-1, 0, 0)).getBlock();
			Block br = w.getBlockState(pos.add(1, 0, 0)).getBlock();
			Block bu = w.getBlockState(pos.add(0, 1, 0)).getBlock();
			Block bd = w.getBlockState(pos.add(0, -1, 0)).getBlock();
			Block bf = w.getBlockState(pos.add(0, 0, 1)).getBlock();
			Block bb = w.getBlockState(pos.add(0, 0, -1)).getBlock();
			
			if(bl instanceof BlockLog || bl instanceof BlockLeaves) 
				getTreeFromLog(w, pos.add(-1, 0, 0), blocks, pos);
			if(br instanceof BlockLog || br instanceof BlockLeaves) 
				getTreeFromLog(w, pos.add(1, 0, 0), blocks, pos);
			if(bu instanceof BlockLog || bu instanceof BlockLeaves) 
				getTreeFromLog(w, pos.add(0, 1, 0), blocks, pos);
			if(bd instanceof BlockLog || bd instanceof BlockLeaves) 
				getTreeFromLog(w, pos.add(0, -1, 0), blocks, pos);
			if(bf instanceof BlockLog || bf instanceof BlockLeaves) 
				getTreeFromLog(w, pos.add(0, 0, 1), blocks, pos);
			if(bb instanceof BlockLog || bb instanceof BlockLeaves) 
				getTreeFromLog(w, pos.add(0, 0, -1), blocks, pos);
		}
		
		return blocks;
	}

	private static void getTreeFromLog(World w, BlockPos pos, Map<BlockPos, IBlockState> blocks, BlockPos origin){
		if(blocks.containsKey(pos))
			return;
		
		if(w.getBlockState(pos).getBlock() instanceof BlockLog && (pos.getX() != origin.getX() || pos.getZ() != origin.getZ()))
				return;
		
		blocks.put(pos, w.getBlockState(pos));
		
		
		BlockPos pl = pos.add(-1, 0, 0);
		BlockPos pr = pos.add(1, 0, 0);
		BlockPos pu = pos.add(0, 1, 0);
		BlockPos pd = pos.add(0, -1, 0);
		BlockPos pf = pos.add(0, 0, 1);
		BlockPos pb = pos.add(0, 0, -1);
		
		Block bl = w.getBlockState(pl).getBlock();
		Block br = w.getBlockState(pr).getBlock();
		Block bu = w.getBlockState(pu).getBlock();
		Block bd = w.getBlockState(pd).getBlock();
		Block bf = w.getBlockState(pf).getBlock();
		Block bb = w.getBlockState(pb).getBlock();
		
	
		if(bl instanceof BlockLog && pl.getDistance(origin.getX(), pl.getY(), origin.getZ()) < maxHorizLogDist || bl instanceof BlockLeaves && pl.getDistance(origin.getX(), pl.getY(), origin.getZ()) < maxHorizLeafDist) 
			getTreeFromLog(w, pl, blocks, origin);
		if(br instanceof BlockLog && pr.getDistance(origin.getX(), pr.getY(), origin.getZ()) < maxHorizLogDist || br instanceof BlockLeaves && pr.getDistance(origin.getX(), pr.getY(), origin.getZ()) < maxHorizLeafDist) 
			getTreeFromLog(w, pr, blocks, origin);
		if(bu instanceof BlockLog || bu instanceof BlockLeaves) 
			getTreeFromLog(w, pu, blocks, origin);
		if(bd instanceof BlockLog || bd instanceof BlockLeaves) 
			getTreeFromLog(w, pd, blocks, origin);
		if(bf instanceof BlockLog && pf.getDistance(origin.getX(), pf.getY(), origin.getZ()) < maxHorizLogDist || bf instanceof BlockLeaves && pf.getDistance(origin.getX(), pf.getY(), origin.getZ())  < maxHorizLeafDist) 
			getTreeFromLog(w, pf, blocks, origin);
		if(bb instanceof BlockLog && pb.getDistance(origin.getX(), pb.getY(), origin.getZ()) < maxHorizLogDist || bb instanceof BlockLeaves && pb.getDistance(origin.getX(), pb.getY(), origin.getZ()) < maxHorizLeafDist) 
			getTreeFromLog(w, pb, blocks, origin);
	}
	
}

