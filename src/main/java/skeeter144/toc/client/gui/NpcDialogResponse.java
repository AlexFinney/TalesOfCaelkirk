package skeeter144.toc.client.gui;

import skeeter144.toc.quest.NpcDialog;

public class NpcDialogResponse {
 
	public final String responseName, dialogueTransition, serverEventFunc;
	public NpcDialog transitionDialog;
	public NpcDialogResponse(String responseName, String dialogueTransition, String serverEventFunc) {
		this.responseName = responseName;
		this.dialogueTransition = dialogueTransition;
		this.serverEventFunc = serverEventFunc;
	}
}
