package skeeter144.toc.items.quest;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.util.Reference;

public class QuestItem extends Item{
	public final boolean droppable;
	public QuestItem(String name, boolean droppable, int maxStackSize){
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setUnlocalizedName(name);
		setMaxStackSize(maxStackSize);
		this.setCreativeTab(TOCItems.quest_items_tab);
		this.droppable = droppable;
	}
}
