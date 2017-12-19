package skeeter144.toc.client.gui;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.network.CraftItemMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.recipe.Recipe;

public abstract class CraftingGui extends GuiScreen{
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
	ScaledResolution sr;
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
			if(r.canRecipeBeCrafted(Minecraft.getMinecraft().player.inventory)) {
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
		tm = Minecraft.getMinecraft().getTextureManager();
		sr = new ScaledResolution(Minecraft.getMinecraft());
		
		tm.bindTexture(backgroundImage);
		
		guiWidth = (int)(sr.getScaledWidth() * .4f);
		guiHeight = (int)(guiWidth * .4f);
		guiX = sr.getScaledWidth() / 2 - guiWidth / 2;
		guiY = (int)(sr.getScaledHeight() * .2f);
	}
	
	void drawRecipes() {
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableLighting();
		int x = 0, y = 0;
		int row = 0, col = 0;
		int adjustedX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
		int adjustedY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight;
		 
		for(Recipe r : allRecipes) {
			if(!craftableRecipes.contains(r))
				continue;
			GlStateManager.translate(0, 0, 100);
			x = recipeStartX + col * iconDim + col * recipeSpace;
			y = recipeStartY + row * iconDim;
			Rectangle2D rect = new Rectangle2D.Double(x, y, iconDim, iconDim);
			if(!wasMouseClicked && Mouse.isButtonDown(0)) {
				if(rect.contains(new Point2D.Double(adjustedX, adjustedY))) {
					wasMouseClicked = true;
					selectedRecipe = r;
				}
			}
			
			if(selectedRecipe != null && r.equals(selectedRecipe))
				this.drawRect((int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(), 0xFFEBFF89);
			
			
			this.itemRender.renderItemAndEffectIntoGUI(r.crafted, x, y);
			GlStateManager.translate(0, 0, -100);
			++col;
			if(col >= columns) {
				col = 0;
				++row;
			}
		}
		
		if(totalCrafting != 0) {
			FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
			fr.drawString(crafted + "" + "/" + totalCrafting, (int)(guiX + guiWidth * .6f), (int)(guiY + guiHeight * .8f), 0xFFFFFFFF);
		}
		
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
	}
	
	
}
