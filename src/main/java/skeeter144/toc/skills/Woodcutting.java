package skeeter144.toc.skills;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skeeter144.toc.blocks.CustomBlockLog;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.items.tools.TOCAxe;

public class Woodcutting {

	public static final int MAX_TREE_SIZE = 300;
	
	static Map<CustomBlockLog, Float> treeDestroyChancesPerHarvest = new HashMap<CustomBlockLog, Float>();
	static Map<CustomBlockLog, HashMap<TOCAxe, Float>> logHarvestChances = new HashMap<CustomBlockLog, HashMap<TOCAxe, Float>>();
	
	
	public static void init() {
		HashMap<TOCAxe, Float> oak_harvest_chances = new HashMap<TOCAxe, Float>();
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, .1f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_iron, .2f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_steel, .3f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, .4f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .5f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .6f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .7f);
		
		HashMap<TOCAxe, Float> birch_harvest_chances = new HashMap<TOCAxe, Float>();
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_iron, .1f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_steel, .15f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, .25f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .35f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .5f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .6f);
		
		HashMap<TOCAxe, Float> spruce_harvest_chances = new HashMap<TOCAxe, Float>();
		spruce_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		spruce_harvest_chances.put((TOCAxe) TOCItems.axe_iron, 0f);
		spruce_harvest_chances.put((TOCAxe) TOCItems.axe_steel, .1f);
		spruce_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, .17f);
		spruce_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .24f);
		spruce_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .31f);
		spruce_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .38f);
		
		HashMap<TOCAxe, Float> acacia_harvest_chances = new HashMap<TOCAxe, Float>();
		acacia_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		acacia_harvest_chances.put((TOCAxe) TOCItems.axe_iron, 0f);
		acacia_harvest_chances.put((TOCAxe) TOCItems.axe_steel, 0f);
		acacia_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, .1f);
		acacia_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .15f);
		acacia_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .2f);
		acacia_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .25f);
		
		HashMap<TOCAxe, Float> jungle_harvest_chances = new HashMap<TOCAxe, Float>();
		jungle_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		jungle_harvest_chances.put((TOCAxe) TOCItems.axe_iron, 0f);
		jungle_harvest_chances.put((TOCAxe) TOCItems.axe_steel, 0f);
		jungle_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, 0f);
		jungle_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .05f);
		jungle_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .1f);
		jungle_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .15f);
		
		HashMap<TOCAxe, Float> dark_oak_harvest_chances = new HashMap<TOCAxe, Float>();
		dark_oak_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		dark_oak_harvest_chances.put((TOCAxe) TOCItems.axe_iron, 0f);
		dark_oak_harvest_chances.put((TOCAxe) TOCItems.axe_steel, 0f);
		dark_oak_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, 0f);
		dark_oak_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .0f);
		dark_oak_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .05f);
		dark_oak_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .1f);
		
		logHarvestChances.put((CustomBlockLog) TOCBlocks.oak_log, oak_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.birch_log, birch_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.spruce_log, spruce_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.acacia_log, acacia_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.jungle_log, jungle_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.dark_oak_log, dark_oak_harvest_chances);

		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.oak_log, .3f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.birch_log, .25f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.spruce_log, .15f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.acacia_log, .15f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.jungle_log, .15f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.dark_oak_log, .15f);
	}
	
	public static float getChopChanceForWood(TOCAxe axe, IBlockState wood) {
		return logHarvestChances.get(wood.getBlock()).get(axe);
	}
	
	public static float getDestroyChanceForWood(IBlockState wood) {
		return treeDestroyChancesPerHarvest.get(wood.getBlock());
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
		return Item.getItemFromBlock(wood);
	}
	
	static final int maxHorizLeafDist = 7;
	static final int maxHorizLogDist = 7;
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
		

		boolean isLeaf = false;
		if(w.getBlockState(pos).getBlock() instanceof BlockLeaves)
			isLeaf = true;
		
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
		
		if(isLeaf) {
			if(bl instanceof BlockLeaves && pl.getDistance(origin.getX(), pl.getY(), origin.getZ()) < maxHorizLeafDist) 
				getTreeFromLog(w, pl, blocks, origin);
			if(br instanceof BlockLeaves && pr.getDistance(origin.getX(), pr.getY(), origin.getZ()) < maxHorizLeafDist) 
				getTreeFromLog(w, pr, blocks, origin);
			if(bu instanceof BlockLeaves) 
				getTreeFromLog(w, pu, blocks, origin);
			if(bd instanceof BlockLeaves) 
				getTreeFromLog(w, pd, blocks, origin);
			if(bf instanceof BlockLeaves && pf.getDistance(origin.getX(), pf.getY(), origin.getZ())  < maxHorizLeafDist) 
				getTreeFromLog(w, pf, blocks, origin);
			if(bb instanceof BlockLeaves && pb.getDistance(origin.getX(), pb.getY(), origin.getZ()) < maxHorizLeafDist) 
				getTreeFromLog(w, pb, blocks, origin);
		}else {
			if(bl instanceof BlockLog && pl.getDistance(origin.getX(), pl.getY(), origin.getZ()) < maxHorizLogDist) 
				getTreeFromLog(w, pl, blocks, origin);
			if(br instanceof BlockLog && pr.getDistance(origin.getX(), pr.getY(), origin.getZ()) < maxHorizLogDist) 
				getTreeFromLog(w, pr, blocks, origin);
			if(bu instanceof BlockLog || bu instanceof BlockLeaves) 
				getTreeFromLog(w, pu, blocks, origin);
			if(bd instanceof BlockLog || bd instanceof BlockLeaves) 
				getTreeFromLog(w, pd, blocks, origin);
			if(bf instanceof BlockLog && pf.getDistance(origin.getX(), pf.getY(), origin.getZ()) < maxHorizLogDist) 
				getTreeFromLog(w, pf, blocks, origin);
			if(bb instanceof BlockLog && pb.getDistance(origin.getX(), pb.getY(), origin.getZ()) < maxHorizLogDist) 
				getTreeFromLog(w, pb, blocks, origin);
		}
		
	
	}
	
}

