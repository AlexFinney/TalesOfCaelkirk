package skeeter144.toc.tasks;

import java.util.UUID;

import skeeter144.toc.recipe.RecipeManager;
import skeeter144.toc.recipe.TickableRecipe;
import skeeter144.toc.util.Util;

public class CraftingTask extends TickableTask{
	int totalToCraft;
	int crafted = 0;
	TickableRecipe recipe;
	UUID playerId;
	RecipeManager manager;
	int nextCraftTick;
	public CraftingTask(TickableRecipe r, UUID playerId, int num) {
		super(r.duration * num);
		totalToCraft = num;
		recipe = r;
		this.playerId = playerId;
		manager = RecipeManager.instance();
	}
	
	@Override
	public void tick(int worldTick) {
		if(worldTick >= nextCraftTick) {
			if(!manager.craftRecipe(Util.getPlayerByUUID(playerId), recipe)) {
				cancel();
			}else {
				if(++crafted == totalToCraft)
					onEnd();
			}
			nextCraftTick = worldTick + recipe.duration;
		}
	}
	
	@Override
	public void onStart() {
		nextCraftTick = start + recipe.duration;
	}
	
	@Override
	public void onEnd() {
		RecipeManager.instance().cancelCraftingForPlayer(playerId);
		duration = 0;
		cancelled = true;
		super.onEnd();
	}
	
	public TickableRecipe getRecipe() {
		return recipe;
	}

}
