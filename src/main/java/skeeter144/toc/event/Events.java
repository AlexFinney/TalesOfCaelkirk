package skeeter144.toc.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.particles.particle.DamageParticle;

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
