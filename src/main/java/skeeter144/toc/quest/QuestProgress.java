package skeeter144.toc.quest;

import java.io.Serializable;

public class QuestProgress implements Serializable{
	public boolean finished = false;
	public int stage = 1;
	public int questId;
}
