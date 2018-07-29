package skeeter144.toc.entity.mob.passive.questgiver;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.ShowQuestDialogMessage;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.quests.ANewAdventureQuest.ANewAdventureQuestProgress;

public class EntityKelvinWhitestone extends EntityNPCInteractable{
	
	public EntityKelvinWhitestone(World worldIn) {
		super(worldIn, QuestManager.aNewAdventure);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/kelvin_whitestone.png");
		
		this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(TOCItems.steel_pickaxe));
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		if(!qp.kelvinStarted) {
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "kelvin_intro"), (EntityPlayerMP)player);
		}else if(qp.swordSmithed) {
			Network.INSTANCE.sendTo(new ShowQuestDialogMessage(this.getUniqueID(), QuestManager.aNewAdventure.id, "kelvin_finished"), (EntityPlayerMP)player);
		}
		
		return true;
	}
	
	public void kelvinStart(UUID playerUUID) {
		EntityPlayer player = world.getPlayerEntityByUUID(playerUUID);
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		qp.kelvinStarted = true;
		player.addItemStackToInventory(new ItemStack(TOCItems.bronze_pickaxe));
		
		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.aNewAdventure.name + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Mine 5 copper and 5 tin ore, then return to Kelvin."));
	}
	
	public void kelvinFinished(UUID uuid) {
		EntityPlayer player = world.getPlayerEntityByUUID(uuid);
		ANewAdventureQuestProgress qp = (ANewAdventureQuestProgress)QuestManager.getQuestProgressForPlayer(player.getUniqueID(), QuestManager.aNewAdventure);
		
		qp.kelvinFinished = true;
		player.sendMessage(new TextComponentString(TextFormatting.BLUE  + "[" +  QuestManager.aNewAdventure.name + "] " + TextFormatting.GREEN + "[New Task]" + TextFormatting.WHITE + "Climb the ladder and head to the magic temple."));
		
		
	}
	
	
	
	
}
