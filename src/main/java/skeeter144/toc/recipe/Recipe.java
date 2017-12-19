package skeeter144.toc.recipe;

import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import skeeter144.toc.player.EntityLevels.Levels;

public class Recipe {
	public final ItemStack crafted;
	public final ItemStack[] components;
	public final Levels level;
	public final int xp;
	public Recipe(ItemStack crafted, Levels level, int xp, ItemStack ...components) {
		this.crafted = crafted;
		this.components = components;
		this.level = level;
		this.xp = xp;
	}
	
	public boolean canRecipeBeCrafted(IInventory inv) {
		return canRecipeBeCraftedNumberTimes(inv, 1);
	}
	
	public boolean canRecipeBeCraftedNumberTimes(IInventory inv, int numTimes) {
		HashMap<Item, Integer> foundItems = new HashMap<Item, Integer>();
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			if(foundItems.get(inv.getStackInSlot(i).getItem()) == null)
				foundItems.put(inv.getStackInSlot(i).getItem(), inv.getStackInSlot(i).getCount());
			else
				foundItems.put(inv.getStackInSlot(i).getItem(), foundItems.get(inv.getStackInSlot(i).getItem()) + inv.getStackInSlot(i).getCount());
		}
		
		for(ItemStack s : components) {
			if(foundItems.get(s.getItem()) == null || foundItems.get(s.getItem()) < s.getCount() * numTimes)
				return false;
		}
		return true;
	}
	
	public int numberCanBeCrafted(IInventory inv) {
		HashMap<Item, Integer> foundItems = new HashMap<Item, Integer>();
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			if(foundItems.get(inv.getStackInSlot(i).getItem()) == null)
				foundItems.put(inv.getStackInSlot(i).getItem(), inv.getStackInSlot(i).getCount());
			else
				foundItems.put(inv.getStackInSlot(i).getItem(), foundItems.get(inv.getStackInSlot(i).getItem()) + inv.getStackInSlot(i).getCount());
		}
		
		boolean ranOut = false;
		int numberCrafted = 0;
		while(!ranOut) {
			for(ItemStack s : components) {
				if(foundItems.get(s.getItem()) == null || foundItems.get(s.getItem()) < s.getCount()) {
					ranOut = true;
					break;
				}else {
					foundItems.put(s.getItem(), foundItems.get(s.getItem()) - s.getCount());
				}
			}
			if(!ranOut)
				++numberCrafted;
		}
		
		
		return numberCrafted;
	}
	
}
