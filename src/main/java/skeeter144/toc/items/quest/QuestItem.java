package skeeter144.toc.items.quest;

import net.minecraft.item.Item;

public class QuestItem extends Item{
	public final boolean droppable;
	public QuestItem(Item.Properties builder, boolean droppable, int maxStackSize){
		super(builder);
		this.droppable = droppable;
	}
}
