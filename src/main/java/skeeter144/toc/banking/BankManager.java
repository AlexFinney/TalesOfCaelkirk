package skeeter144.toc.banking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import skeeter144.toc.banking.BankInventory.SerializableBankInventory;

public class BankManager {

	private static Map<UUID, BankInventory> playerInventories = new HashMap< UUID, BankInventory>();
	
	public static BankInventory getPlayerInventory(PlayerEntity player) {
		
		if(playerInventories.get(player.getUniqueID()) == null) 
			playerInventories.put(player.getUniqueID(), new BankInventory(new StringTextComponent("Bank Inventory"), 54));
		
		
		//playerInventories.get(player.getUniqueID()).setCustomName(player.getDisplayName().appendText("'s Bank"));
		return playerInventories.get(player.getUniqueID());
	}
	
	public static void loadInventories() {
		File f = new File("banks");
		if(!f.exists())
			f.mkdirs();
		
		for(String bankFileName : f.list()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f.getAbsolutePath() + "\\" + bankFileName));
				SerializableBankInventory inv = (SerializableBankInventory)ois.readObject();
				playerInventories.put(UUID.fromString(bankFileName), inv.toBankInventory());
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void saveInventories() {
		for(Map.Entry<UUID, BankInventory> entry : playerInventories.entrySet()) {
			
			File f = new File("banks/" + entry.getKey().toString());
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(new SerializableBankInventory(entry.getValue()));
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void savePlayerInventory(PlayerEntity player) {
		
	}

}
