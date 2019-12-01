package skeeter144.toc.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import skeeter144.toc.TOCMain;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.AddLevelXpPKT;
import skeeter144.toc.network.ItemCraftedPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.tasks.CraftingTask;

public class RecipeManager {

	HashMap<UUID, List<CraftingTask>> playerCraftingQueue = new HashMap<UUID, List<CraftingTask>>();
	
	public final Recipe[] SMELTING_RECIPES = new Recipe[] {
			new TickableRecipe(new ItemStack(TOCItems.ingot_bronze), Levels.SMITHING,      1, 12, 40, new ItemStack(TOCItems.ore_copper), new ItemStack(TOCItems.ore_tin)),
			new TickableRecipe(new ItemStack(TOCItems.ingot_iron), Levels.SMITHING,        7, 20, 50, new ItemStack(TOCItems.ore_iron, 2)),
			new TickableRecipe(new ItemStack(TOCItems.ingot_steel),Levels.SMITHING,        23, 35,  60, new ItemStack(TOCItems.ore_iron, 2), new ItemStack(TOCItems.ore_coal)),
			new TickableRecipe(new ItemStack(TOCItems.ingot_gold), Levels.SMITHING,        23, 15,  60, new ItemStack(TOCItems.ore_gold)),
			new TickableRecipe(new ItemStack(TOCItems.ingot_mithril), Levels.SMITHING,     35, 80, 70, new ItemStack(TOCItems.ore_mithril), new ItemStack(TOCItems.ore_coal, 3)),
			new TickableRecipe(new ItemStack(TOCItems.ingot_adamantite), Levels.SMITHING,  50, 130, 80, new ItemStack(TOCItems.ore_adamantite), new ItemStack(TOCItems.ore_coal, 5)),
			new TickableRecipe(new ItemStack(TOCItems.ingot_runite), Levels.SMITHING,      70, 203,  90, new ItemStack(TOCItems.ore_runite), new ItemStack(TOCItems.ore_coal, 7)),
			new TickableRecipe(new ItemStack(TOCItems.ingot_dragonstone), Levels.SMITHING, 85, 317,  100, new ItemStack(TOCItems.ore_dragonstone), new ItemStack(TOCItems.ore_coal, 9))
			};
	
	
	public final Recipe[] SMITHING_RECIPES = new Recipe[] {
			new Recipe(new ItemStack(TOCItems.bronze_club),           Levels.SMITHING, 1, 30, new ItemStack(TOCItems.ingot_bronze, 3)),
			new Recipe(new ItemStack(TOCItems.axe_bronze),            Levels.SMITHING, 1, 10, new ItemStack(TOCItems.ingot_bronze, 1)),
			new Recipe(new ItemStack(TOCItems.bronze_short_sword),    Levels.SMITHING, 1, 20, new ItemStack(TOCItems.ingot_bronze, 2)),
			new Recipe(new ItemStack(TOCItems.arrowhead_bronze, 4),   Levels.SMITHING, 4, 15, new ItemStack(TOCItems.ingot_bronze, 1)),
			new Recipe(new ItemStack(TOCItems.bronze_pickaxe),        Levels.SMITHING, 8, 45, new ItemStack(TOCItems.ingot_bronze, 3)),
			new Recipe(new ItemStack(TOCItems.bronze_warhammer),      Levels.SMITHING, 12, 70, new ItemStack(TOCItems.ingot_bronze, 4)),
			new Recipe(new ItemStack(TOCItems.bronze_great_axe),      Levels.SMITHING, 15, 150, new ItemStack(TOCItems.ingot_bronze, 6)),
			new Recipe(new ItemStack(TOCItems.bronze_great_sword),    Levels.SMITHING, 15, 150, new ItemStack(TOCItems.ingot_bronze, 6)),
			
			new Recipe(new ItemStack(TOCItems.iron_club),             Levels.SMITHING, 7, 70, new ItemStack(TOCItems.ingot_iron, 3)),
			new Recipe(new ItemStack(TOCItems.axe_iron),              Levels.SMITHING, 7, 30, new ItemStack(TOCItems.ingot_iron, 1)),
			new Recipe(new ItemStack(TOCItems.iron_short_sword),      Levels.SMITHING, 7, 60, new ItemStack(TOCItems.ingot_iron, 2)),
			new Recipe(new ItemStack(TOCItems.arrowhead_iron, 4),     Levels.SMITHING, 11, 40, new ItemStack(TOCItems.ingot_iron, 1)),
			new Recipe(new ItemStack(TOCItems.iron_pickaxe),          Levels.SMITHING, 15, 90, new ItemStack(TOCItems.ingot_iron, 3)),
			new Recipe(new ItemStack(TOCItems.iron_warhammer),        Levels.SMITHING, 19, 120, new ItemStack(TOCItems.ingot_iron, 4)),
			new Recipe(new ItemStack(TOCItems.iron_great_axe),        Levels.SMITHING, 23, 200, new ItemStack(TOCItems.ingot_iron, 6)),
			new Recipe(new ItemStack(TOCItems.iron_great_sword),      Levels.SMITHING, 23, 200, new ItemStack(TOCItems.ingot_iron, 6)),
			
			new Recipe(new ItemStack(TOCItems.steel_club),             Levels.SMITHING, 23, 160, new ItemStack(TOCItems.ingot_steel, 3)),
			new Recipe(new ItemStack(TOCItems.axe_steel),              Levels.SMITHING, 23, 80, new ItemStack(TOCItems.ingot_steel, 1)),
			new Recipe(new ItemStack(TOCItems.steel_short_sword),      Levels.SMITHING, 23, 140, new ItemStack(TOCItems.ingot_steel, 2)),
			new Recipe(new ItemStack(TOCItems.arrowhead_steel, 4),     Levels.SMITHING, 28, 100, new ItemStack(TOCItems.ingot_steel, 1)),
			new Recipe(new ItemStack(TOCItems.steel_pickaxe),          Levels.SMITHING, 33, 200, new ItemStack(TOCItems.ingot_steel, 3)),
			new Recipe(new ItemStack(TOCItems.steel_warhammer),        Levels.SMITHING, 38, 340, new ItemStack(TOCItems.ingot_steel, 4)),
			new Recipe(new ItemStack(TOCItems.steel_great_axe),        Levels.SMITHING, 43, 600, new ItemStack(TOCItems.ingot_steel, 6)),
			new Recipe(new ItemStack(TOCItems.steel_great_sword),      Levels.SMITHING, 43, 600, new ItemStack(TOCItems.ingot_steel, 6))
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
	
	public void queueRecipe(UUID uuid, Recipe recipe, int num) {
		if(playerCraftingQueue.containsKey(uuid)) cancelCraftingForPlayer(uuid);
		playerCraftingQueue.put(uuid, new ArrayList<CraftingTask>());
		
		CraftingTask task = new CraftingTask((TickableRecipe)recipe, uuid, num);
		playerCraftingQueue.get(uuid).add(task);
		
		TOCMain.serverTaskManager.addTask(task);
	}
	
	public void cancelCraftingForPlayer(UUID uuid) {
		if(playerCraftingQueue.containsKey(uuid)) {
			playerCraftingQueue.get(uuid).get(0).cancel();
			playerCraftingQueue.remove(uuid);
		}
	}
	
	public void playerFinishedCrafting(UUID uuid) {
		
	}
		
	public boolean craftRecipe(ServerPlayerEntity player, Recipe r) {
		if(!playerCraftingQueue.containsKey(player.getUniqueID())) return false;
		
		ItemStack[] itemsLeft = new ItemStack[r.components.length];
		for(int i = 0; i < itemsLeft.length; ++i) 
			itemsLeft[i] = new ItemStack(r.components[i].getItem(), r.components[i].getCount());
		
		for(int i = 0; i < itemsLeft.length; ++i) {
			int slot = 0;
			for(ItemStack stack : player.inventory.mainInventory) {
				if(stack.getItem().equals(itemsLeft[i].getItem())){
					player.inventory.getStackInSlot(slot).shrink(itemsLeft[i].getCount());
					itemsLeft[i].shrink(itemsLeft[i].getCount());
				}
				if(itemsLeft[i].getCount() == 0 || itemsLeft[i].isEmpty())
					break;
				++slot;
			}
		}
		MinecraftForge.EVENT_BUS.post(new ItemSmithedEvent(player, r.crafted.copy()));
		
		player.inventory.addItemStackToInventory(r.crafted.copy());
		
		Network.INSTANCE.sendTo(new ItemCraftedPKT(), player);
		TOCMain.pm.getPlayer(player).levels.addExp(r.level, r.xp);
		Network.INSTANCE.sendTo(new AddLevelXpPKT(r.level.name().toString(), r.xp), player);
		return true;
	}
	
	static RecipeManager instance;
	public static RecipeManager instance() {
		if(instance == null)
			instance = new RecipeManager();
		return instance;
	}
	
	public static class ItemSmithedEvent extends Event
	{		
		public PlayerEntity player;
		public ItemStack smithed;
		public ItemSmithedEvent(ServerPlayerEntity player, ItemStack smithed) {
			this.player = player;
			this.smithed = smithed;
		}
	}
}
