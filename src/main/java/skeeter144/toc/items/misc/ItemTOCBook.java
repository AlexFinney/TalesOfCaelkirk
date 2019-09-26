package skeeter144.toc.items.misc;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemTOCBook extends Item {

	public ItemTOCBook(Item.Properties builder) {
		super(builder);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		if (worldIn.isRemote) {
		//	TOCBook.INSTANCE.openGui(itemStack);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}