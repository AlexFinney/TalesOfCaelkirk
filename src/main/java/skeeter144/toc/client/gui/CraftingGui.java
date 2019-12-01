package skeeter144.toc.client.gui;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SetAnvilRecipePKT;
import skeeter144.toc.recipe.Recipe;

public abstract class CraftingGui extends Screen implements IGuiEventListener{
	
	protected CraftingGui(ITextComponent titleIn) {
		super(titleIn);
	}

	int totalCrafting = 0;
	int crafted = 0;
	boolean wasMouseClicked = false;
	int guiWidth, guiHeight, guiX, guiY;
	int iconDim;
	
	Recipe currentlyCrafting = null;
	Recipe selectedRecipe = null;
	Recipe[] allRecipes;
	ArrayList<Recipe> craftableRecipes = new ArrayList<Recipe>();
	ResourceLocation backgroundImage;
	TextureManager tm;
	int recipeStartX;
	int recipeStartY;
	int recipeSpace;
	int columns;
	
//	@Override
//	public void onResize(Minecraft mcIn, int w, int h) {
//		super.onResize(mcIn, w, h);
//		resized = true;
//	}
	
	public void updatePlayersInventory() {
		craftableRecipes.clear();
		for(Recipe r : allRecipes) {
			if(r.canRecipeBeCrafted(Minecraft.getInstance().player.inventory)) {
				craftableRecipes.add(r);
			}
		}
	}

	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public void incrementCrafted() {
		if (crafted < totalCrafting) ++crafted;
	}

	public void incrementTotalCrafted() {
		++totalCrafting;
		updatePlayersInventory();
	}
	
	

	void drawBackground() {
		tm.bindTexture(backgroundImage);
		blit(guiX, guiY, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);
	}
	
	void setGuiVals() {
		tm = Minecraft.getInstance().getTextureManager();
		MainWindow mw = Minecraft.getInstance().mainWindow;
		
		tm.bindTexture(backgroundImage);
		
		guiWidth = mw.getScaledWidth() / 2;
		guiHeight = mw.getScaledHeight() / 4;
		guiX = mw.getScaledWidth() / 2 - guiWidth / 2;
		guiY = (int)(mw.getScaledHeight() * .2f);
	}
	
	void drawRecipes() {
		MainWindow mw = Minecraft.getInstance().mainWindow;
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableLighting();
		int row = 0, col = 0;
		for(Recipe r : craftableRecipes) {
			GlStateManager.translated(0, 0, 100);
			int x = recipeStartX + col * iconDim + col * recipeSpace;
			int y = recipeStartY + row * iconDim - 1*(row) + recipeSpace * row;
			Rectangle2D rect = new Rectangle2D.Double(x, y, iconDim, iconDim);
			
			if(selectedRecipe != null && r.equals(selectedRecipe))
				fill((int)rect.getMinX() -1, (int)rect.getMinY() - 1, (int)rect.getMaxX() + 1, (int)rect.getMaxY() + 1, 0xFFEBFF89);
			
			itemRenderer.renderItemAndEffectIntoGUI(r.crafted, x-1, y);
			GlStateManager.translatef(0, 0, -100);
			++col;
			if(col >= columns) {
				col = 0;
				++row;
			}
		}
		
		if(totalCrafting != 0) {
			FontRenderer fr = Minecraft.getInstance().fontRenderer;
			fr.drawString(crafted + "" + "/" + totalCrafting, (int)(guiX + guiWidth * .6f), (int)(guiY + guiHeight * .8f), 0xFFFFFFFF);
		}
		
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
	}
	
	@Override
	public boolean mouseClicked(double mx, double my, int btn) {
		super.mouseClicked(mx, my, btn);
		
		int col = 0, row = 0;
		for(Recipe r : craftableRecipes) {
			int x = recipeStartX + col * iconDim + col * recipeSpace;
			int y = recipeStartY + row * iconDim - 1*(row) + recipeSpace * row;
			Rectangle2D rect = new Rectangle2D.Double(x, y, iconDim, iconDim);
			if (rect.contains(new Point2D.Double(mx, my))) {
				wasMouseClicked = true;
				selectedRecipe = r;
				return true;
			}
			
			++col;
			if(col >= columns) {
				col = 0;
				++row;
			}
		}
		return false;
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public boolean keyPressed(int key, int scan, int modifiers) {
		if(key == GLFW.GLFW_KEY_E || key == GLFW.GLFW_KEY_ESCAPE) {
			onClose();
		}
		return true;
	}
}
