package skeeter144.toc.handlers;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.items.weapons.TOCBow;
import skeeter144.toc.items.weapons.TOCGreatAxe;
import skeeter144.toc.items.weapons.TOCGreatSword;
import skeeter144.toc.items.weapons.TOCSword;

public class ItemTooltipHandler {
	
	
	@SubscribeEvent
	public void tooltipDrawn(ItemTooltipEvent e) {
		Class[] classes = new Class[] {TOCSword.class, TOCGreatAxe.class, TOCGreatSword.class, TOCBow.class};
		
		boolean found = false;
		for(Class c : classes) {
			if(c.equals(e.getItemStack().getItem().getClass())) {
				found = true;
				break;
			}
		}
		
		if(!found)
			return;
		
		List<String> tooltip = e.getToolTip();
		tooltip.clear();
		
		Item item = e.getItemStack().getItem();
		
		if(e.getEntityPlayer() == null || e.getFlags() == null)
			return;
		
		tooltip.add(e.getItemStack().getDisplayName());
		tooltip.add("");
		
		if(e.getItemStack().getItem() instanceof TOCSword) 
			((TOCSword)item).addInformation(e.getItemStack(), e.getEntityPlayer().world, tooltip, e.getFlags());
		
		else if(e.getItemStack().getItem() instanceof TOCGreatSword) 
			((TOCGreatSword)item).addInformation(e.getItemStack(), e.getEntityPlayer().world, tooltip, e.getFlags());
		
		else if(e.getItemStack().getItem() instanceof TOCGreatAxe) 
			((TOCGreatAxe)item).addInformation(e.getItemStack(), e.getEntityPlayer().world, tooltip, e.getFlags());
		
		else if(e.getItemStack().getItem() instanceof TOCBow) 
			((TOCBow)item).addInformation(e.getItemStack(), e.getEntityPlayer().world, tooltip, e.getFlags());
		
	}
}
