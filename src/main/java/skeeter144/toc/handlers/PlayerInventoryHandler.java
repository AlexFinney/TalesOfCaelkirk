package skeeter144.toc.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.client.gui.BankGui;
import skeeter144.toc.entity.mob.passive.shopkeeper.ShopData.ItemPrice;
import skeeter144.toc.items.TOCItems;

public class PlayerInventoryHandler {

	@SubscribeEvent
	public void addCoinsToInventory(CoinsAddedToInventoryEvent e) {
		EntityPlayer player = (EntityPlayer)e.getEntityLiving();
		handleCoinsAdded(player, e.amount, true);
	}
	
	@SubscribeEvent
	public void playerPickUpItem(EntityItemPickupEvent e) {
		Item i = e.getItem().getItem().getItem();
		if(i.equals(TOCItems.copper_coin)  || i.equals(TOCItems.silver_coin) || i.equals(TOCItems.gold_coin))
			handleCoinsAdded(e.getEntityPlayer(), null, false);
		
	}
	
	private void handleCoinsAdded(EntityPlayer player, ItemPrice coins, boolean shouldAdd) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		if(shouldAdd) {
			player.inventory.addItemStackToInventory(new ItemStack(TOCItems.copper_coin, coins.copper));
			player.inventory.addItemStackToInventory(new ItemStack(TOCItems.silver_coin, coins.silver));
			player.inventory.addItemStackToInventory(new ItemStack(TOCItems.gold_coin, coins.gold));	
		}
		
		
		items.addAll(player.inventory.mainInventory);
		items.addAll(player.inventory.offHandInventory);
		for(ItemStack i : items) {
			if(i.getItem().equals(TOCItems.copper_coin) && i.getCount() == 50) {
				player.inventory.addItemStackToInventory(new ItemStack(TOCItems.silver_coin));
				for(ItemStack j : items) {
					if(j.getItem().equals(TOCItems.copper_coin) && j.getCount() > 0) {
						i.setCount(j.getCount());
						j.setCount(0);
						
					}
				}
			}
		}
		
		for(ItemStack i : items) {
			if(i.getItem().equals(TOCItems.silver_coin) && i.getCount() == 50) {
				player.inventory.addItemStackToInventory(new ItemStack(TOCItems.gold_coin));
				for(ItemStack j : items) {
					if(j.getItem().equals(TOCItems.silver_coin) && j.getCount() > 0) {
						i.setCount(j.getCount());
						j.setCount(0);
						
					}
				}
			}
		}
	}
	
	public static class CoinsAddedToInventoryEvent extends LivingEvent{
		ItemPrice amount;
		public CoinsAddedToInventoryEvent(EntityLivingBase entity, ItemPrice amount) {
			super(entity);
			this.amount = amount;
		}
		
	}
	
	public static class ItemAddedToInventoryEvent extends LivingEvent{
		public ItemStack stack;
		public ItemAddedToInventoryEvent(EntityPlayer entity, ItemStack stack) {
			super(entity);
			this.stack = stack;
		}
	}
}
