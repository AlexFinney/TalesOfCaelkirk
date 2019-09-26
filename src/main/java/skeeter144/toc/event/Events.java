package skeeter144.toc.event;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skeeter144.toc.TOCMain;

public class Events {

	@SubscribeEvent
	public void displayDamage(LivingUpdateEvent event) {
		TOCMain.proxy.displayDamageDealt(event.getEntityLiving());
	}

	@SubscribeEvent
	public void displayEntityStatus(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != ElementType.CHAT) {
			return;
		}
		TOCMain.proxy.setEntityInCrosshairs();
	}
	
}
