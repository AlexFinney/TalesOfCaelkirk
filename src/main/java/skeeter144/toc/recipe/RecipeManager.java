package skeeter144.toc.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.ItemCraftedMessage;
import skeeter144.toc.network.Network;

public class RecipeManager {

	HashMap<UUID, List<Recipe>> playerCraftingQueue = new HashMap<UUID, List<Recipe>>();
	public final Recipe[] SMELTING_RECIPES = new Recipe[] {
//			new Recipe(new ItemStack(TOCItems.ingot_bronze), Levels.SMITHING,      1, 12, new ItemStack(TOCItems.ore_copper), new ItemStack(TOCItems.ore_tin)),
//			new Recipe(new ItemStack(TOCItems.ingot_iron), Levels.SMITHING,        7, 20,new ItemStack(TOCItems.ore_iron, 2)),
//			new Recipe(new ItemStack(TOCItems.ingot_steel),Levels.SMITHING,        23, 35, new ItemStack(TOCItems.ore_iron, 2), new ItemStack(TOCItems.ore_coal)),
//			new Recipe(new ItemStack(TOCItems.ingot_gold), Levels.SMITHING,        23, 15, new ItemStack(TOCItems.ore_gold)),
//			new Recipe(new ItemStack(TOCItems.ingot_mithril), Levels.SMITHING,     35, 80,new ItemStack(TOCItems.ore_mithril), new ItemStack(TOCItems.ore_coal, 3)),
//			new Recipe(new ItemStack(TOCItems.ingot_adamantite), Levels.SMITHING,  50, 130,new ItemStack(TOCItems.ore_adamantite), new ItemStack(TOCItems.ore_coal, 5)),
//			new Recipe(new ItemStack(TOCItems.ingot_runite), Levels.SMITHING,      70, 203, new ItemStack(TOCItems.ore_runite), new ItemStack(TOCItems.ore_coal, 7)),
//			new Recipe(new ItemStack(TOCItems.ingot_dragonstone), Levels.SMITHING, 85, 317, new ItemStack(TOCItems.ore_dragonstone), new ItemStack(TOCItems.ore_coal, 9))
			};
	
	
	public final Recipe[] SMITHING_RECIPES = new Recipe[] {
//			new Recipe(new ItemStack(TOCItems.bronze_club),           Levels.SMITHING, 1, 30, new ItemStack(TOCItems.ingot_bronze, 3)),
//			new Recipe(new ItemStack(TOCItems.axe_bronze),            Levels.SMITHING, 1, 10, new ItemStack(TOCItems.ingot_bronze, 1)),
//			new Recipe(new ItemStack(TOCItems.bronze_short_sword),    Levels.SMITHING, 1, 20, new ItemStack(TOCItems.ingot_bronze, 2)),
//			new Recipe(new ItemStack(TOCItems.arrowhead_bronze, 4),   Levels.SMITHING, 4, 15, new ItemStack(TOCItems.ingot_bronze, 1)),
//			new Recipe(new ItemStack(TOCItems.bronze_pickaxe),        Levels.SMITHING, 8, 45, new ItemStack(TOCItems.ingot_bronze, 3)),
//			new Recipe(new ItemStack(TOCItems.bronze_warhammer),      Levels.SMITHING, 12, 70, new ItemStack(TOCItems.ingot_bronze, 4)),
//			new Recipe(new ItemStack(TOCItems.bronze_great_axe),      Levels.SMITHING, 15, 150, new ItemStack(TOCItems.ingot_bronze, 6)),
//			new Recipe(new ItemStack(TOCItems.bronze_great_sword),    Levels.SMITHING, 15, 150, new ItemStack(TOCItems.ingot_bronze, 6)),
//			
//			new Recipe(new ItemStack(TOCItems.iron_club),             Levels.SMITHING, 7, 70, new ItemStack(TOCItems.ingot_iron, 3)),
//			new Recipe(new ItemStack(TOCItems.axe_iron),              Levels.SMITHING, 7, 30, new ItemStack(TOCItems.ingot_iron, 1)),
//			new Recipe(new ItemStack(TOCItems.iron_short_sword),      Levels.SMITHING, 7, 60, new ItemStack(TOCItems.ingot_iron, 2)),
//			new Recipe(new ItemStack(TOCItems.arrowhead_iron, 4),     Levels.SMITHING, 11, 40, new ItemStack(TOCItems.ingot_iron, 1)),
//			new Recipe(new ItemStack(TOCItems.iron_pickaxe),          Levels.SMITHING, 15, 90, new ItemStack(TOCItems.ingot_iron, 3)),
//			new Recipe(new ItemStack(TOCItems.iron_warhammer),        Levels.SMITHING, 19, 120, new ItemStack(TOCItems.ingot_iron, 4)),
//			new Recipe(new ItemStack(TOCItems.iron_great_axe),        Levels.SMITHING, 23, 200, new ItemStack(TOCItems.ingot_iron, 6)),
//			new Recipe(new ItemStack(TOCItems.iron_great_sword),      Levels.SMITHING, 23, 200, new ItemStack(TOCItems.ingot_iron, 6)),
//			
//			new Recipe(new ItemStack(TOCItems.steel_club),             Levels.SMITHING, 23, 160, new ItemStack(TOCItems.ingot_steel, 3)),
//			new Recipe(new ItemStack(TOCItems.axe_steel),              Levels.SMITHING, 23, 80, new ItemStack(TOCItems.ingot_steel, 1)),
//			new Recipe(new ItemStack(TOCItems.steel_short_sword),      Levels.SMITHING, 23, 140, new ItemStack(TOCItems.ingot_steel, 2)),
//			new Recipe(new ItemStack(TOCItems.arrowhead_steel, 4),     Levels.SMITHING, 28, 100, new ItemStack(TOCItems.ingot_steel, 1)),
//			new Recipe(new ItemStack(TOCItems.steel_pickaxe),          Levels.SMITHING, 33, 200, new ItemStack(TOCItems.ingot_steel, 3)),
//			new Recipe(new ItemStack(TOCItems.steel_warhammer),        Levels.SMITHING, 38, 340, new ItemStack(TOCItems.ingot_steel, 4)),
//			new Recipe(new ItemStack(TOCItems.steel_great_axe),        Levels.SMITHING, 43, 600, new ItemStack(TOCItems.ingot_steel, 6)),
//			new Recipe(new ItemStack(TOCItems.steel_great_sword),      Levels.SMITHING, 43, 600, new ItemStack(TOCItems.ingot_steel, 6))
	};
	
