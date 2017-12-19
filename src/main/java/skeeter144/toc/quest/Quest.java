package skeeter144.toc.quest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;

public abstract class Quest implements Serializable{
	public final String name;
	public final int id;
	protected Map<Levels, Integer> experienceRewards = new HashMap<Levels, Integer>();
	protected Map<Item, Integer> itemRewards = new HashMap<Item, Integer>();
	protected int currentStage = 0;
	public final Map<Integer, NpcDialog> questDialogs = new HashMap<Integer, NpcDialog>();
	public Quest(String name, int id) {
		this.name = name;
		this.id = id;
		createQuestDialogs();
	}
	
	public void onQuestFinished(EntityPlayerMP player) {
		EntityLevels l = TOCMain.pm.getPlayer(player.getUniqueID()).levels;
		boolean combatLeveled = false;
		for(Map.Entry<Levels, Integer> pair : experienceRewards.entrySet()) {
			boolean leveled = l.addExp(pair.getKey(), pair.getValue());
			
			if(leveled && pair.getKey().equals(Levels.ATTACK) || pair.getKey().equals(Levels.DEFENSE) || pair.getKey().equals(Levels.STRENGTH)
					|| pair.getKey().equals(Levels.MAGIC) || pair.getKey().equals(Levels.HITPOINTS))
				combatLeveled = true;
				
			if(combatLeveled)
				TOCMain.pm.getPlayer(player.getUniqueID()).combatLeveledUp();
			
			Network.INSTANCE.sendTo(new AddLevelXpMessage(pair.getKey().name(), pair.getValue()), player);
		}
		
		
		
		
		for(Map.Entry<Item, Integer> pair : itemRewards.entrySet()) {
			player.addItemStackToInventory(new ItemStack(pair.getKey(), pair.getValue()));
		}
		
		questFinished(player);
	}
	
	
	
	public abstract boolean canPlayerStartQuest(TOCPlayer player);
	public abstract QuestProgress getNewQuestProgressInstance();
	public abstract Class<? extends QuestProgress> getQuestProgressClass();
	protected abstract void questFinished(EntityPlayerMP player);
	protected abstract void createQuestDialogs();
	public abstract void handleDialogResponse(EntityPlayerMP player, UUID questGiver, int dialogId, int responseIndex);
}
