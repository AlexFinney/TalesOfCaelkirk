package skeeter144.toc.util;

import net.minecraft.client.settings.KeyBinding;

public class CustomKey extends KeyBinding{
	
	public boolean wasPressedLastTick = false;
	public CustomKey(String description, int keyCode, String category) {
		super(description, keyCode, category);
	}

}