	public Recipe getRecipeForItem(Item i) {
		for(Recipe r : SMELTING_RECIPES) {
			if(r.crafted.getItem().equals(i))
				return r;
		}
		
		for(Recipe r : SMITHING_RECIPES) {
			if(r.crafted.getItem().equals(i))
				return r;
		}
		return null;
	}
	
	public void queueRecipe(UUID uuid, Recipe recipe) {
		if(playerCraftingQueue.get(uuid) == null) 
			playerCraftingQueue.put(uuid, new ArrayList<Recipe>());
		playerCraftingQueue.get(uuid).add(recipe);
	}
	
	public void cancelCraftingForPlayer(UUID uuid) {
		playerCraftingQueue.remove(uuid);
	}
	
	public void craftRecipes() {
		for(Map.Entry<UUID, List<Recipe>> entry : playerCraftingQueue.entrySet()) {
//			EntityPlayerMP player = FMLCommonHandler.instance().getInstanceServerInstance().getPlayerList().getPlayerByUUID(entry.getKey());
//			
//			Recipe r = entry.getValue().get(0);
//			if(r.canRecipeBeCrafted(player.inventory)) {
//				craftRecipe(player, r);
//			}else {
//				playerCraftingQueue.get(player.getUniqueID()).remove(r);
//				if(playerCraftingQueue.get(player.getUniqueID()).size() == 0)
//					playerCraftingQueue.remove(player.getUniqueID());
//			}
		}
	}
	
	private void craftRecipe(EntityPlayerMP player, Recipe r) {
		ItemStack[] itemsLeft = new ItemStack[r.components.length];
		for(int i = 0; i < itemsLeft.length; ++i) 
			itemsLeft[i] = new ItemStack(r.components[i].getItem(), r.components[i].getCount());
		
		for(int i = 0; i < itemsLeft.length; ++i) {
			int slot = 0;
			for(ItemStack stack : player.inventory.mainInventory) {
				if(stack.getItem().equals(itemsLeft[i].getItem())){
					itemsLeft[i].shrink(stack.getCount());
					player.inventory.getStackInSlot(slot).shrink(stack.getCount());
				}
				if(itemsLeft[i].getCount() == 0 || itemsLeft[i].isEmpty())
					break;
				++slot;
			}
		}
		//MinecraftForge.EVENT_BUS.post(new ItemSmithedEvent(player, r.crafted.copy()));
		
		player.inventory.addItemStackToInventory(r.crafted.copy());
		playerCraftingQueue.get(player.getUniqueID()).remove(r);
		if(playerCraftingQueue.get(player.getUniqueID()).size() == 0)
			playerCraftingQueue.remove(player.getUniqueID());
		
		Network.INSTANCE.sendTo(new ItemCraftedMessage(), player);
		TOCMain.pm.getPlayer(player).levels.addExp(r.level, r.xp);
		Network.INSTANCE.sendTo(new AddLevelXpMessage(r.level.name().toString(), r.xp), player);
	}
	
	static RecipeManager instance;
	public static RecipeManager instance() {
		if(instance == null)
			instance = new RecipeManager();
		return instance;
	}
	
	public static class ItemSmithedEvent extends Event
	{		
		public EntityPlayer player;
		public ItemStack smithed;
		public ItemSmithedEvent(EntityPlayerMP player, ItemStack smithed) {
			this.player = player;
			this.smithed = smithed;
		}
	}
}
