package skeeter144.toc.skills;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;

public class Crafting {

	Map<Item, Integer> itemLevelRequirements = new HashMap<Item, Integer>();
	Map<Item, Integer> itemXpRewards = new HashMap<Item, Integer>();
	
	public Crafting() {
		registerCraftingResult(TOCItems.stick_oak, 1, 3);
		
		registerCraftingResult(TOCItems.oak_shortbow_unstrung, 1, 8);
		registerCraftingResult(TOCItems.oak_bow_short, 1, 10);

		registerCraftingResult(TOCItems.oak_longbow_unstrung, 2, 12);
		registerCraftingResult(TOCItems.oak_bow_long, 2, 14);
	}
	
	@SubscribeEvent
	public void itemCraftedEvent(ItemCraftedEvent e) {
		if(e.player.world.isRemote)
			return;
	
		Item i = e.crafting.getItem();
		int levelReq = itemLevelRequirements.get(i);
		if(levelReq == -1) {
			System.err.println("Error: Item " + i + " is not registered in the crafting manager.");
			e.setCanceled(true);
			return;
		}
		TOCPlayer p = TOCMain.pm.getPlayer(e.player.getPersistentID());
		System.out.println(p.getPlayerLevels().getLevel(Levels.CRAFTING).getXp());
		if(p.levels.getLevel(Levels.CRAFTING).getLevel() < levelReq) {
			e.player.sendMessage(new TextComponentString("You must be Crafting Level " + levelReq + " to craft that!"));
			e.crafting.setCount(0);
			
			return;
		}
		
		int xpGiven = getXpFromItemCraft(e.crafting.getItem());
		p.levels.addExp(Levels.CRAFTING, xpGiven);
		Network.INSTANCE.sendTo(new AddLevelXpMessage("Crafting", xpGiven * 10), (EntityPlayerMP) e.player);
	}
	
	int getCraftingLevelRequiredFor(Item i) {
		return itemLevelRequirements.get(i) == null ? -1 : itemLevelRequirements.get(i);
	}
	
	int getXpFromItemCraft(Item i) {
		return itemXpRewards.get(i) == null ? -1 : itemXpRewards.get(i);
	}
	
	void registerCraftingResult(Item item, int levelReq, int xp) {
		itemXpRewards.put(item, xp);
		itemLevelRequirements.put(item, levelReq);
	}
}
