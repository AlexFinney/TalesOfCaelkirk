package skeeter144.toc.items.consumable;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.effects.HealingPotionEffect;

public class ItemHealingPotion extends ItemBucketMilk {

	int duration;
	float totalHeal;
	public ItemHealingPotion(Item.Properties builder, int duration, float totalHeal) {
		super(builder);
		this.duration = duration;
		this.totalHeal = totalHeal;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if(!worldIn.isRemote) {
			ServerEffectHandler.attemptAddNewEffect(entityLiving.getUniqueID(), new HealingPotionEffect(entityLiving, duration, totalHeal));
		}
		stack.shrink(1);
		return null;//new ItemStack(TOCItems.glass_vial);
	}
	
	@Override
	public String getHighlightTip(ItemStack item, String displayName) {
		return displayName;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString(""));
		tooltip.add(new TextComponentString("Restores " + (int)totalHeal + " health over " + (float)(duration / 20) + " seconds"));
	}
}
