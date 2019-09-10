package skeeter144.toc.client;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import skeeter144.toc.util.CustomKey;

public class Keybindings {
	public static List<CustomKey> keyBinds;
	public static CustomKey SPELLBOOK_KEYBIND;
	public static CustomKey LEVELS_KEYBIND;
	public static CustomKey INVENTORY_KEYBIND;
	public static CustomKey MOUNT_FLY_UP;
	public static CustomKey MOUNT_FLY_DOWN;
	
	public static void registerKeybinds() {
		keyBinds = new ArrayList<CustomKey>();
		
		// instantiate the key bindings
		SPELLBOOK_KEYBIND = new CustomKey("key.magic.opengui", Keyboard.KEY_M, "key.toc.category");
		LEVELS_KEYBIND = new CustomKey("key.levels.opengui", Keyboard.KEY_L, "key.toc.category");
		INVENTORY_KEYBIND = new CustomKey("key.inventory.opengui", Keyboard.KEY_E, "key.toc.category");
		MOUNT_FLY_UP = new CustomKey("key.mount_controls.fly_up", Keyboard.KEY_R, "key.toc.category");
		MOUNT_FLY_DOWN = new CustomKey("key.mount_controls.fly_down", Keyboard.KEY_F, "key.toc.category");
		
		
		keyBinds.add(SPELLBOOK_KEYBIND);
		keyBinds.add(LEVELS_KEYBIND);
		keyBinds.add(INVENTORY_KEYBIND);
		keyBinds.add(MOUNT_FLY_UP);
		keyBinds.add(MOUNT_FLY_DOWN);
		
		
		for(CustomKey c : keyBinds)
			ClientRegistry.registerKeyBinding(c);
		
		int i = 0;
		for(KeyBinding kb : Minecraft.getMinecraft().gameSettings.keyBindings) {
			if(kb.equals(Minecraft.getMinecraft().gameSettings.keyBindInventory)) {
				Minecraft.getMinecraft().gameSettings.keyBindInventory = new KeyBinding("key.inventory", Keyboard.KEY_V, "key.categories.inventory");
				Minecraft.getMinecraft().gameSettings.keyBindings[i] = Minecraft.getMinecraft().gameSettings.keyBindInventory;
				break;
			}else if(kb.equals(Minecraft.getMinecraft().gameSettings.keyBindAdvancements)) {
				Minecraft.getMinecraft().gameSettings.keyBindInventory = new KeyBinding("key.advancements", Keyboard.KEY_F18, "key.categories.misc");
				Minecraft.getMinecraft().gameSettings.keyBindings[i] = Minecraft.getMinecraft().gameSettings.keyBindInventory;
				break;
			}
			++i;
		}
	}
}
