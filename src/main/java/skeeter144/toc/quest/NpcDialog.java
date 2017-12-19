package skeeter144.toc.quest;

public class NpcDialog {
	public final String npcDialog;
	public final String[] playerResponses;
	public NpcDialog(String dialog, String... responses) {
		npcDialog = dialog;
		playerResponses = responses;
	}
}
