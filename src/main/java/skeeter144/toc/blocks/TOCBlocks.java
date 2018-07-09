package skeeter144.toc.blocks;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import skeeter144.toc.blocks.log.BlockMagicLeaves;
import skeeter144.toc.blocks.log.CustomBlockLeaves;
import skeeter144.toc.blocks.log.CustomBlockLog;
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
	
	
	//blocks that have corresponding TileEntity
	public static Block harvested_ore = new BlockHarvestedOre(Material.ROCK, "harvested_ore");
	public static Block harvested_tree = new BlockHarvestedTree(Material.WOOD, "harvested_tree");
	public static Block blockAnvil = new BlockAnvil("anvil");
	public static Block blockMobSpawnerInvis = new BlockMobSpawner("mob_spawner");
	public static Block blockMobSpawner = new CustomBlock("mob_spawner_model", Material.AIR);
	
	public static Block oak_log = new CustomBlockLog("oak_log");
	public static Block birch_log = new CustomBlockLog("birch_log");
	public static Block maple_log = new CustomBlockLog("maple_log");
	public static Block yew_log = new CustomBlockLog("yew_log");
	public static Block orc_log = new CustomBlockLog("orc_log");
	public static Block magic_log = new CustomBlockLog("magic_log");
	
	public static Block magicLeaves = new BlockMagicLeaves("magic_leaves");
	public static Block orc_leaves = new CustomBlockLeaves("orc_leaves");
	public static Block maple_leaves = new CustomBlockLeaves("maple_leaves");
	public static Block yew_leaves = new CustomBlockLeaves("yew_leaves");
	
	public static void registerRenders() {
		try {
			for (Field f : TOCBlocks.class.getFields()) {
				if (f.get(null) instanceof Block) {
					registerBlockItemRender((Block) f.get(null));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void registerAllTileEntities() {
		GameRegistry.registerTileEntity(((BlockHarvestedOre)harvested_ore).getTileEntityClass(), harvested_ore.getRegistryName().toString());
		GameRegistry.registerTileEntity(((BlockAnvil)blockAnvil).getTileEntityClass(), blockAnvil.getRegistryName().toString());
		GameRegistry.registerTileEntity(((BlockHarvestedTree)harvested_tree).getTileEntityClass(), harvested_tree.getRegistryName().toString());
		GameRegistry.registerTileEntity(((BlockMobSpawner)blockMobSpawnerInvis).getTileEntityClass(), blockMobSpawnerInvis.getRegistryName().toString());
	}

	public static void registerAllBlocks() {
		try {
			for (Field f : TOCBlocks.class.getFields()) {
				if (f.get(null) instanceof Block) {
					register((Block) f.get(null));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void register(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		
		if(block.equals(oak_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		} else if(block.equals(birch_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		}else if(block.equals(maple_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		}else if(block.equals(yew_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		}else if(block.equals(orc_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		}else if(block.equals(magic_log)) {
			ForgeRegistries.ITEMS.getValue(block.getRegistryName()).setMaxStackSize(1);
			block.setHardness(-1);
		}
	}
	
	private static void registerBlockItemRender(Block block) {
		if(block == blockMobSpawnerInvis) {
			ModelLoader.setCustomModelResourceLocation(ForgeRegistries.ITEMS.getValue(blockMobSpawnerInvis.getRegistryName()), 0, 
					new ModelResourceLocation(blockMobSpawner.getRegistryName(), "inventory"));
		}else{
			ModelLoader.setCustomModelResourceLocation(ForgeRegistries.ITEMS.getValue(block.getRegistryName()), 0, 
					new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
	}
}
