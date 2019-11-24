package skeeter144.toc.items.weapons;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import skeeter144.toc.entity.projectile.EntityTOCArrow;
import skeeter144.toc.util.Reference;

public class TOCArrow extends ArrowItem implements IItemProvider {

	int damage;
	public final float destroyChance;
	public final int color;
	public TOCArrow(Item.Properties builder, String name, int damage, float destroyChance) {
		super(builder);
		this.destroyChance = destroyChance;
		this.damage = damage;
		color = 1;
		setRegistryName(Reference.MODID, name);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Bonus Damage: " + damage));
	}

	@Override
	public int getDamage(ItemStack stack) {
		return damage;
	}

	@Override
	public ArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		EntityTOCArrow ent_arrow = new EntityTOCArrow(worldIn, shooter);
		ent_arrow.toc_arrow = (TOCArrow) stack.getItem();
		return ent_arrow;
	}

}
