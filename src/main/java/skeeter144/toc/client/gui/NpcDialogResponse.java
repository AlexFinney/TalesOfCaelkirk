package skeeter144.toc.client.gui;

import skeeter144.toc.quest.NpcDialog;

public class NpcDialogResponse {
 
	public final String responseName, dialogueTransition;
	public NpcDialog transitionDialog;
	public NpcDialogResponse(String responseName, String dialogueTransition) {
		this.responseName = responseName;
		this.dialogueTransition = dialogueTransition;
	}
}
