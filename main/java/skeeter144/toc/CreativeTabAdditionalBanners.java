package skeeter144.toc;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.NonNullList;
import skeeter144.toc.handler.DesignHandler;
import skeeter144.toc.handler.PatternHandler;
import skeeter144.toc.handler.PatternHandler.BannerLayer;

public class CreativeTabAdditionalBanners extends CreativeTabs {

    private static ItemStack DISPLAY = null;
    private static List<ItemStack> CACHE = null;

    public CreativeTabAdditionalBanners () {

        super("additionalbanners");
        this.setBackgroundImageName("item_search.png");
    }

    @Override
    public ItemStack getTabIconItem () {

        return this.getIconItemStack();
    }

    @Override
    public ItemStack getIconItemStack () {

        if (DISPLAY == null)
            DISPLAY = PatternHandler.createBanner(EnumDyeColor.WHITE, PatternHandler.createPatternList(DesignHandler.LanguageDesign.ADD.getLayers()));

        return DISPLAY;
    }

    @Override
    public boolean hasSearchBar () {

        return true;
    }

    @Override
    public void displayAllRelevantItems (NonNullList<ItemStack> itemList) {

        super.displayAllRelevantItems(itemList);

        for (final BannerPattern pattern : BannerPattern.values())
            itemList.add(PatternHandler.createBanner(EnumDyeColor.WHITE, PatternHandler.createPatternList(EnumDyeColor.BLACK, new BannerLayer(pattern, EnumDyeColor.BLACK))));
        if (CACHE == null) {

            CACHE = new ArrayList<ItemStack>();
            for (final EnumDyeColor color : EnumDyeColor.values())
                for (final DesignHandler.LanguageDesign design : DesignHandler.LanguageDesign.values()) {

                    final ItemStack stack = PatternHandler.createBanner(color, PatternHandler.createPatternList(color, design.getLayers()));
                    stack.setStackDisplayName(ChatFormatting.RESET + "Design: " + design.name().toLowerCase());
                    CACHE.add(stack);
                }
        }

        itemList.addAll(CACHE);
    }
}