package skeeter144.toc.items.misc;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.Reference;

public class TOCArrow extends ItemArrow{
	
	int damage;
	public TOCArrow(String name, int damage) {
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.damage = damage;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.YELLOW + "Bonus Damage: " + damage);
	}
	
	@Override
	public int getDamage(ItemStack stack) {
		return damage;
	}
	
}
