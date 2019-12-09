package skeeter144.toc.handlers;

import java.util.ArrayList;

import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ItemPrice;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.util.Reference;
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PlayerInventoryHandler {

	@SubscribeEvent
	public static void addCoinsToInventory(CoinsAddedToInventoryEvent e) {
		PlayerEntity player = (PlayerEntity)e.getEntityLiving();
		handleCoinsAdded(player, e.amount, true);
	}
	
	@SubscribeEvent
	public static void playerPickUpItem(EntityItemPickupEvent e) {
		Item i = e.getItem().getItem().getItem();
		if(i.equals(TOCItems.copper_coin)  || i.equals(TOCItems.silver_coin) || i.equals(TOCItems.gold_coin)) {
			handleCoinsAdded(e.getPlayer(), null, false);
		}
		
	}
	
	private static void handleCoinsAdded(PlayerEntity player, ItemPrice coins, boolean shouldAdd) {
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
		public CoinsAddedToInventoryEvent(LivingEntity entity, ItemPrice amount) {
			super(entity);
			this.amount = amount;
		}
		
	}
	
	public static class ItemAddedToInventoryEvent extends LivingEvent{
		public ItemStack stack;
		public ItemAddedToInventoryEvent(PlayerEntity entity, ItemStack stack) {
			super(entity);
			this.stack = stack;
		}
	}
}
