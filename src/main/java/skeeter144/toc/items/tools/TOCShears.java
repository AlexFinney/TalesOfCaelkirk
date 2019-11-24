package skeeter144.toc.items.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import skeeter144.toc.util.Reference;

public class TOCShears extends ShearsItem
{
    public TOCShears(Item.Properties builder, String name)
    {
    	super(builder);
    	setRegistryName(Reference.MODID, name);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, net.minecraft.entity.player.PlayerEntity player, LivingEntity entity, net.minecraft.util.Hand hand)
    {
        if (entity.world.isRemote)
        {
            return false;
        }

        boolean b = super.itemInteractionForEntity(itemstack, player, entity, hand);
        if(b) 
        	MinecraftForge.EVENT_BUS.post(new SheepShearedEvent(entity, player));

        return b;
    }
 
    public static class SheepShearedEvent extends LivingEvent{
    	public PlayerEntity shearer;
		public SheepShearedEvent(LivingEntity entity, PlayerEntity shearer) {
			super(entity);
			this.shearer = shearer;
		}
    	
    }
    
}