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
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.blocks.log.CustomBlockLog;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.items.tools.TOCAxe;

public class Woodcutting {

	public static final int MAX_TREE_SIZE = 300;
	
	static Map<CustomBlockLog, Float> treeDestroyChancesPerHarvest = new HashMap<CustomBlockLog, Float>();
	static Map<CustomBlockLog, HashMap<TOCAxe, Float>> logHarvestChances = new HashMap<CustomBlockLog, HashMap<TOCAxe, Float>>();
	static Map<CustomBlockLog, Integer> treeLevelRequirements = new HashMap<CustomBlockLog, Integer>();
	
	public static void init() {
		
		treeLevelRequirements.put((CustomBlockLog) TOCBlocks.oak_log,     1);
		treeLevelRequirements.put((CustomBlockLog) TOCBlocks.birch_log,  15);
		treeLevelRequirements.put((CustomBlockLog) TOCBlocks.maple_log,  30);
		treeLevelRequirements.put((CustomBlockLog) TOCBlocks.yew_log,    45);
		treeLevelRequirements.put((CustomBlockLog) TOCBlocks.orc_log,    60);
		treeLevelRequirements.put((CustomBlockLog) TOCBlocks.magic_log,  75);
		
		
		HashMap<TOCAxe, Float> oak_harvest_chances = new HashMap<TOCAxe, Float>();
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, .2f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_iron, .25f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_steel, .35f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, .45f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .55f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .65f);
		oak_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .75f);
		
		HashMap<TOCAxe, Float> birch_harvest_chances = new HashMap<TOCAxe, Float>();
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_iron, .15f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_steel, .2f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, .3f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .4f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .5f);
		birch_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .6f);
		
		HashMap<TOCAxe, Float> maple_harvest_chances = new HashMap<TOCAxe, Float>();
		maple_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		maple_harvest_chances.put((TOCAxe) TOCItems.axe_iron, 0f);
		maple_harvest_chances.put((TOCAxe) TOCItems.axe_steel, 0f);
		maple_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, .17f);
		maple_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .24f);
		maple_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .31f);
		maple_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .38f);
		
		HashMap<TOCAxe, Float> yew_harvest_chances = new HashMap<TOCAxe, Float>();
		yew_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		yew_harvest_chances.put((TOCAxe) TOCItems.axe_iron, 0f);
		yew_harvest_chances.put((TOCAxe) TOCItems.axe_steel, 0f);
		yew_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, .1f);
		yew_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .18f);
		yew_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .25f);
		yew_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .35f);
		
		
		HashMap<TOCAxe, Float>  orc_harvest_chances = new HashMap<TOCAxe, Float>();
		orc_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		orc_harvest_chances.put((TOCAxe) TOCItems.axe_iron, 0f);
		orc_harvest_chances.put((TOCAxe) TOCItems.axe_steel, 0f);
		orc_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, 0f);
		orc_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .0f);
		orc_harvest_chances.put((TOCAxe) TOCItems.axe_runite, .1f);
		orc_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .2f);
		
		HashMap<TOCAxe, Float>  magic_harvest_chances = new HashMap<TOCAxe, Float>();
		magic_harvest_chances.put((TOCAxe) TOCItems.axe_bronze, 0f);
		magic_harvest_chances.put((TOCAxe) TOCItems.axe_iron, 0f);
		magic_harvest_chances.put((TOCAxe) TOCItems.axe_steel, 0f);
		magic_harvest_chances.put((TOCAxe) TOCItems.axe_mithril, 0f);
		magic_harvest_chances.put((TOCAxe) TOCItems.axe_adamantite, .0f);
		magic_harvest_chances.put((TOCAxe) TOCItems.axe_runite, 0f);
		magic_harvest_chances.put((TOCAxe) TOCItems.axe_dragonstone, .1f);
		
		logHarvestChances.put((CustomBlockLog) TOCBlocks.oak_log, oak_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.birch_log, birch_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.maple_log, maple_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.yew_log, yew_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.magic_log, magic_harvest_chances);
		logHarvestChances.put((CustomBlockLog) TOCBlocks.orc_log, orc_harvest_chances);

		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.oak_log, .3f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.birch_log, .25f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.maple_log, .2f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.yew_log, .15f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.orc_log, .13f);
		treeDestroyChancesPerHarvest.put((CustomBlockLog) TOCBlocks.magic_log, .1f);
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
	
	public static int getLevelRequirementForWood(BlockLog wood) {
		return treeLevelRequirements.get(wood);
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
			if(bl instanceof BlockLog && pl.getDistance(origin.getX(), pl.getY(), origin.getZ()) < maxHorizLogDist || bl instanceof BlockLeaves) 
				getTreeFromLog(w, pl, blocks, origin);
			if(br instanceof BlockLog && pr.getDistance(origin.getX(), pr.getY(), origin.getZ()) < maxHorizLogDist || br instanceof BlockLeaves) 
				getTreeFromLog(w, pr, blocks, origin);
			if(bu instanceof BlockLog || bu instanceof BlockLeaves) 
				getTreeFromLog(w, pu, blocks, origin);
			if(bd instanceof BlockLog || bd instanceof BlockLeaves) 
				getTreeFromLog(w, pd, blocks, origin);
			if(bf instanceof BlockLog  && pf.getDistance(origin.getX(), pf.getY(), origin.getZ()) < maxHorizLogDist || bf instanceof BlockLeaves) 
				getTreeFromLog(w, pf, blocks, origin);
			if(bb instanceof BlockLog && pb.getDistance(origin.getX(), pb.getY(), origin.getZ()) < maxHorizLogDist || bb instanceof BlockLeaves) 
				getTreeFromLog(w, pb, blocks, origin);
		}
		
	
	}
	
}

