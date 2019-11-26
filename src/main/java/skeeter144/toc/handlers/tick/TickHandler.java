package skeeter144.toc.handlers.tick;

import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import skeeter144.toc.TOCMain;
import skeeter144.toc.util.Reference;

@EventBusSubscriber(modid = Reference.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TickHandler {
	
	static int serverTicks = 0;
	@SubscribeEvent
	public void serverTicked(ServerTickEvent e) {
		TOCMain.serverTaskManager.tickTasks();
		++serverTicks;
	}
	
	@SubscribeEvent
	public void clientTicked(ClientTickEvent e) {}
	
}
