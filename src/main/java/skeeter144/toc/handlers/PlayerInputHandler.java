package skeeter144.toc.handlers;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.Keybindings;
import skeeter144.toc.client.gui.CustomPlayerGui;
import skeeter144.toc.client.gui.Guis;
import skeeter144.toc.client.gui.LevelsGui;
import skeeter144.toc.event.KeyPressedEvent;
import skeeter144.toc.event.KeyReleasedEvent;
import skeeter144.toc.util.CustomKey;

public class PlayerInputHandler {
	
	@SubscribeEvent
	public void clientTick(ClientTickEvent e) {
		for(CustomKey c : Keybindings.keyBinds) {
			if(c.isKeyDown() && !c.wasPressedLastTick) { //got pressed this tick
				MinecraftForge.EVENT_BUS.post(new KeyPressedEvent(c));
				c.wasPressedLastTick = true;
			}else if(!c.isKeyDown() && c.wasPressedLastTick) { //got released this tick
				MinecraftForge.EVENT_BUS.post(new KeyReleasedEvent(c));
				c.wasPressedLastTick = false;
			}
		}
	}
	
	@SubscribeEvent
	public void keyPressed(KeyPressedEvent e) {
		if(Keybindings.SPELLBOOK_KEYBIND.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(null);
			Minecraft.getInstance().player.openGui(TOCMain.instance, Guis.SPELL_BOOK_GUI, Minecraft.getInstance().player.world, 0, 0, 0);
			return;
		}else if(Keybindings.LEVELS_KEYBIND.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(new LevelsGui());
			return;
		}else if(Keybindings.INVENTORY_KEYBIND.isPressed()) {
			if(Minecraft.getInstance().currentScreen instanceof CustomPlayerGui)
				 Minecraft.getInstance().displayGuiScreen(null);
			else 
				Minecraft.getInstance().displayGuiScreen(new CustomPlayerGui(Minecraft.getInstance().player));
		}
	}
	
	@SubscribeEvent
	public void keyReleased(KeyReleasedEvent e) {}
	
}
