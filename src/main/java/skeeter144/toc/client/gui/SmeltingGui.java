package skeeter144.toc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import skeeter144.toc.network.CraftItemPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.recipe.RecipeManager;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Mouse;
import skeeter144.toc.util.Reference;

public class SmeltingGui extends CraftingGui{
	
	
	public SmeltingGui() {
		backgroundImage = new ResourceLocation(Reference.MODID, "textures/gui/smelting_gui.png");
		allRecipes = RecipeManager.instance().SMELTING_RECIPES;
		updatePlayersInventory();
	}
	
	@Override
	public void render(int x, int y, float partialTicks) {
		this.drawDefaultBackground();
		this.setGuiVals();
		drawBackground();
		drawRecipes();
		super.render(x, y, partialTicks);
		for(GuiButton btn : this.buttons) 
			btn.enabled = selectedRecipe != null;
	    
	    if(resized) {
	    	initGui();
	    	resized = false;
	    }
	    
	    if(wasMouseClicked && !Mouse.isButtonDown(0)) 
			wasMouseClicked = false;
	}

	
	public void initGui() {
		this.buttons.clear();
		int btnWidth = (int)(guiWidth * .3f);
		
		GuiButton smelt1 = new GuiButton(0, (int)(guiX + guiWidth - btnWidth * 1.2f), guiY + (int)(guiY * .15f), "Smelt 1") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				EntityPlayer pl = Minecraft.getInstance().player;
				if (selectedRecipe.canRecipeBeCraftedNumberTimes(pl.inventory, totalCrafting + 1)) {
					Network.INSTANCE.sendToServer(new CraftItemPKT(selectedRecipe.crafted.getItem(), 1));
					totalCrafting++;
					pl.world.playSound(pl, pl.getPosition(), Sounds.anvil_strike, SoundCategory.MASTER, 1, 1);
				}
			}
		};
		smelt1.width = btnWidth;
		
		GuiButton smeltAll = new GuiButton(1, (int)(guiX + guiWidth - btnWidth * 1.2f), guiY + (int)(guiY * .5f), "Smelt All") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				EntityPlayer pl = Minecraft.getInstance().player;
				int num = selectedRecipe.numberCanBeCrafted(pl.inventory) - totalCrafting;
				Network.INSTANCE.sendToServer(new CraftItemPKT(selectedRecipe.crafted.getItem(), num));
				totalCrafting += num;
				updatePlayersInventory();
				pl.world.playSound(pl, pl.getPosition(), Sounds.anvil_strike, SoundCategory.MASTER, 1, 1);
			}
		};
		smeltAll.width = btnWidth;
		
		addButton(smelt1);
		addButton(smeltAll);
	}
	
	protected void actionPerformed(GuiButton button){
		EntityPlayer pl = Minecraft.getInstance().player;
		if(button.id == 0) {//smelt 1 more
			if(selectedRecipe.canRecipeBeCraftedNumberTimes(pl.inventory, this.totalCrafting + 1)) {
			//	Network.INSTANCE.sendToServer(new CraftItemMessage(selectedRecipe.crafted.getItem(), 1));
				totalCrafting++;
				pl.world.playSound(pl, pl.getPosition(), Sounds.anvil_strike, SoundCategory.MASTER, 1, 1);
			}
		}else {//smelt all
			int num = selectedRecipe.numberCanBeCrafted(pl.inventory) - totalCrafting;
			for(int i = 0; i < num; ++i) {
			//	Network.INSTANCE.sendToServer(new CraftItemMessage(selectedRecipe.crafted.getItem(), 1));
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
			Network.INSTANCE.sendToServer(new CraftItemPKT(selectedRecipe.crafted.getItem(), -1));
	}
	
}
