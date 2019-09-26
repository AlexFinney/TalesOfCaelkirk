package skeeter144.toc.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ItemPrice;
import skeeter144.toc.handlers.PlayerInventoryHandler.CoinsAddedToInventoryEvent;
import skeeter144.toc.items.TOCItems;

public class CurrencyOperations {
	
	public static ItemPrice add(ItemPrice a, ItemPrice b) {
		int copper = a.copper + b.copper;
		int carryOver = 0;
		if(copper >= 50) {
			carryOver = 1;
			copper -= 50;
		}
		
		int silver = a.silver + b.silver + carryOver;
		if(silver >= 50) {
			carryOver = 1;
			silver -= 50;
		}else
			carryOver = 1;
		
		int gold = a.gold + b.gold + carryOver;

		return new ItemPrice(gold, silver, copper);
	}
	
	public static ItemPrice subtract(ItemPrice a, ItemPrice b) {
		return convertCopperToAll(convertAllToCopper(a) - convertAllToCopper(b));
	}
	
	public static ItemPrice multiply(ItemPrice a, int amt) {
		return convertCopperToAll(convertAllToCopper(a) * amt);
	}
	
	public static int convertAllToCopper(ItemPrice amt) {
		return amt.gold * 2500 + amt.silver * 50 + amt.copper;
	}
	
	public static ItemPrice convertCopperToAll(int amt) {
		int rem = amt;
		int g, s,c;
		
		c = rem % 50;
		rem -= c;
		
		s = rem / 50;
		rem = s;
		
		g = rem / 50;
		s = s % 50;
		
		return new ItemPrice(g, s, c);
	}
	
	public static boolean doesPlayerHaveEnoughMoney(EntityPlayer pl, ItemPrice amt) {
		int plG = 0, plS = 0, plC = 0;
		
		List<ItemStack> plItems = new ArrayList<ItemStack>();
		
		plItems.addAll(pl.inventory.mainInventory);
		plItems.addAll(pl.inventory.offHandInventory);
		
		for(ItemStack is : plItems) {
			//TODO
//			if(is.getItem().equals(TOCItems.gold_coin))
//				plG += is.getCount();
//			if(is.getItem().equals(TOCItems.silver_coin))
//				plS += is.getCount();
//			if(is.getItem().equals(TOCItems.copper_coin))
//				plC += is.getCount();
		}
		
		int playerTotal = plG * 2500 + plS * 50 + plC;
		int priceTotal = amt.gold * 2500 + amt.silver * 50 + amt.copper;
		
		return playerTotal >= priceTotal;
	}
	
	public static void subtractMoneyFromPlayer(EntityPlayer pl, ItemPrice amt) {
		int plG = 0, plS = 0, plC = 0;
		List<ItemStack> plItems = new ArrayList<ItemStack>();
		List<ItemStack> plCoins = new ArrayList<ItemStack>();

		plItems.addAll(pl.inventory.mainInventory);
		plItems.addAll(pl.inventory.offHandInventory);
		for(ItemStack is : plItems) {
//			if(is.getItem().equals(TOCItems.gold_coin) || is.getItem().equals(TOCItems.silver_coin) || is.getItem().equals(TOCItems.copper_coin))
//				plCoins.add(is);
		}
		
		for(ItemStack is : plCoins) {
//			if(is.getItem().equals(TOCItems.gold_coin))
//				plG += is.getCount();
//			if(is.getItem().equals(TOCItems.silver_coin))
//				plS += is.getCount();
//			if(is.getItem().equals(TOCItems.copper_coin))
//				plC += is.getCount();
			is.setCount(0);
		}
		
		int playerTotal = convertAllToCopper(new ItemPrice(plG, plS, plC));
		playerTotal -= convertAllToCopper(amt);
		
		MinecraftForge.EVENT_BUS.post(new CoinsAddedToInventoryEvent(pl, convertCopperToAll(playerTotal)));
		
	}
}
