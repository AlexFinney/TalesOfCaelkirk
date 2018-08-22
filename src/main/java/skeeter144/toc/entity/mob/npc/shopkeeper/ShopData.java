package skeeter144.toc.entity.mob.npc.shopkeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.item.Item;

public class ShopData implements Serializable{
	public ArrayList<ShopListing> listings =new ArrayList<ShopListing>();
	public UUID keepId;
	public ShopData() {}
	
	public ItemPrice getBuyPriceForItem(Item i) {
		for(ShopListing l : listings) {
			if(l.itemName.equalsIgnoreCase(i.getRegistryName().toString())) {
				return l.buyPrice;
			}
		}
		return null;
	}
	
	public ItemPrice getSellPriceForItem(Item i) {
		for(ShopListing l : listings) {
			if(l.itemName.equalsIgnoreCase(i.getRegistryName().toString())) {
				return l.sellPrice;
			}
		}
		return null;
	}
	
	
	public static class ShopListing implements Serializable{
		public final String itemName;
		public final ItemPrice buyPrice, sellPrice;
		public ShopListing(String itemName, ItemPrice buyPrice, ItemPrice sellPrice) {
			this.itemName = itemName;
			this.buyPrice = buyPrice;
			this.sellPrice = sellPrice;
		}
	}

	public static class ItemPrice implements Serializable{
		public final int gold;
		public final int silver;
		public final int copper;
		public ItemPrice(int g, int s, int c) {
			this.gold = g;
			this.silver = s;
			this.copper = c;
		}
	}
}
