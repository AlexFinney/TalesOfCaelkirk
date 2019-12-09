package skeeter144.toc.quest;

import java.io.Serializable;
import java.util.UUID;

import skeeter144.toc.data.Database;

public class QuestProgress implements Serializable{
	private static final long serialVersionUID = -3429641809467634017L;
	
	public UUID playerId;
	public int questId;
	public int stage = 0;
	public boolean completed = false;
	
	public QuestProgress(UUID playerId, int questId, int stage) {
		this.playerId = playerId;
		this.questId = questId;
		this.stage = stage;
	}
	
	public void incStage() {
		++stage;
		//QuestManager.updateQuestProgressForPlayer(playerId, this);
	}
	
	public void save() {
		if(playerId != null) Database.saveQuestProgress(playerId, questId, this);
	}
}
