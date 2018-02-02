package skeeter144.toc.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import skeeter144.toc.blocks.log.CustomBlockLog;
import skeeter144.toc.blocks.log.BlockMagicLeaves;
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
	
	public static Block blockAnvil = new BlockAnvil("anvil");
	
	public static Block oak_log = new CustomBlockLog("oak_log");
	public static Block spruce_log = new CustomBlockLog("spruce_log");
	public static Block birch_log = new CustomBlockLog("birch_log");
	public static Block magic_log = new CustomBlockLog("magic_log");
	public static Block orc_log = new CustomBlockLog("orc_log");
	
	public static Block magicLeaves = new BlockMagicLeaves("magic_leaves");
	public static Block orc_leaves = new BlockMagicLeaves("orc_leaves");
	
	
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
		registerBlockItemRender(blockAnvil);
		registerBlockItemRender(harvested_tree);
		registerBlockItemRender(oak_log);
		registerBlockItemRender(spruce_log);
		registerBlockItemRender(birch_log);
		registerBlockItemRender(magic_log);
		registerBlockItemRender(magicLeaves);
		registerBlockItemRender(orc_log);
		registerBlockItemRender(orc_leaves);
	}
	
	public static void registerAllTileEntities() {
		GameRegistry.registerTileEntity(((BlockHarvestedOre)harvested_ore).getTileEntityClass(), harvested_ore.getRegistryName().toString());
		GameRegistry.registerTileEntity(((BlockAnvil)blockAnvil).getTileEntityClass(), blockAnvil.getRegistryName().toString());
		GameRegistry.registerTileEntity(((BlockHarvestedTree)harvested_tree).getTileEntityClass(), harvested_tree.getRegistryName().toString());
	}

	public static void registerAllBlocks() {
		final Block[] blocks = {
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
				spruce_log,
				birch_log,
				magicLeaves,
				magic_log,
				orc_log,
				orc_leaves
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
			block.setHardness(-1);
		} else if(block.equals(spruce_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		}else if(block.equals(birch_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		}else if(block.equals(magic_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		}
	}
	
	private static void registerBlockItemRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(ForgeRegistries.ITEMS.getValue(block.getRegistryName()), 0, 
				new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
