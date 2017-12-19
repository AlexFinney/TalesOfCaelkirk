package skeeter144.toc.client.gui;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.Keybindings;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.Level;
import skeeter144.toc.util.Reference;

public class LevelsGui extends GuiScreen{

	ResourceLocation backgreoundImage = new ResourceLocation(Reference.MODID, "textures/gui/levels_background.png");

	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
	    super.drawScreen(mouseX, mouseY, partialTicks);
	   
	    
	    drawBookBackground();
	}
	
	boolean wasMouseClicked = false;
	static int bookWidth, bookHeight, bookX, bookY;
	private void drawBookBackground() {
	    GlStateManager.pushAttrib();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		TextureManager tm = Minecraft.getMinecraft().getTextureManager();
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		
		tm.bindTexture(backgreoundImage);
		
		bookWidth = sr.getScaledWidth() / 2;
		bookHeight = (int)(bookWidth * .75f);
		bookX = sr.getScaledWidth() / 2 - bookWidth / 2 - 5;
		bookY = sr.getScaledHeight() / 10;
		
		drawModalRectWithCustomSizedTexture(bookX, bookY, 0, 0, bookWidth, bookHeight, bookWidth, bookHeight);
		
		
		int baseX = bookX + bookWidth / 20;
		int baseY = bookY + 10;
		
		int iconDim = bookWidth / 12;
		int iconSpace = bookWidth / 30;
		
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		
		int count = 0;
		int row = 0;
		int col = 0;
		for(Level level : TOCMain.localPlayer.getPlayerLevels().getLevels()) {
			tm.bindTexture(new ResourceLocation("toc:textures/icons/levels/" + level.getName().toLowerCase() + "_icon.png"));
			int iconX = baseX + col * iconDim + iconSpace * col;
			int iconY =  baseY + row * iconDim + iconSpace / 2 * row;
			GlStateManager.color(1, 1, 1);
			drawModalRectWithCustomSizedTexture(iconX, iconY, 0, 0, iconDim, iconDim, iconDim, iconDim);
			
			fr.drawString(level.getLevel() + "", iconX + iconDim - (int)(iconDim / 6f), iconY + iconDim - (int)(iconDim/2.5f), 0x000000, false);
			
			Rectangle2D.Float rect = new Rectangle2D.Float(iconX, iconY, iconDim, iconDim);
			int adjustedX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
			int adjustedY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight;
			
			if(rect.contains(adjustedX, adjustedY)) {
				if(!wasMouseClicked && Mouse.isButtonDown(0)) {
					wasMouseClicked = true;
				}
				
				String str = level.getName();
				adjustedX -= (fr.getStringWidth(str)) / 2 ;

				
				int x = (int)(bookX + bookWidth / 1.35f) - fr.getStringWidth(str) / 2;
				int y = (int)(bookY + bookHeight / 30);
				
				
				fr.drawString(str, x, y, 0x000000, false);
				
				
				int descBaseX = (int)(bookX + bookWidth / 1.9f);
				
				int descBaseY = (int)(bookY + bookHeight / 8);
				
				int gap = (int)(bookHeight / 16f);
				
				fr.drawString("Level: " + level.getLevel(), descBaseX, descBaseY, 0x000000, false);
				fr.drawString("Current Xp: " + level.getXp(), descBaseX, descBaseY + gap, 0x000000, false);
				fr.drawString("Xp Remaining: " + (EntityLevels.getExpForLevel(level.getLevel() + 1) - level.getXp()), descBaseX, descBaseY + 2 * gap, 0x000000, false);
				
				
				GlStateManager.color(1, 1, 1);
			}
			
		//	if(count == selectedIcon) {
		//		tm.bindTexture(new ResourceLocation(Reference.MODID, "textures/spells/selected_icon.png"));
		//		drawModalRectWithCustomSizedTexture(iconX, iconY, 0, 0, iconDim, iconDim, iconDim, iconDim);
				
		//	}
			count++;
			col = count % 4;
			if(count % 4 == 0) {
				row++;
				col = 0;
			}
		}
		
		if(wasMouseClicked && !Mouse.isButtonDown(0)) {
			wasMouseClicked = false;
		}
		
		GlStateManager.popAttrib();
	}
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keybindings.LEVELS_KEYBIND.getKeyCode())
			Minecraft.getMinecraft().displayGuiScreen(null);
		super.keyTyped(typedChar, keyCode);
	}
}
