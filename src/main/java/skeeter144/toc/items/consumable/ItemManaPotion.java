package skeeter144.toc.items.consumable;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.Reference;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.effects.ManaPotionEffect;
import skeeter144.toc.items.TOCItems;

public class ItemManaPotion extends ItemBucketMilk {

	int duration;
	float totalHeal;
	public ItemManaPotion(String name, int duration, float totalHeal) {
		super();
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setUnlocalizedName(name);
		this.duration = duration;
		this.totalHeal = totalHeal;
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if(!worldIn.isRemote) {
			ServerEffectHandler.attemptAddNewEffect(entityLiving.getUniqueID(), new ManaPotionEffect(entityLiving, duration, totalHeal));
		}
		stack.shrink(1);
		return new ItemStack(TOCItems.glass_vial);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("");
		tooltip.add("Restores " + (int)totalHeal + " mana over " + (float)(duration / 20) + " seconds");
	}
	
}
