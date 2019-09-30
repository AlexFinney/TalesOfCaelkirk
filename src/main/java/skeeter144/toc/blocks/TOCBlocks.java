package skeeter144.toc.blocks;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.blocks.log.BlockMagicLeaves;
import skeeter144.toc.blocks.log.CustomBlockLeaves;
import skeeter144.toc.blocks.log.CustomBlockLog;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.entity.tile.TileEntityHarvestedOre;
import skeeter144.toc.entity.tile.TileEntityHarvestedTree;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;
import skeeter144.toc.util.Reference;

public class TOCBlocks {

//	public static Block copper_ore = new BlockHarvestableOre("block_ore_copper",  TOCItems.ore_copper, 10, 20, 35);
//	public static Block tin_ore = new BlockHarvestableOre("block_ore_tin",  TOCItems.ore_tin, 10, 20, 35);
//	public static Block iron_ore = new BlockHarvestableOre("block_ore_iron", TOCItems.ore_iron, 25, 30, 50);
//	public static Block coal_ore = new BlockHarvestableOre("block_ore_coal", TOCItems.ore_coal, 53, 38, 60);
//	public static Block gold_ore = new BlockHarvestableOre("block_ore_gold", TOCItems.ore_gold, 53, 38, 60);
//	public static Block mithril_ore = new BlockHarvestableOre("block_ore_mithril", TOCItems.ore_mithril, 158, 120, 180);
//	public static Block adamantite_ore = new BlockHarvestableOre("block_ore_adamantite", TOCItems.ore_adamantite, 290, 240, 360);
//	public static Block runite_ore = new BlockHarvestableOre("block_ore_runite", TOCItems.ore_runite, 475, 360, 480);
//	public static Block dragonstone_ore = new BlockHarvestableOre("block_ore_dragonstone", TOCItems.ore_dragonstone, 578, 420, 600);
//	
//	
//	//blocks that have corresponding TileEntity
//	public static Block harvested_ore = new BlockHarvestedOre(Material.ROCK, "harvested_ore");
//	public static Block harvested_tree = new BlockHarvestedTree(Material.WOOD, "harvested_tree");
//	public static Block blockAnvil = new BlockAnvil("anvil");
//	public static Block blockMobSpawnerInvis = new BlockMobSpawner("mob_spawner");
//	public static Block blockMobSpawner = new CustomBlock(Properties.create(Material.ROCK), "mob_spawner_model");
//	
	public static Block oak_log = new CustomBlockLog("oak_log");
	public static Block birch_log = new CustomBlockLog("birch_log");
	public static Block maple_log = new CustomBlockLog("maple_log");
	public static Block yew_log = new CustomBlockLog("yew_log");
	public static Block orc_log = new CustomBlockLog("orc_log");
	public static Block magic_log = new CustomBlockLog("magic_log");
//	
	public static Block magicLeaves = new BlockMagicLeaves("magic_leaves");
	public static Block orc_leaves = new CustomBlockLeaves("orc_leaves");
	public static Block maple_leaves = new CustomBlockLeaves("maple_leaves");
	public static Block yew_leaves = new CustomBlockLeaves("yew_leaves");
	
	public static TileEntityType<TileEntityHarvestedOre> te_harvested_ore;
	public static TileEntityType<TileEntityHarvestedTree> te_harvested_tree;
	public static TileEntityType<TileEntityAnvil> te_anvil;
	public static TileEntityType<TileEntityMobSpawner> te_mob_spawner;


	
	
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
	
	@SubscribeEvent
	public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> evt) {
		te_harvested_ore = TileEntityType.register(Reference.MODID + ":harvested_ore", TileEntityType.Builder.create(TileEntityHarvestedOre::new));
		te_anvil = TileEntityType.register(Reference.MODID + ":anvil", TileEntityType.Builder.create(TileEntityAnvil::new));
		te_harvested_tree = TileEntityType.register(Reference.MODID + ":harvested_tree", TileEntityType.Builder.create(TileEntityHarvestedTree::new));
		te_mob_spawner = TileEntityType.register(Reference.MODID + ":mob_spawner", TileEntityType.Builder.create(TileEntityMobSpawner::new));

	}
	
	public static void registerAllBlocks(final RegistryEvent.Register<Block> event) {
		try {
			for (Field f : TOCBlocks.class.getFields()) {
				if (f.get(null) instanceof Block) {
					event.getRegistry().register((Block) f.get(null));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void register(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block, new Item.Properties()).setRegistryName(block.getRegistryName()));
	}
	
	private static void registerBlockItemRender(Block block) {
//		if(block == blockMobSpawnerInvis) {
//		//	ModelLoader.setCustomModelResourceLocation(ForgeRegistries.ITEMS.getValue(blockMobSpawnerInvis.getRegistryName()), 0, 
//		//			new ModelResourceLocation(blockMobSpawner.getRegistryName(), "inventory"));
//		}else{
//	//		ModelLoader.setCustomModelResourceLocation(ForgeRegistries.ITEMS.getValue(block.getRegistryName()), 0, 
//	//				new ModelResourceLocation(block.getRegistryName(), "inventory"));
//		}
	}
}
