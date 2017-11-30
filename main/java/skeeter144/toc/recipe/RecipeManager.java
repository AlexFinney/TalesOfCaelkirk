package skeeter144.toc.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import skeeter144.toc.TOCMain;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.ItemCraftedMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels.Levels;

public class RecipeManager {

	HashMap<UUID, List<Recipe>> playerCraftingQueue = new HashMap<UUID, List<Recipe>>();
	public final Recipe[] SMELTING_RECIPES = new Recipe[] {
			new Recipe(new ItemStack(TOCItems.ingot_bronze), Levels.SMITHING, 12, new ItemStack(TOCItems.ore_copper), new ItemStack(TOCItems.ore_tin)),
			new Recipe(new ItemStack(TOCItems.ingot_iron), Levels.SMITHING, 20,new ItemStack(TOCItems.ore_iron, 2)),
			new Recipe(new ItemStack(TOCItems.ingot_steel),Levels.SMITHING, 35, new ItemStack(TOCItems.ore_iron, 2), new ItemStack(TOCItems.ore_coal)),
			new Recipe(new ItemStack(TOCItems.ingot_gold), Levels.SMITHING, 15, new ItemStack(TOCItems.ore_gold)),
			new Recipe(new ItemStack(TOCItems.ingot_mithril), Levels.SMITHING, 80,new ItemStack(TOCItems.ore_mithril), new ItemStack(TOCItems.ore_coal, 3)),
			new Recipe(new ItemStack(TOCItems.ingot_adamantite), Levels.SMITHING, 130,new ItemStack(TOCItems.ore_adamantite), new ItemStack(TOCItems.ore_coal, 5)),
			new Recipe(new ItemStack(TOCItems.ingot_runite), Levels.SMITHING,  203, new ItemStack(TOCItems.ore_runite), new ItemStack(TOCItems.ore_coal, 7)),
			new Recipe(new ItemStack(TOCItems.ingot_dragonstone), Levels.SMITHING, 317, new ItemStack(TOCItems.ore_dragonstone), new ItemStack(TOCItems.ore_coal, 9))
			};
	
	
	public final Recipe[] SMITHING_RECIPES = new Recipe[] {
			new Recipe(new ItemStack(TOCItems.axe_bronze), Levels.SMITHING, 6, new ItemStack(TOCItems.ingot_bronze, 1)),
			new Recipe(new ItemStack(TOCItems.bronze_short_sword), Levels.SMITHING, 12, new ItemStack(TOCItems.ingot_bronze, 2)),
			new Recipe(new ItemStack(TOCItems.bronze_pickaxe), Levels.SMITHING, 18,new ItemStack(TOCItems.ingot_bronze, 3)),
			new Recipe(new ItemStack(TOCItems.bronze_club), Levels.SMITHING, 18,new ItemStack(TOCItems.ingot_bronze, 3)),
			new Recipe(new ItemStack(TOCItems.bronze_warhammer), Levels.SMITHING, 24, new ItemStack(TOCItems.ingot_bronze, 4)),
			new Recipe(new ItemStack(TOCItems.bronze_great_axe),Levels.SMITHING, 36, new ItemStack(TOCItems.ingot_bronze, 6)),
			new Recipe(new ItemStack(TOCItems.bronze_great_sword), Levels.SMITHING, 36, new ItemStack(TOCItems.ingot_bronze, 6))
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
			EntityPlayerMP player = (EntityPlayerMP) TOCMain.pm.getPlayer(entry.getKey()).mcEntity;
			Recipe r = entry.getValue().get(0);
			System.out.println("check");
			if(r.canRecipeBeCrafted(player.inventory)) {
				craftRecipe(player, r);
			}else {
				playerCraftingQueue.get(player.getUniqueID()).remove(r);
				if(playerCraftingQueue.get(player.getUniqueID()).size() == 0)
					playerCraftingQueue.remove(player.getUniqueID());
			}
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
		player.inventory.addItemStackToInventory(r.crafted.copy());
		playerCraftingQueue.get(player.getUniqueID()).remove(r);
		if(playerCraftingQueue.get(player.getUniqueID()).size() == 0)
			playerCraftingQueue.remove(player.getUniqueID());
		Network.INSTANCE.sendTo(new ItemCraftedMessage(), player);
		TOCMain.pm.getPlayer(player.getPersistentID()).levels.addExp(r.level, r.xp);
		Network.INSTANCE.sendTo(new AddLevelXpMessage(r.level.name().toString(), r.xp), player);
	}
	
	static RecipeManager instance;
	public static RecipeManager instance() {
		if(instance == null)
			instance = new RecipeManager();
		return instance;
	}
}
