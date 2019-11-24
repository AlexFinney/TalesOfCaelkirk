package skeeter144.toc.handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import skeeter144.toc.TOCMain;
import skeeter144.toc.data.Database;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SetClientTOCPlayerPKT;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.util.PlayerManager;
import skeeter144.toc.util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerLoginHandler {

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
	   PlayerEntity player = event.getPlayer();
		if(player.world.isRemote) {
		   return;
	   }
	   
       TOCPlayer tocPlayer = TOCMain.pm.getPlayer(player);
	   if(tocPlayer != null) {
		   ((ServerPlayerEntity)player).sendMessage(new StringTextComponent("Welcome back, traveller.  I see you haven't been eaten by a monster yet.  This is good..."), ChatType.CHAT);
	   }else {
		   ((ServerPlayerEntity)player).sendMessage(new StringTextComponent("Welome to the Land of Caelkirk, traveller.  So now, here begins your tale..."), ChatType.CHAT);
		   tocPlayer = Database.createPlayerInDatabase(player);
	   }
	   
	   
	   Network.INSTANCE.sendTo(new SetClientTOCPlayerPKT(tocPlayer), (ServerPlayerEntity)player);
	}
	
	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
	   PlayerEntity player = event.getPlayer();
		if(player.world.isRemote) {
		   return;
	   }
	   PlayerManager.instance().savePlayer(PlayerManager.instance().getPlayer(player));
	}
}