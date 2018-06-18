package skeeter144.toc.quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.NpcDialogResponse;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.proxy.CommonProxy;
import skeeter144.toc.player.TOCPlayer;

public abstract class Quest implements Serializable{
	public String name;
	public final int id;
	protected Map<Levels, Integer> experienceRewards = new HashMap<Levels, Integer>();
	protected Map<Item, Integer> itemRewards = new HashMap<Item, Integer>();
	protected int currentStage = 0;
	public final Map<String, NpcDialog> questDialogs = new HashMap<String, NpcDialog>();

	public Quest(String questFile, int id) {
		this.id = id;
		
		parseQuestFile(questFile + ".json");
	}
	
	private void parseQuestFile(String questFileName) {
		InputStream is = CommonProxy.class.getResourceAsStream("/assets/toc/quests/" + questFileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		String s = "";
		try {
			while(reader.ready()) {
				s += reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(s);
		
		String questName = obj.get("name").getAsString();
		
		name = questName;
		
		JsonObject dialogues = (JsonObject)obj.get("dialogues");
		for(Entry<String, JsonElement> entry : dialogues.entrySet()) {
			String dialogueName = entry.getKey();
			
			JsonObject dialogue = (JsonObject)entry.getValue();
			String text = dialogue.get("text").getAsString();
			
			JsonObject responses = (JsonObject)dialogue.get("responses");
			
			ArrayList<NpcDialogResponse> dialogResponses = new ArrayList<NpcDialogResponse>();
			for(Entry<String, JsonElement> responseEntry : responses.entrySet()) {
				String responseName = responseEntry.getKey();
				String dialogTransition = ((JsonObject)responseEntry.getValue()).get("name").getAsString();
				
				JsonElement element = ((JsonObject)responseEntry.getValue()).get("event");
				String serverFunc = "";
				if(element != null)
					serverFunc = element.getAsString();
				
				dialogResponses.add(new NpcDialogResponse(responseName, dialogTransition, serverFunc));
			}
			
			NpcDialog npcDialog = new NpcDialog(dialogueName, text, dialogResponses);
			questDialogs.put(npcDialog.dialogName, npcDialog);
		}
		
		for(NpcDialog dialog : questDialogs.values()) {
			for(NpcDialogResponse response : dialog.playerResponses) {
				response.transitionDialog = questDialogs.get(response.dialogueTransition);
			}
		}
	}

	public void onQuestFinished(EntityPlayerMP player) {
		EntityLevels l = TOCMain.pm.getPlayer(player).levels;
		boolean combatLeveled = false;
		for(Map.Entry<Levels, Integer> pair : experienceRewards.entrySet()) {
			boolean leveled = l.addExp(pair.getKey(), pair.getValue());
			
			if(leveled && pair.getKey().equals(Levels.ATTACK) || pair.getKey().equals(Levels.DEFENSE) || pair.getKey().equals(Levels.STRENGTH)
					|| pair.getKey().equals(Levels.MAGIC) || pair.getKey().equals(Levels.HITPOINTS))
				combatLeveled = true;
				
			if(combatLeveled)
				TOCMain.pm.getPlayer(player).combatLeveledUp();
			
			Network.INSTANCE.sendTo(new AddLevelXpMessage(pair.getKey().name(), pair.getValue()), player);
		}
		
		
		
		
		for(Map.Entry<Item, Integer> pair : itemRewards.entrySet()) {
			player.addItemStackToInventory(new ItemStack(pair.getKey(), pair.getValue()));
		}
		
		questFinished(player);
	}
	
	
	
	public abstract boolean canPlayerStartQuest(TOCPlayer player);
	public abstract QuestProgress getNewQuestProgressInstance();
	public abstract Class<? extends QuestProgress> getQuestProgressClass();
	protected abstract void questFinished(EntityPlayerMP player);
}
