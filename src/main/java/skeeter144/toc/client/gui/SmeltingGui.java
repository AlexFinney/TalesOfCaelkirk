package skeeter144.toc.client.gui;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.CraftItemMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.recipe.Recipe;
import skeeter144.toc.recipe.RecipeManager;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Reference;

public class SmeltingGui extends CraftingGui{
	
	
	public SmeltingGui() {
		backgroundImage = new ResourceLocation(Reference.MODID, "textures/gui/smelting_gui.png");
		allRecipes = RecipeManager.instance().SMELTING_RECIPES;
		updatePlayersInventory();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.setGuiVals();
		drawBackground();
		drawRecipes();
		for(GuiButton btn : this.buttonList) 
			btn.enabled = selectedRecipe != null;
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    
	    if(resized) {
	    	initGui();
	    	resized = false;
	    }
	    
	    if(wasMouseClicked && !Mouse.isButtonDown(0)) 
			wasMouseClicked = false;
	}

	
	public void initGui() {
		this.buttonList.clear();
		int btnWidth = (int)(guiWidth * .3f);
		
		GuiButton smelt1 = new GuiButton(0, (int)(guiX + guiWidth - btnWidth * 1.2f), guiY + (int)(guiY * .15f), "Smelt 1");
		smelt1.width = btnWidth;
		
		GuiButton smeltAll = new GuiButton(1, (int)(guiX + guiWidth - btnWidth * 1.2f), guiY + (int)(guiY * .5f), "Smelt All");
		smeltAll.width = btnWidth;
		
		this.buttonList.add(smeltAll);
		this.buttonList.add(smelt1);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		EntityPlayer pl = Minecraft.getMinecraft().player;
		if(button.id == 0) {//smelt 1 more
			if(selectedRecipe.canRecipeBeCraftedNumberTimes(pl.inventory, this.totalCrafting + 1)) {
				Network.INSTANCE.sendToServer(new CraftItemMessage(selectedRecipe.crafted.getItem(), 1));
				totalCrafting++;
				pl.world.playSound(pl, pl.getPosition(), Sounds.anvil_strike, SoundCategory.MASTER, 1, 1);
			}
		}else {//smelt all
			int num = selectedRecipe.numberCanBeCrafted(pl.inventory) - totalCrafting;
			for(int i = 0; i < num; ++i) {
				Network.INSTANCE.sendToServer(new CraftItemMessage(selectedRecipe.crafted.getItem(), 1));
				totalCrafting++;
			}
			updatePlayersInventory();
			pl.world.playSound(pl, pl.getPosition(), Sounds.anvil_strike, SoundCategory.MASTER, 1, 1);
			
		}
	}
	
	@Override
	void setGuiVals() {
		 super.setGuiVals();
		 this.recipeStartX = guiX + (int)(guiX * .1f);
		 this.recipeStartY = guiY + (int)(guiHeight * .12f);
		 this.columns = 4;
		 iconDim = (int)(guiWidth * .1f);
		 this.recipeSpace = iconDim / 2;
	}

	public void onGuiClosed() {
		super.onGuiClosed();
		if(selectedRecipe != null)
			Network.INSTANCE.sendToServer(new CraftItemMessage(selectedRecipe.crafted.getItem(), -1));
	}
	
}
