package skeeter144.toc.quest;

import java.util.ArrayList;

import skeeter144.toc.client.gui.NpcDialogResponse;

public class NpcDialog {
	public final String dialogText;
	public final ArrayList<NpcDialogResponse> playerResponses;
	public final String dialogName;
	
	public NpcDialog(String name, String dialogText, ArrayList<NpcDialogResponse> responses) {
		this.dialogName = name;
		this.dialogText = dialogText;
		playerResponses = responses;
	}
}
