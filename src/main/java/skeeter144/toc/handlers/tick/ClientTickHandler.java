package skeeter144.toc.handlers.tick;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.Keybindings;
import skeeter144.toc.entity.mob.mount.flying.EntityAbstractFlyingMount;
import skeeter144.toc.entity.mob.mount.flying.EntityFlyingController;

public class ClientTickHandler extends TickHandler{
	
	int clientTicks = 0;
	
	@Override
	public void clientTicked(ClientTickEvent e) {
		TOCMain.clientTaskManager.tickTasks();
		++clientTicks;
		
		Minecraft mc = Minecraft.getInstance();
		if(mc != null && mc.player != null && mc.player.isRidingHorse()) 
		{
			if(mc.player.getRidingEntity() instanceof EntityAbstractFlyingMount) 
			{
				boolean flyingUp = false;
				boolean flyingDown = false;
				 if(Keybindings.MOUNT_FLY_UP.isKeyDown() && Keybindings.MOUNT_FLY_DOWN.isKeyDown()) {
					 flyingUp = false;
					 flyingDown = false;
				 }else if(Keybindings.MOUNT_FLY_UP.isKeyDown()) {
					 flyingUp = true;
				 }else if(Keybindings.MOUNT_FLY_DOWN.isKeyDown()) {
					 flyingDown = true;
				 }
				 
				 if(EntityFlyingController.isClientFlyingDown != flyingDown || EntityFlyingController.isClientFlyingUp != flyingUp) {
					 EntityFlyingController.sendServerFlyingUpdate(flyingUp, flyingDown);
				 }
			}
		}
	}
}
