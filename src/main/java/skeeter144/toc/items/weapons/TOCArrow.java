package skeeter144.toc.items.weapons;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.entity.projectile.EntityTOCArrow;
import skeeter144.toc.util.Reference;

public class TOCArrow extends ItemArrow {

	int damage;
	public final float destroyChance;
	public final int color;
	public TOCArrow(String name, int damage, float destroyChance, int color) {
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.damage = damage;
		this.destroyChance = destroyChance;
		this.color = color;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.YELLOW + "Bonus Damage: " + damage);
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
