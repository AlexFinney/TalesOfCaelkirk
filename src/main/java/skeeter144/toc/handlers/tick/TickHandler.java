package skeeter144.toc.handlers.tick;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.recipe.RecipeManager;

public class TickHandler {
	
	int serverTicks = 0;
	
	@SubscribeEvent
	public void serverTicked(ServerTickEvent e) {
		TOCMain.serverTaskManager.tickTasks(serverTicks);
		++serverTicks;
		if(serverTicks % 40 == 0) {
			RecipeManager.instance().craftRecipes();
		}
	}
	
	@SubscribeEvent
	public void clientTicked(ClientTickEvent e) {}
	
}
