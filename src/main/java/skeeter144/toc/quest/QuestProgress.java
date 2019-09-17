package skeeter144.toc.quest;

import java.util.UUID;

public class QuestProgress {
	public UUID playerId;
	public int questId;
	public int stage = 0;
	public int qp1 = 0;
	public int qp2 = 0;
	public int qp3 = 0;
	
	public QuestProgress(UUID playerId, int questId, int stage, int qp1, int qp2, int qp3) {
		this.playerId = playerId;
		this.questId = questId;
		this.stage = stage;
		this.qp1 = qp1;
		this.qp2 = qp2;
		this.qp3 = qp3;
	}
	
	public void incStage() {
		++stage;
		qp1 = 0;
		qp2 = 0; 
		qp3 = 0;
		QuestManager.updateQuestProgressForPlayer(playerId, this);
	}
}
