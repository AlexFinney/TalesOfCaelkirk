package skeeter144.toc.client.gui;

import java.awt.geom.Rectangle2D;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.Keybindings;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.Level;
import skeeter144.toc.util.Mouse;
import skeeter144.toc.util.Reference;

public class LevelsGui extends Screen{

	public LevelsGui(ITextComponent titleIn) {
		super(titleIn);
	}

	ResourceLocation backgreoundImage = new ResourceLocation(Reference.MODID, "textures/gui/levels_background.png");

	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
	    
	    drawBookBackground();
	}
	
	boolean wasMouseClicked = false;
	static int bookWidth, bookHeight, bookX, bookY;
	private void drawBookBackground() {
	    GlStateManager.pushMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		
		tm.bindTexture(backgreoundImage);
		
		bookWidth = sr.getScaledWidth() / 2;
		bookHeight = (int)(bookWidth * .75f);
		bookX = sr.getScaledWidth() / 2 - bookWidth / 2 - 5;
		bookY = sr.getScaledHeight() / 10;
		
		blit(bookX, bookY, 0, 0, bookWidth, bookHeight, bookWidth, bookHeight);
		
		
		int baseX = bookX + bookWidth / 20;
		int baseY = bookY + 10;
		
		int iconDim = bookWidth / 12;
		int iconSpace = bookWidth / 30;
		
		FontRenderer fr = Minecraft.getInstance().fontRenderer;
		
		int count = 0;
		int row = 0;
		int col = 0;
		for(Level level : TOCMain.localPlayer.getPlayerLevels().getLevels()) {
			tm.bindTexture(new ResourceLocation("toc:textures/icons/levels/" + level.getName().toLowerCase() + "_icon.png"));
			int iconX = baseX + col * iconDim + iconSpace * col;
			int iconY =  baseY + row * iconDim + iconSpace / 2 * row;
			GlStateManager.color3f(1, 1, 1);
			blit(iconX, iconY, 0, 0, iconDim, iconDim, iconDim, iconDim);
			
			fr.drawString(level.getLevel() + "", iconX + iconDim - (int)(iconDim / 6f), iconY + iconDim - (int)(iconDim/2.5f), 0x000000);
			
			Rectangle2D.Float rect = new Rectangle2D.Float(iconX, iconY, iconDim, iconDim);
			int adjustedX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getInstance().mainWindow.getWidth();
			int adjustedY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getInstance().mainWindow.getHeight();
			
			if(rect.contains(adjustedX, adjustedY)) {
				if(!wasMouseClicked && Mouse.isButtonDown(0)) {
					wasMouseClicked = true;
					if(level.name.equalsIgnoreCase("mining")) {
						ItemStack is = new ItemStack(Items.WRITABLE_BOOK);
						CompoundNBT nbt = new CompoundNBT();
						nbt.putString("pages", "[adasfasf]");
						is.setTag(nbt);
						Minecraft.getInstance().player.inventory.addItemStackToInventory(is);
					}
					
				}
				
				String str = level.getName();
				adjustedX -= (fr.getStringWidth(str)) / 2 ;

				
				int x = (int)(bookX + bookWidth / 1.35f) - fr.getStringWidth(str) / 2;
				int y = (int)(bookY + bookHeight / 30);
				
				
				fr.drawString(str, x, y, 0x000000);
				
				
				int descBaseX = (int)(bookX + bookWidth / 1.9f);
				
				int descBaseY = (int)(bookY + bookHeight / 8);
				
				int gap = (int)(bookHeight / 16f);
				
				fr.drawString("Level: " + level.getLevel(), descBaseX, descBaseY, 0x000000);
				fr.drawString("Current Xp: " + level.getXp(), descBaseX, descBaseY + gap, 0x000000);
				fr.drawString("Xp Remaining: " + (EntityLevels.getExpForLevel(level.getLevel() + 1) - level.getXp()), descBaseX, descBaseY + 2 * gap, 0x000000);
				
				
				GlStateManager.color3f(1, 1, 1);
			}
			
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
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public boolean charTyped(char typedChar, int keyCode) {
		if(keyCode == Keybindings.LEVELS_KEYBIND.getKey().getKeyCode())
			Minecraft.getInstance().displayGuiScreen(null);
		return super.charTyped(typedChar, keyCode);
	}
}
