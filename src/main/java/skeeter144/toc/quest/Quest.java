package skeeter144.toc.quest;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.AddLevelXpPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;

public abstract class Quest {
	public String name;
	public final int id;
	public int initialStep;
	protected Map<Levels, Integer> experienceRewards = new HashMap<Levels, Integer>();
	protected Map<Item, Integer> itemRewards = new HashMap<Item, Integer>();
	public final Map<String, NpcDialog> questDialogs = new HashMap<String, NpcDialog>();

	public Quest(int id, String name, int initialStep) {
		this.id = id;
		this.name = name;
		this.initialStep = initialStep;
	}
	

	public void onQuestFinished(EntityPlayerMP player) {
		EntityLevels l = TOCMain.pm.getPlayer(player).levels;
		boolean combatLeveled = false;
		for(Map.Entry<Levels, Integer> pair : experienceRewards.entrySet()) {
			boolean leveled = l.addExp(pair.getKey(), pair.getValue());
			
			if(leveled && pair.getKey().equals(Levels.ATTACK) || pair.getKey().equals(Levels.DEFENSE) || pair.getKey().equals(Levels.STRENGTH)
					|| pair.getKey().equals(Levels.MAGIC) || pair.getKey().equals(Levels.HITPOINTS))
				combatLeveled = true;
				
			if(combatLeveled)
				TOCMain.pm.getPlayer(player).combatLeveledUp();
			
			Network.INSTANCE.sendTo(new AddLevelXpPKT(pair.getKey().name(), pair.getValue()), player);
		}
		
		
		for(Map.Entry<Item, Integer> pair : itemRewards.entrySet()) {
			player.addItemStackToInventory(new ItemStack(pair.getKey(), pair.getValue()));
		}
		
		questFinished(player);
	}
	
	public boolean canPlayerStartQuest(TOCPlayer player) {return false;}
	public abstract QuestProgress getNewQuestProgressInstance();
	public abstract Class<? extends QuestProgress> getQuestProgressClass();
	protected void questFinished(EntityPlayerMP player) {}
	
}
