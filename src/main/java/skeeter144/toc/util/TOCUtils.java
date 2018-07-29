package skeeter144.toc.util;

import mapwriter.Mw;
import mapwriter.config.WorldConfig;
import mapwriter.map.IconMarker;
import mapwriter.map.Marker;
import mapwriter.map.MarkerManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class TOCUtils {
	
	public static int getItemCountInInventory(Item item, IInventory inv) {
		int count = 0;
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			if(inv.getStackInSlot(i).getItem().equals(item))
				count += inv.getStackInSlot(i).getCount();
		}
		return count;
	}
	
	public static void removeItemsFromInventory(Item item, int count, IInventory inv) {
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack is = inv.getStackInSlot(i);
			if(!is.getItem().equals(item))
				continue;
			
			if(is.getCount() > 0) {
				if(is.getCount() > count) {
					is.setCount(is.getCount() - count);
					return;
				}else {
					count -= is.getCount();
					is.setCount(0);
				}
			}
			
			if(count == 0)
				return;
		}
	}
	
	public static void removeIconMarkerFromMap(String name, BlockPos pos, int dim) {
		if(!Mw.getInstance().markerManager.delMarker(name, "all")) {
			Marker toDelete = null;
			for(Marker m : Mw.getInstance().markerManager.markerList) {
				if(m.x == pos.getX() && m.z == pos.getZ()) {
					toDelete = m;
					break;
				}
			}
			Mw.getInstance().markerManager.delMarker(toDelete);
		}
		Mw.getInstance().markerManager.save(WorldConfig.getInstance().worldConfiguration, mapwriter.util.Reference.catMarkers);	
		Mw.getInstance().markerManager.update();
	}
	
	public static void addIconMarkerToMap(String name, ResourceLocation texture, BlockPos pos, int dim) {
	//	Mw.getInstance().markerManager.addMarker(new IconMarker(name, texture, pos.getX(), pos.getY(), pos.getZ(), dim));
	//	Mw.getInstance().markerManager.save(WorldConfig.getInstance().worldConfiguration, mapwriter.util.Reference.catMarkers);	
	//	Mw.getInstance().markerManager.update();
	}
}
