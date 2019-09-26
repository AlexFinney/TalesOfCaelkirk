package skeeter144.toc.items.weapons;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.entity.projectile.EntityTOCArrow;

public class TOCArrow extends ItemArrow {

	int damage;
	public final float destroyChance;
	public final int color;
	public TOCArrow(Item.Properties builder) {
		super(builder);
		destroyChance = .5f;
		color = 1;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Bonus Damage: " + damage));
	}

	@Override
	public int getDamage(ItemStack stack) {
		return damage;
	}

	@Override
	public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
		EntityTOCArrow ent_arrow = new EntityTOCArrow(worldIn, shooter);
		ent_arrow.toc_arrow = (TOCArrow) stack.getItem();
		return ent_arrow;
	}

}
