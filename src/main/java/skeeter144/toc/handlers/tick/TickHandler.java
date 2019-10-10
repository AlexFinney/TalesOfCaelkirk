package skeeter144.toc.handlers.tick;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.recipe.RecipeManager;
import skeeter144.toc.util.Reference;

@EventBusSubscriber(modid = Reference.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TickHandler {
	
	static int serverTicks = 0;
	@SubscribeEvent
	public static void serverTicked(ServerTickEvent e) {
		TOCMain.serverTaskManager.tickTasks(serverTicks);
		++serverTicks;
		if(serverTicks % 40 == 0) {
			RecipeManager.instance().craftRecipes();
		}
	}
	
	@SubscribeEvent
	public void clientTicked(ClientTickEvent e) {}
	
}
