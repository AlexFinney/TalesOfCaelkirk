package skeeter144.toc.items.tools;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import skeeter144.toc.blocks.BlockHarvestableOre;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.util.Reference;

public class TOCPickaxe extends ItemPickaxe{

	public TOCPickaxe(IItemTier tier, String name, float damage, float speed, Item.Properties builder) {
		super(tier, (int) damage, speed, builder);
		setRegistryName(Reference.MODID, name);
	}

	public static Map<TOCPickaxe, Map<BlockHarvestableOre, Float>> pickaxeChances;
	
	
	public float getMineChanceForOre(BlockHarvestableOre ore){
		Float f = pickaxeChances.get(this).get(ore);
		return f != null ? f : 0;
	}
	
	
	public static void initPickaxeChances() {
		pickaxeChances = new HashMap<TOCPickaxe, Map<BlockHarvestableOre, Float>>();
		
		pickaxeChances.put((TOCPickaxe) TOCItems.bronze_pickaxe, new HashMap());
		pickaxeChances.get(TOCItems.bronze_pickaxe).put((BlockHarvestableOre) TOCBlocks.copper_ore, .2f);
		pickaxeChances.get(TOCItems.bronze_pickaxe).put((BlockHarvestableOre) TOCBlocks.tin_ore, .2f);
		pickaxeChances.get(TOCItems.bronze_pickaxe).put((BlockHarvestableOre) TOCBlocks.iron_ore, .1f);
		
		
		pickaxeChances.put((TOCPickaxe) TOCItems.iron_pickaxe, new HashMap());
		pickaxeChances.get(TOCItems.iron_pickaxe).put((BlockHarvestableOre) TOCBlocks.copper_ore, .3f);
		pickaxeChances.get(TOCItems.iron_pickaxe).put((BlockHarvestableOre) TOCBlocks.tin_ore, .3f);
		pickaxeChances.get(TOCItems.iron_pickaxe).put((BlockHarvestableOre) TOCBlocks.iron_ore, .2f);
		pickaxeChances.get(TOCItems.iron_pickaxe).put((BlockHarvestableOre) TOCBlocks.coal_ore, .15f);
		
		
		pickaxeChances.put((TOCPickaxe) TOCItems.steel_pickaxe, new HashMap());
		pickaxeChances.get(TOCItems.steel_pickaxe).put((BlockHarvestableOre) TOCBlocks.copper_ore, .4f);
		pickaxeChances.get(TOCItems.steel_pickaxe).put((BlockHarvestableOre) TOCBlocks.tin_ore, .4f);
		pickaxeChances.get(TOCItems.steel_pickaxe).put((BlockHarvestableOre) TOCBlocks.iron_ore, .3f);
		pickaxeChances.get(TOCItems.steel_pickaxe).put((BlockHarvestableOre) TOCBlocks.coal_ore, .2f);
		pickaxeChances.get(TOCItems.steel_pickaxe).put((BlockHarvestableOre) TOCBlocks.gold_ore, .2f);
		pickaxeChances.get(TOCItems.steel_pickaxe).put((BlockHarvestableOre) TOCBlocks.mithril_ore, .05f);
		
		
		pickaxeChances.put((TOCPickaxe) TOCItems.mithril_pickaxe, new HashMap());
		pickaxeChances.get(TOCItems.mithril_pickaxe).put((BlockHarvestableOre) TOCBlocks.copper_ore, .6f);
		pickaxeChances.get(TOCItems.mithril_pickaxe).put((BlockHarvestableOre) TOCBlocks.tin_ore, .6f);
		pickaxeChances.get(TOCItems.mithril_pickaxe).put((BlockHarvestableOre) TOCBlocks.iron_ore, .5f);
		pickaxeChances.get(TOCItems.mithril_pickaxe).put((BlockHarvestableOre) TOCBlocks.coal_ore, .25f);
		pickaxeChances.get(TOCItems.mithril_pickaxe).put((BlockHarvestableOre) TOCBlocks.gold_ore, .25f);
		pickaxeChances.get(TOCItems.mithril_pickaxe).put((BlockHarvestableOre) TOCBlocks.mithril_ore, .08f);
		pickaxeChances.get(TOCItems.mithril_pickaxe).put((BlockHarvestableOre) TOCBlocks.adamantite_ore, .03f);
		
		pickaxeChances.put((TOCPickaxe) TOCItems.adamantite_pickaxe, new HashMap());
		pickaxeChances.get(TOCItems.adamantite_pickaxe).put((BlockHarvestableOre) TOCBlocks.copper_ore, .75f);
		pickaxeChances.get(TOCItems.adamantite_pickaxe).put((BlockHarvestableOre) TOCBlocks.tin_ore, .75f);
		pickaxeChances.get(TOCItems.adamantite_pickaxe).put((BlockHarvestableOre) TOCBlocks.iron_ore, .55f);
		pickaxeChances.get(TOCItems.adamantite_pickaxe).put((BlockHarvestableOre) TOCBlocks.coal_ore, .3f);
		pickaxeChances.get(TOCItems.adamantite_pickaxe).put((BlockHarvestableOre) TOCBlocks.gold_ore, .3f);
		pickaxeChances.get(TOCItems.adamantite_pickaxe).put((BlockHarvestableOre) TOCBlocks.mithril_ore, .12f);
		pickaxeChances.get(TOCItems.adamantite_pickaxe).put((BlockHarvestableOre) TOCBlocks.adamantite_ore, .08f);
		pickaxeChances.get(TOCItems.adamantite_pickaxe).put((BlockHarvestableOre) TOCBlocks.runite_ore, .027f);
		
		
		pickaxeChances.put((TOCPickaxe) TOCItems.runite_pickaxe, new HashMap());
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.copper_ore, .85f);
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.tin_ore, .85f);
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.iron_ore, .6f);
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.coal_ore, .35f);
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.gold_ore, .35f);
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.mithril_ore, .15f);
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.adamantite_ore, .13f);
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.runite_ore, .05f);
		pickaxeChances.get(TOCItems.runite_pickaxe).put((BlockHarvestableOre) TOCBlocks.dragonstone_ore, .02f);
		
		
		pickaxeChances.put((TOCPickaxe) TOCItems.dragonstone_pickaxe, new HashMap());
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.copper_ore, .9f);
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.tin_ore, .9f);
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.iron_ore, .7f);
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.coal_ore, .45f);
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.gold_ore, .45f);
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.mithril_ore, .23f);
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.adamantite_ore, .18f);
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.runite_ore, .1f);
		pickaxeChances.get(TOCItems.dragonstone_pickaxe).put((BlockHarvestableOre) TOCBlocks.dragonstone_ore, .04f);
	}
	
}
