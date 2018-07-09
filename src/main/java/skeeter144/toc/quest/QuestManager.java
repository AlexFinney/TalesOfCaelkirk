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

import net.minecraftforge.common.MinecraftForge;
import skeeter144.toc.quest.quests.ANewAdventureQuest;

public class QuestManager{

	public static Quest aNewAdventure;
	
	public static final HashMap<Integer, Quest> quests = new HashMap<Integer, Quest>();
	public static HashMap<UUID, ArrayList<QuestProgress>> playerQuestProgresses = new HashMap<UUID, ArrayList<QuestProgress>>();
	
	public static void initQuests() {
		loadQuestProgress();
	
		aNewAdventure = new ANewAdventureQuest("a_new_adventure", getNextId());
		
		addQuest(aNewAdventure);
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
				
				playerQuestProgresses = (HashMap<UUID, ArrayList<QuestProgress>>) ois.readObject();
				
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
		ArrayList<QuestProgress> l = playerQuestProgresses.get(uuid);
		if(l == null)
			return false;
		
		boolean found = false;
		for(QuestProgress qp : l) {
			if(qp.getClass().equals(c.getQuestProgressClass())) {
				if(qp.finished)
					return true;
				else
					return false;
			}
		}
		
		return false;
	}
	
	public static boolean hasPlayerStartedQuest(UUID uuid, Quest c) {
		
		ArrayList<QuestProgress> l = playerQuestProgresses.get(uuid);
		if(l == null)
			return false;
		
		for(QuestProgress qp : l) {
			if(qp.getClass().equals(c.getQuestProgressClass())) {
				return true;
			}
		}
		return false;
	}
	
	public static void startPlayerOnQuest(UUID uuid, Quest q) {
		ArrayList<QuestProgress> progresses = QuestManager.playerQuestProgresses.get(uuid);
		if(progresses == null)
			progresses = new ArrayList<QuestProgress>();
		
		progresses.add(q.getNewQuestProgressInstance());
		playerQuestProgresses.put(uuid, progresses);
	}
	
	public static QuestProgress getQuestProgressForPlayer(UUID uuid, Quest theQuest) {
		List<QuestProgress> progresses = QuestManager.playerQuestProgresses.get(uuid);
		if(progresses == null)
			return null;
		
		for(QuestProgress qp : progresses) {
			if(qp.getClass().equals(theQuest.getQuestProgressClass())) {
				return qp;
			}
		}
		return null;
	}
	
	private static int nextId = 0;
	private static int getNextId() {
		return nextId++;
	}
}
