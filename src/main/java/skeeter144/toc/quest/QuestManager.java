package skeeter144.toc.quest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.jline.utils.Log;

import net.minecraftforge.common.MinecraftForge;
import skeeter144.toc.data.Database;
import skeeter144.toc.quest.quests.ANewAdventureQuest;

public class QuestManager{
	static int next_id = 0;
	public static final String A_NEW_ADVENTURE = "A New Adventure";
	public static final HashMap<Integer, Quest> quests = new HashMap<Integer, Quest>();
	public static HashMap<UUID, HashMap<Integer, QuestProgress>> playerQuestProgresses = new HashMap<UUID, HashMap<Integer, QuestProgress>>();
	
	public static void initQuests() {
		
		addQuest(new ANewAdventureQuest(next_id));
		
		loadQuestProgress();
	
	}
	
	private static void addQuest(Quest q) {
		quests.put(q.id, q);
		Log.info("Quest Registered: " + q.name);
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
	
	public static void loadQuestProgress() {
		ArrayList<ArrayList<String>> rows = Database.executeQuery("select * from QuestProgress");
		if(rows == null) { System.err.println("Unable to retrieve rows from quest progress table"); return;}
		
		for(ArrayList<String> row : rows) {
			HashMap<Integer, QuestProgress> progress = new HashMap<>();
			progress.put(Integer.parseInt(row.get(1)), 
					new QuestProgress(UUID.fromString(row.get(0)), 
							Integer.parseInt(row.get(1)),
							Integer.parseInt(row.get(2)),
							Integer.parseInt(row.get(3)), 
							Integer.parseInt(row.get(4)), 
							Integer.parseInt(row.get(5))));
			
			playerQuestProgresses.put(UUID.fromString(row.get(0)), progress);
		}
		
		
		
//		File f = new File("quest_progress.bin");
//		if(f.exists()) {
//			try {
//				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
//				
//				playerQuestProgresses = (HashMap<UUID, HashMap<Integer, QuestProgress>>) ois.readObject();
//				
//				ois.close();
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
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
	
	public static QuestProgress startPlayerOnQuest(UUID uuid, String questName) {
		Quest q = null;
		HashMap<Integer, QuestProgress> map = playerQuestProgresses.get(uuid);
		if(map == null) {
			playerQuestProgresses.put(uuid, new HashMap<Integer, QuestProgress>());
			map = playerQuestProgresses.get(uuid);
		}
		q = questByName(questName);
		
		QuestProgress qp = map.get(q.id);
		if(qp == null) {
			qp = new QuestProgress(uuid, q.id, q.initialStep, 0, 0, 0);
			map.put(q.id, qp);
			Database.executeUpdate(String.format("insert into QuestProgress values ( \"%s\", %s, %s, %s, %s, %s )", 
					uuid.toString(),
					q.id,
					q.initialStep,
					0, 0, 0));
		}
		return qp;
	}
	
	public static Quest questByName(String questName) {
		for(Map.Entry<Integer, Quest> pair : quests.entrySet()) {
			if(pair.getValue().name.equalsIgnoreCase(questName))
				return pair.getValue();
		}
		Log.error("Quest " + questName + " not found when requested!");
		return null;
	}
	
	/**
	 * 
	 * @param uuid UUID Of the Player
	 * @param theQuest The Quest in question
	 * @return Returns the QuestProgress for the Player/Quest if it exists. Creates new instance if player hasn't started quest
	 */
	public static QuestProgress getQuestProgressForPlayer(UUID uuid, String questName) {
		
		HashMap<Integer, QuestProgress> qps = playerQuestProgresses.get(uuid);
		if(qps == null) {
			playerQuestProgresses.put(uuid, new HashMap<Integer, QuestProgress>());
			qps = playerQuestProgresses.get(uuid);
		}
		
		Quest q = null;
		for(Entry<Integer, Quest> quests : quests.entrySet()) {
			if(quests.getValue().name.equalsIgnoreCase(questName)) {
				q = quests.getValue();
				break;
			}
		}
		
		if(q == null) {Log.error("Quest " + questName + " is not a registered quest!"); return null;}
		
		QuestProgress progress = qps.get(q.id);
		if(progress == null) progress = new QuestProgress(uuid, q.id, q.initialStep, 0, 0, 0);
		return progress;
	}

	public static void updateQuestProgressForPlayer(UUID playerUUID, QuestProgress qp) {
		Database.executeUpdate(String.format("update QuestProgress set QuestID = %s, "
				+ "CurrentStep = %s, Counter1 = %s, "
				+ "Counter2 = %s, Counter3 = %s where UUID = \"%s\"", 
				qp.questId,
				qp.stage,
				qp.qp1, qp.qp2, qp.qp3, 
				playerUUID.toString()));
	}
}
