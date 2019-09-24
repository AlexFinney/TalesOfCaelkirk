package skeeter144.toc.client.gui;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import skeeter144.toc.entity.tile.TileEntityAnvil;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SetAnvilRecipeMessage;
import skeeter144.toc.recipe.Recipe;
import skeeter144.toc.recipe.RecipeManager;
import skeeter144.toc.util.Mouse;
import skeeter144.toc.util.Reference;

public class SmithingGui extends CraftingGui{

	TileEntityAnvil anvil;
	public SmithingGui(BlockPos pos) {
		backgroundImage = new ResourceLocation(Reference.MODID, "textures/gui/smithing_gui.png");
		allRecipes = RecipeManager.instance().SMITHING_RECIPES;
		this.anvil = (TileEntityAnvil) Minecraft.getInstance().world.getTileEntity(pos);
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.setGuiVals();
		drawBackground();
		
		if(anvil != null && anvil.ingot != null) {
			int ingots = anvil.addedIngots;
			for(Recipe r : allRecipes) {
				if(r.components[0].getItem().equals(anvil.ingot) && r.components[0].getCount() <= ingots) {
					craftableRecipes.add(r);
				}
			}
		}
		
		drawRecipes();
		if(wasMouseClicked && !Mouse.isButtonDown(0)) 
			wasMouseClicked = false;
	}
	
	@Override
	void setGuiVals() {
		 super.setGuiVals();
		 MainWindow sr = Minecraft.getInstance().mainWindow;
		 guiWidth = (int)(sr.getScaledWidth() * .5f);
		 guiHeight = (int)(guiWidth * .5f);
		 guiX = sr.getScaledWidth() / 2 - guiWidth / 2;
		 guiY = (int)(sr.getScaledHeight() * .2f);
		 this.recipeStartX = guiX + (int)(guiX * .1f);
		 this.recipeStartY = guiY + (int)(guiHeight * .12f);
		 this.columns = 8;
		 iconDim = (int)(guiWidth * .072f);
		 this.recipeSpace = (int)(iconDim * .95f);
	}
	
	@Override
	public void onGuiClosed() {
		if(selectedRecipe != null) {
			Network.INSTANCE.sendToServer(new SetAnvilRecipeMessage(selectedRecipe, anvil.getPos()));
		}
	}
	
}
