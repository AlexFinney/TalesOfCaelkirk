package skeeter144.toc.banking;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import skeeter144.toc.entity.mob.passive.banker.EntityBanker;

public class BankManager {

	private static Map<Class<? extends EntityBanker>, Map< UUID, BankInventory>> bankerDatabase = new HashMap<Class<? extends EntityBanker>, Map< UUID, BankInventory>>();
	
	public static BankInventory getPlayerInventory(EntityPlayer player, Class<? extends EntityBanker> bankerClass) {
		
		
		if(bankerDatabase.get(bankerClass) == null)
			bankerDatabase.put(bankerClass, new HashMap<UUID, BankInventory>());
		
		Map< UUID, BankInventory> playerInventories = bankerDatabase.get(bankerClass);
		
		if(playerInventories.get(player.getUniqueID()) == null) 
			playerInventories.put(player.getUniqueID(), new BankInventory("Bank Inventory", false, 54));
		
		
		playerInventories.get(player.getUniqueID()).setCustomName(player.getDisplayNameString());
		return playerInventories.get(player.getUniqueID());
	}

}
