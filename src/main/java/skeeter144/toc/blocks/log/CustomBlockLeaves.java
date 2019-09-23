package skeeter144.toc.blocks.log;

import java.util.List;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import skeeter144.toc.util.Reference;

public class CustomBlockLeaves extends BlockLeaves {

	public CustomBlockLeaves(String name) {
		super(Properties.create(Material.LEAVES).hardnessAndResistance(-1, 1));
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IWorld world, BlockPos pos, int fortune) {
		return null;
	}
}
