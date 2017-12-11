package skeeter144.toc.items.tools;

import net.minecraft.block.BlockLog;
import net.minecraft.item.ItemAxe;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class TOCAxe extends ItemAxe {

	public TOCAxe(String name, float damage, float speed, ToolMaterial material) {
		super(material, damage, speed);
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Reference.MODID, name));
		setMaxStackSize(1);
		setMaxDamage(-1);
	}

	
}
