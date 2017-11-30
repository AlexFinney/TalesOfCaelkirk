package skeeter144.toc.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SetClientTOCPlayerMessage;
import skeeter144.toc.player.TOCPlayer;

public class PlayerLoginHandler {

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
	   if(event.player.world.isRemote) {
		   return;
	   }
	   
	   if(TOCMain.pm.loadPlayerFromFile(event.player.getUniqueID(), event.player)) {
		   event.player.sendMessage(new TextComponentTranslation("Welcome back, traveller.  I see you haven't been eaten by a monster yet.  This is good..."));
	   }else {
		   event.player.sendMessage(new TextComponentTranslation("Welome to the Land of Caelkirk, traveller.  So now, here begins your tale..."));
	   }
	   
	   TOCPlayer player = TOCMain.pm.getPlayer(event.player.getPersistentID());
	   Network.INSTANCE.sendTo(new SetClientTOCPlayerMessage(player), (EntityPlayerMP)event.player);
	   
	}
	
	
	
}