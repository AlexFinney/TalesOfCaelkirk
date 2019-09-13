package skeeter144.toc.quest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jline.internal.Log;
import net.minecraftforge.common.MinecraftForge;
import skeeter144.toc.data.Database;
import skeeter144.toc.quest.quests.ANewAdventureQuest;

public class QuestManager{

	public static Quest aNewAdventure;
	public static final HashMap<Integer, Quest> quests = new HashMap<Integer, Quest>();
	public static HashMap<UUID, HashMap<Integer, QuestProgress>> playerQuestProgresses = new HashMap<UUID, HashMap<Integer, QuestProgress>>();
	
	public static void initQuests() {
		loadQuestProgress();
	
		ArrayList<ArrayList<String>> rows = Database.executeQuery("select * from Quests");
		for(ArrayList<String> row : rows)
		{
			Quest q = new Quest(Integer.parseInt(row.get(0)), row.get(1));
			addQuest(q);
		}		
	}
	
	private static void addQuest(Quest q) {
		quests.put(q.id, q);
		MinecraftForge.EVENT_BUS.register(q);
	}
	
	public static int getIdFromQuestClass(Class<? extends Quest> questClass) {
		for(Map.Entry<Integer, Quest> entry : quests.entrySet()) {
			if(questClass.equals(entry.getValue().getClass())) {
				return entry.getKey().intValue();
			}
		}
		
		return -1;
	}
	
	public static Quest getQuestById(int id) {
		return quests.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public static void loadQuestProgress() {
		File f = new File("quest_progress.bin");
		if(f.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				
				playerQuestProgresses = (HashMap<UUID, HashMap<Integer, QuestProgress>>) ois.readObject();
				
				ois.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveQuestProgress() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("quest_progress.bin")));
			
			oos.writeObject(playerQuestProgresses);
			
			oos.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isPlayerOnQuest(UUID uuid, Quest q) {
		return hasPlayerStartedQuest(uuid, q) && !hasPlayerFinishedQuest(uuid, q);
	}
	
	
	public static boolean hasPlayerFinishedQuest(UUID uuid, Quest c) {
		HashMap<Integer, QuestProgress> map = playerQuestProgresses.get(uuid);
	
		if(map.get(c.id) == null)
			return false;
		
		
		
		return false;
	}
	
	public static boolean hasPlayerStartedQuest(UUID uuid, Quest c) {
		HashMap<Integer, QuestProgress> map = playerQuestProgresses.get(uuid);
		
		if(map == null || map.get(c.id) == null) {
			playerQuestProgresses.put(uuid, new HashMap<Integer, QuestProgress>());
			return false;
		}else {
			return true;
		}
	}
	
	public static void startPlayerOnQuest(UUID uuid, Quest q) {
		HashMap<Integer, QuestProgress> map = playerQuestProgresses.get(uuid);
		if(map == null) {
			playerQuestProgresses.put(uuid, new HashMap<Integer, QuestProgress>());
			map = playerQuestProgresses.get(uuid);
		}
		

		QuestProgress qp = map.get(q.id);
	//	if(qp == null)
	//		map.put(q.id, new QuestProgress());
	}
	
	/**
	 * 
	 * @param uuid UUID Of the Player
	 * @param theQuest The Quest in question
	 * @return Returns the QuestProgress for the Player/Quest if it exists. Creates new instance if player hasn't started quest
	 */
	public static QuestProgress getQuestProgressForPlayer(UUID uuid, String questName) {
		
		ArrayList<ArrayList<String>> rows = Database.executeQuery(String.format("select * from QuestProgress join Quests ON QuestProgress.QuestID = Quests.ID and Quests.Name = %s",
				questName));
		
		if(rows.size() == 0) {
			rows = Database.executeQuery(String.format("select * from Quests where Name = %s", questName));
			if(rows.size() == 0) Log.error("Unable to find quest entry in database with name \"" + questName + "\"");
			
			return null;
		}else {
			ArrayList<String> row = rows.get(0);
			return new QuestProgress(uuid, Integer.parseInt(row.get(1)), 
										   Integer.parseInt(row.get(2)), 
										   Integer.parseInt(row.get(3)),
										   Integer.parseInt(row.get(4)),
										   Integer.parseInt(row.get(5)));
		}
	}
	
	private static int nextId = 0;
	private static int getNextId() {
		return nextId++;
	}
}
