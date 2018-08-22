package skeeter144.toc.quest;

import java.io.Serializable;

public class QuestProgress implements Serializable{
	public String name = "";
	public boolean started = false;
	public boolean completed = false;
	public int stage = 0;
	public int qp1 = 0;
	public int qp2 = 0;
	public int qp3 = 0;
	public int qp4 = 0;
	public int questId;
	
	public void incStage() {
		++stage;
		qp1 = 0;
		qp2 = 0; 
		qp3 = 0; 
		qp4 = 4;
	}
}
