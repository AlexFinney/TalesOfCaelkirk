package skeeter144.toc.entity.mob.npc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import skeeter144.toc.client.gui.NpcDialogResponse;
import skeeter144.toc.proxy.CommonProxy;
import skeeter144.toc.quest.NpcDialog;

public class DialogManager {

	public static Map<String/*class name*/, HashMap<String, NpcDialog>> npcDialogs;
	
	public static void init() {
		npcDialogs = new HashMap<String, HashMap<String, NpcDialog>>();
		
		URL url = CommonProxy.class.getResource("/assets/toc/npc_dialogues");
		File f = new File(url.getPath());
		try {
			if (f.isDirectory()) {
				for (File file : f.listFiles()) {
					HashMap<String, NpcDialog> dialogs = new HashMap<String, NpcDialog>();
					
					BufferedReader br = new BufferedReader(new FileReader(file));
					String s = "";
					try {
						while(br.ready()) {
							s += br.readLine();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					JsonParser parser = new JsonParser();
					JsonObject obj = (JsonObject) parser.parse(s);
					
					for(Entry<String, JsonElement> entry : obj.entrySet()) {
						String dialogueName = entry.getKey();
						
						JsonObject dialogue = (JsonObject)entry.getValue();
						String text = dialogue.get("text").getAsString();
						
						ArrayList<NpcDialogResponse> responsesAr = new ArrayList<NpcDialogResponse>();
						JsonObject responses = (JsonObject)dialogue.get("responses");
						for(Entry<String, JsonElement> response : responses.entrySet()) {
							String responseText = response.getKey();
							JsonObject responseElement =  (JsonObject)response.getValue();
							String transitionName = responseElement.get("transition").getAsString();
							
							JsonElement element = ((JsonObject)response.getValue()).get("event");
							String serverFunc = "";
							if(element != null)
								serverFunc = element.getAsString();
							
							
							NpcDialogResponse resp = new NpcDialogResponse(responseText, ((JsonObject)response.getValue()).get("transition").getAsString(), serverFunc);
							responsesAr.add(resp);
						}
						
						NpcDialog dialog = new NpcDialog(dialogueName, text, responsesAr);
						dialogs.put(dialogueName, dialog);
					}
					
					for(Entry<String, NpcDialog> entry : dialogs.entrySet()) {
						NpcDialog dialog = entry.getValue();
						for(NpcDialogResponse response : dialog.playerResponses) {
							response.transitionDialog = dialogs.get(response.dialogueTransition);
						}
					}
					
					String fName = file.getName();
					String[] temp = fName.split(".json");
					String name = temp[0];
					String[] name_parts = name.split("_");
					name = "Entity";
					for(String str : name_parts) {
						char c =  Character.toUpperCase(str.charAt(0));
						name += (c + str.substring(1, str.length()));
					}
					
					npcDialogs.put(name + ".class", dialogs);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
