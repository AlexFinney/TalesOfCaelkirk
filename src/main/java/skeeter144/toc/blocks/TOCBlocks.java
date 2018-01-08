package skeeter144.toc.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import skeeter144.toc.items.TOCItems;

public class TOCBlocks {

	public static Block copper_ore = new BlockHarvestableOre("block_ore_copper",  TOCItems.ore_copper, 10, 20, 35);
	public static Block tin_ore = new BlockHarvestableOre("block_ore_tin",  TOCItems.ore_tin, 10, 20, 35);
	public static Block iron_ore = new BlockHarvestableOre("block_ore_iron", TOCItems.ore_iron, 25, 30, 50);
	public static Block coal_ore = new BlockHarvestableOre("block_ore_coal", TOCItems.ore_coal, 53, 38, 60);
	public static Block gold_ore = new BlockHarvestableOre("block_ore_gold", TOCItems.ore_gold, 53, 38, 60);
	public static Block mithril_ore = new BlockHarvestableOre("block_ore_mithril", TOCItems.ore_mithril, 158, 120, 180);
	public static Block adamantite_ore = new BlockHarvestableOre("block_ore_adamantite", TOCItems.ore_adamantite, 290, 240, 360);
	public static Block runite_ore = new BlockHarvestableOre("block_ore_runite", TOCItems.ore_runite, 475, 360, 480);
	public static Block dragonstone_ore = new BlockHarvestableOre("block_ore_dragonstone", TOCItems.ore_dragonstone, 578, 420, 600);
	
	public static Block harvested_ore = new BlockHarvestedOre(Material.ROCK, "harvested_ore");
	
	
	public static Block harvested_tree = new BlockHarvestedTree(Material.WOOD, "harvested_tree");
	
	public static Block spiderTreeLog = new BlockSpiderTreeLog("spider_tree_log");
	public static Block spiderTreeLeaves = new BlockSpiderTreeLog("spider_tree_leaves");
	public static Block blockAnvil = new BlockAnvil("anvil");
	
	public static Block oak_log = new CustomBlockLog("oak_log");
	public static Block spruce_log = new CustomBlockLog("spruce_log");
	public static Block birch_log = new CustomBlockLog("birch_log");
	public static Block jungle_log = new CustomBlockLog("jungle_log");
	public static Block dark_oak_log = new CustomBlockLog("dark_oak_log");
	public static Block acacia_log = new CustomBlockLog("acacia_log");
	
	public static void registerRenders() {
		registerBlockItemRender(copper_ore);
		registerBlockItemRender(tin_ore);
		registerBlockItemRender(iron_ore);
		registerBlockItemRender(coal_ore);
		registerBlockItemRender(gold_ore);
		registerBlockItemRender(mithril_ore);
		registerBlockItemRender(adamantite_ore);
		registerBlockItemRender(runite_ore);
		registerBlockItemRender(dragonstone_ore);
		registerBlockItemRender(harvested_ore);
		registerBlockItemRender(spiderTreeLeaves);
		registerBlockItemRender(spiderTreeLog);
		registerBlockItemRender(blockAnvil);
		registerBlockItemRender(harvested_tree);
		registerBlockItemRender(oak_log);
		registerBlockItemRender(spruce_log);
		registerBlockItemRender(birch_log);
		registerBlockItemRender(jungle_log);
		registerBlockItemRender(dark_oak_log);
		registerBlockItemRender(acacia_log);
	}
	
	public static void registerAllTileEntities() {
		GameRegistry.registerTileEntity(((BlockHarvestedOre)harvested_ore).getTileEntityClass(), harvested_ore.getRegistryName().toString());
		GameRegistry.registerTileEntity(((BlockAnvil)blockAnvil).getTileEntityClass(), blockAnvil.getRegistryName().toString());
		GameRegistry.registerTileEntity(((BlockHarvestedTree)harvested_tree).getTileEntityClass(), harvested_tree.getRegistryName().toString());
	}

	public static void registerAllBlocks() {
		final Block[] blocks = {
				spiderTreeLeaves,
				spiderTreeLog,
				copper_ore,
				tin_ore,
				iron_ore,
				coal_ore,
				gold_ore,
				mithril_ore,
				adamantite_ore,
				runite_ore,
				dragonstone_ore,
				harvested_ore,
				blockAnvil,
				harvested_tree,
				oak_log,
				acacia_log,
				spruce_log,
				dark_oak_log,
				birch_log,
				jungle_log,
		};
		for(Block b : blocks) {
			register(b);
		}
	}
	
	private static void register(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		
		if(block.equals(oak_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
		} else if(block.equals(spruce_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
		}else if(block.equals(birch_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
		}else if(block.equals(acacia_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
		}else if(block.equals(jungle_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
		}else if(block.equals(dark_oak_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
		}
	}
	
	private static void registerBlockItemRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(ForgeRegistries.ITEMS.getValue(block.getRegistryName()), 0, 
				new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
