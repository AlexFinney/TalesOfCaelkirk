package skeeter144.toc.client.gui;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.recipe.Recipe;
import skeeter144.toc.util.Mouse;

public abstract class CraftingGui extends GuiScreen implements IGuiEventListener{
	int totalCrafting = 0;
	int crafted = 0;
	boolean resized = true;
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
	
	@Override
	public void onResize(Minecraft mcIn, int w, int h) {
		super.onResize(mcIn, w, h);
		resized = true;
	}
	
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
		++crafted;
		if(crafted == totalCrafting)
			selectedRecipe = null;
	}

	public void incrementTotalCrafted() {
		++totalCrafting;
		updatePlayersInventory();
	}
	
	

	void drawBackground() {
		drawModalRectWithCustomSizedTexture(guiX, guiY, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);
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
		int x = 0, y = 0;
		int row = 0, col = 0;
		for(Recipe r : craftableRecipes) {
			GlStateManager.translated(0, 0, 100);
			x = recipeStartX + col * iconDim + col * recipeSpace;
			y = recipeStartY + row * iconDim;
			Rectangle2D rect = new Rectangle2D.Double(x, y, iconDim, iconDim);
			
			if(selectedRecipe != null && r.equals(selectedRecipe))
				drawRect((int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(), 0xFFEBFF89);
			
			this.itemRender.renderItemAndEffectIntoGUI(r.crafted, x+2, y+2);
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
			int y = recipeStartY + row * iconDim;
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
	
}
