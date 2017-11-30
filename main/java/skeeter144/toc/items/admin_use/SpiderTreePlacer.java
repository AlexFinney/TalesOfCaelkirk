package skeeter144.toc.items.admin_use;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import skeeter144.toc.Reference;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.TOCBlocks;
import skeeter144.toc.util.Util;

public class SpiderTreePlacer extends Item{

	public SpiderTreePlacer(String name) {
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.MISC);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		int trunkMinHeight = 15, trunkMaxHeight = 20;
		
		if(!playerIn.world.isRemote) {
			RayTraceResult rtr = Util.rayTrace(playerIn, 200, 1);
			if(rtr != null && rtr.getBlockPos() != null) {
				for(int i = 0; i < 10; ++i) {
					int offX = TOCMain.rand.nextInt(10) - 5;
					int offZ = TOCMain.rand.nextInt(10) - 5;
					int trunkHeight = TOCMain.rand.nextInt(trunkMaxHeight - trunkMinHeight) + trunkMinHeight;
					if(trunkHeight + rtr.getBlockPos().getY() + 20 >= 256) {
						System.out.println("Warning: Unable to place tree here. Ground is too high");
						return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
					}
					
					for(int y = rtr.getBlockPos().getY() + 1; y < rtr.getBlockPos().getY() + trunkHeight + 1; ++y) {
							playerIn.world.setBlockState(new BlockPos(rtr.getBlockPos().getX() + offX, y, rtr.getBlockPos().getZ()+ offZ),
									TOCBlocks.spiderTreeLog.getDefaultState());
							playerIn.world.setBlockState(new BlockPos(rtr.getBlockPos().getX() + 1 + offX, y, rtr.getBlockPos().getZ()+ offZ),
									TOCBlocks.spiderTreeLog.getDefaultState());
							playerIn.world.setBlockState(new BlockPos(rtr.getBlockPos().getX() + offX, y, rtr.getBlockPos().getZ() + 1+ offZ),
									TOCBlocks.spiderTreeLog.getDefaultState());
							playerIn.world.setBlockState(new BlockPos(rtr.getBlockPos().getX() + 1 + offX, y, rtr.getBlockPos().getZ() + 1 + offZ),
									TOCBlocks.spiderTreeLog.getDefaultState());
					}
					
					BlockPos pos = rtr.getBlockPos();
					World w = playerIn.world;
					leafLayer(w, 1, 4, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 2, 8, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 3, 10, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 4, 8, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 5, 8, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 6, 8, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 7, 8, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 8, 8, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 9, 6, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 10, 6, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 11, 6, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 12, 4, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 13, 4, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 14, 2, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					leafLayer(w, 15, 2, new BlockPos(pos.getX() + offX, pos.getY(), pos.getZ() + offZ), trunkHeight);
					
				}
			}
		}	
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	void leafLayer(World w, int height, int length, BlockPos pos, int trunkHeight) {
		for(int z = -length / 2; z <= length / 2 + 1; ++z) {
			for(int x = -length / 2; x <= length / 2 + 1; ++x) {
				if(TOCMain.rand.nextFloat() < .5)
					continue;
				
				w.setBlockState(new BlockPos(pos.getX() + x, height + pos.getY() - 3 + trunkHeight, 
						pos.getZ() + z),	TOCBlocks.spiderTreeLeaves.getDefaultState());
			}
		}
	}
}

