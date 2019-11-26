package skeeter144.toc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;
import skeeter144.toc.network.CraftItemPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.recipe.RecipeManager;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Mouse;
import skeeter144.toc.util.Reference;

public class SmeltingGui extends CraftingGui{
	
	
	public SmeltingGui() {
		super(new StringTextComponent("Smelting"));
		backgroundImage = new ResourceLocation(Reference.MODID, "textures/gui/smelting_gui.png");
		allRecipes = RecipeManager.instance().SMELTING_RECIPES;
		updatePlayersInventory();
	}
	
	@Override
	public void render(int x, int y, float partialTicks) {
		this.renderBackground();
		this.setGuiVals();
		drawBackground();
		drawRecipes();
		super.render(x, y, partialTicks);
		for(Widget btn : buttons) 
			btn.active = selectedRecipe != null;
	    
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
		
		Button smelt1 = new Button((int)(guiX + guiWidth - btnWidth * 1.2f), guiY + (int)(guiY * .15f), btnWidth, 20,"Smelt 1", new Button.IPressable() {
			
			@Override
			public void onPress(Button p_onPress_1_) {
				PlayerEntity pl = Minecraft.getInstance().player;
				crafted = 0;
				totalCrafting = 1;
				if (selectedRecipe.canRecipeBeCraftedNumberTimes(pl.inventory, totalCrafting)) {
					Network.INSTANCE.sendToServer(new CraftItemPKT(selectedRecipe.crafted.getItem(), 1));
					pl.world.playSound(pl, pl.getPosition(), Sounds.anvil_strike, SoundCategory.MASTER, 1, 1);
				}
			}
		});
		
		Button smeltAll = new Button((int)(guiX + guiWidth - btnWidth * 1.2f), guiY + (int)(guiY * .5f), btnWidth, 20, "Smelt All",  new Button.IPressable() {
			@Override
			public void onPress(Button p_onPress_1_) {
				PlayerEntity pl = Minecraft.getInstance().player;
				int num = selectedRecipe.numberCanBeCrafted(pl.inventory);
				crafted = 0;
				totalCrafting = num;
				Network.INSTANCE.sendToServer(new CraftItemPKT(selectedRecipe.crafted.getItem(), num));
				updatePlayersInventory();
				pl.world.playSound(pl, pl.getPosition(), Sounds.anvil_strike, SoundCategory.MASTER, 1, 1);
			}
		});
		
		addButton(smelt1);
		addButton(smeltAll);
	}
	
	protected void actionPerformed(Button button){
		PlayerEntity pl = Minecraft.getInstance().player;
		//TODO
		if(button.x == 0) {//smelt 1 more
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

	@Override
	public void onClose() {
		super.onClose();
		if(selectedRecipe != null)
			Network.INSTANCE.sendToServer(new CraftItemPKT(selectedRecipe.crafted.getItem(), -1));
	}
	
}
