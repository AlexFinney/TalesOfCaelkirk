package skeeter144.toc.recipe;

import net.minecraft.item.ItemStack;
import skeeter144.toc.player.EntityLevels.Levels;

public class TickableRecipe extends Recipe{
	public final int duration;
	public TickableRecipe(ItemStack crafted, Levels level, int levelReq, int xp, int duration, ItemStack... components) {
		super(crafted, level, levelReq, xp, components);
		this.duration = duration;
	}

}
