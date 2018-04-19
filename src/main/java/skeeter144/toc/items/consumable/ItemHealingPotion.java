package skeeter144.toc.items.consumable;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.effects.HealingPotionEffect;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.util.Reference;

public class ItemHealingPotion extends ItemBucketMilk {

	int duration;
	float totalHeal;
	public ItemHealingPotion(String name, int duration, float totalHeal) {
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
			ServerEffectHandler.attemptAddNewEffect(entityLiving.getUniqueID(), new HealingPotionEffect(entityLiving, duration, totalHeal));
		}
		stack.shrink(1);
		return new ItemStack(TOCItems.glass_vial);
	}
	
	@Override
	public String getHighlightTip(ItemStack item, String displayName) {
		return displayName;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("");
		tooltip.add("Restores " + (int)totalHeal + " health over " + (float)(duration / 20) + " seconds");
	}
}
