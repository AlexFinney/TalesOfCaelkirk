package skeeter144.toc.items.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import skeeter144.toc.util.Reference;

public class TOCShears extends ItemShears
{
    public TOCShears()
    {
        this.setMaxStackSize(1);
        this.setMaxDamage(-1);
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setRegistryName(new ResourceLocation(Reference.MODID, "shears"));
        this.setUnlocalizedName("shears");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, net.minecraft.entity.player.EntityPlayer player, EntityLivingBase entity, net.minecraft.util.EnumHand hand)
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
    	public EntityPlayer shearer;
		public SheepShearedEvent(EntityLivingBase entity, EntityPlayer shearer) {
			super(entity);
			this.shearer = shearer;
		}
    	
    }
    
}