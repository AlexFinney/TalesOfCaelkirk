package skeeter144.toc.items.misc;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.util.Reference;

public class ItemTOCBook extends Item {

	  public ItemTOCBook(String name) {
	    this.setCreativeTab(TOCItems.quest_items_tab);
	    this.setMaxStackSize(1);
	    this.setUnlocalizedName(name);
	    this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	  }

	  @Nonnull
	  @Override
	  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
	    ItemStack itemStack = playerIn.getHeldItem(handIn);
	    if(worldIn.isRemote) {
	      TOCBook.INSTANCE.openGui(itemStack);
	    }
	    return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	  }

	  @Override
	  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	  }
	}