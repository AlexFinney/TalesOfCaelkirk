package skeeter144.toc.quest;

import java.io.Serializable;
import java.util.UUID;

public class QuestProgress implements Serializable{
	private static final long serialVersionUID = -3429641809467634017L;
	
	public UUID playerId;
	public int questId;
	public int stage = 0;
	
	public QuestProgress(UUID playerId, int questId, int stage) {
		this.playerId = playerId;
		this.questId = questId;
		this.stage = stage;
	}
	
	public void incStage() {
		++stage;
		QuestManager.updateQuestProgressForPlayer(playerId, this);
	}
}
