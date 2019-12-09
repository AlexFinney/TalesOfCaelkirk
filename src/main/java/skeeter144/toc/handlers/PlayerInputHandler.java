package skeeter144.toc.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import skeeter144.toc.client.Keybindings;
import skeeter144.toc.client.gui.CustomPlayerGui;
import skeeter144.toc.client.gui.LevelsGui;
import skeeter144.toc.client.gui.SpellBookGUI;
import skeeter144.toc.event.KeyPressedEvent;
import skeeter144.toc.event.KeyReleasedEvent;
import skeeter144.toc.util.CustomKey;
import skeeter144.toc.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PlayerInputHandler {
	
	@SubscribeEvent
	public static void clientTick(ClientTickEvent e) {
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
	public static void keyPressed(KeyPressedEvent e) {
		if(Keybindings.SPELLBOOK_KEYBIND.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(null);
			//TODO: open gui
			Minecraft.getInstance().displayGuiScreen(new SpellBookGUI(new StringTextComponent("Spell Book")));
			return;
		}else if(Keybindings.LEVELS_KEYBIND.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(new LevelsGui(new StringTextComponent("Levels")));
			return;
		}else if(Keybindings.INVENTORY_KEYBIND.isPressed()) {
			if(Minecraft.getInstance().currentScreen instanceof CustomPlayerGui)
				 Minecraft.getInstance().displayGuiScreen(null);
			//TODO
			//else 
			//	Minecraft.getInstance().displayGuiScreen(new CustomPlayerGui(Minecraft.getInstance().player));
		}
	}
	
	@SubscribeEvent
	public void keyReleased(KeyReleasedEvent e) {}
	
}
