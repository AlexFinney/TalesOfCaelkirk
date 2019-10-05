package skeeter144.toc.items.quest;

import net.minecraft.item.Item;
import skeeter144.toc.util.Reference;

public class QuestItem extends Item{
	public final boolean droppable;
	public QuestItem(Item.Properties builder, String name, boolean droppable, int maxStackSize){
		super(builder);
		setRegistryName(Reference.MODID, name);
		this.droppable = droppable;
	}
}
