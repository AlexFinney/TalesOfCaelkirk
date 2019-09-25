package skeeter144.toc.util;

import net.minecraft.client.Minecraft;

public class Mouse {

	public static int getX() {
		return (int) Minecraft.getInstance().mouseHelper.getMouseX();
	}
	
	public static int getY() {
		return (int) Minecraft.getInstance().mouseHelper.getMouseY();
	}
	
	public static boolean isButtonDown(int btn) {
		if(btn == 0) return Minecraft.getInstance().mouseHelper.isLeftDown();
		else if(btn == 1) return Minecraft.getInstance().mouseHelper.isRightDown();
		else return false;
	}
	
}
