package skeeter144.toc.quest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jline.utils.Log;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraftforge.common.MinecraftForge;
import skeeter144.toc.data.Database;
import skeeter144.toc.quest.quests.ANewAdventureQuest;

public class QuestManager{
	static int next_id = 0;
	public static Quest A_NEW_ADVENTURE;
	public static final HashMap<Integer, Quest> quests = new HashMap<Integer, Quest>();
	public static HashMap<UUID, HashMap<Integer, QuestProgress>> playerQuestProgresses = new HashMap<UUID, HashMap<Integer, QuestProgress>>();
	
	public static void initQuests() {
		
		addQuest(A_NEW_ADVENTURE = new ANewAdventureQuest(next_id));
		
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
		playerQuestProgresses = Database.loadAllQuestProgress();
		
		
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
			qp = new QuestProgress(uuid, q.id, q.initialStep);
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
	@SuppressWarnings("unchecked")
	public static <T extends QuestProgress> T getQuestProgressForPlayer(UUID uuid, Class<T> qpClass) {
		
		HashMap<Integer, QuestProgress> playersQuestProgress = playerQuestProgresses.get(uuid);
		if(playersQuestProgress == null) {
			playersQuestProgress = new HashMap<Integer, QuestProgress>();
			playerQuestProgresses.put(uuid, playersQuestProgress);
		}
		
		Quest q = null;
		for(Entry<Integer, Quest> quests : quests.entrySet()) {
			if(quests.getValue().getQuestProgressClass().equals(qpClass)) {
				q = quests.getValue();
				break;
			}
		}
		
		if(q == null) {Log.error("Quest with quest progess class " + qpClass + " is not a registered quest!"); return null;}
		T qp = null;
		try {
			qp = (T) playersQuestProgress.get(q.id);
			if(qp == null) { 
				qp = (T) q.getNewQuestProgressInstance();
				playersQuestProgress.put(q.id, qp);
			}
		}catch(Exception e) {
			System.out.println("An error occured tring to get quest with quest progress class " + qpClass + "\n");
			e.printStackTrace();
		}
		
		return qp;
	}
}
