package skeeter144.toc.minigames;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiningMinigame {
	int width;
	int height;
	BlockPos center;
	World world;
	EntityPlayer owner;
	public MiningMinigame(World w, EntityPlayer owner, BlockPos center, int sizeX, int sizeY) {
		world = w;
		width = sizeX;
		height = sizeY;
		this.center = center;
		this.owner = owner;
		makeBoard();
		givePlayerItems();
	}
	
	void makeBoard() {
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				BlockPos p = center;
				generateTileAt(p.add(i - width / 2, center.getY() - 5, j - height / 2));
			}
		}
	}
	
	void generateTileAt(BlockPos pos) {
		IBlockState state = null;
		
		int block = (int)(world.rand.nextFloat() * 100f);
		if(block < 4) state = Blocks.DIAMOND_ORE.getDefaultState();
		else if(block < 7) state = Blocks.EMERALD_ORE.getDefaultState();
		else if(block < 15) state = Blocks.GOLD_ORE.getDefaultState();
		else if(block < 30) state = Blocks.IRON_ORE.getDefaultState();
		else if(block < 50) state = Blocks.COAL_ORE.getDefaultState();
		else state = Blocks.STONE.getDefaultState();
		
		world.setBlockState(pos, state);
	}
	
	ArrayList<ItemStack> playerToolbar;
	void givePlayerItems() {
		playerToolbar = new ArrayList<ItemStack>(9);
		for(int i = 0; i < 9; ++i)
		{
			playerToolbar.add(owner.inventory.getStackInSlot(i));
			owner.inventory.setInventorySlotContents(i, new ItemStack(Items.AIR));
		}
	
		
		owner.inventory.setInventorySlotContents(0, (new ItemStack(Items.WOODEN_PICKAXE)));
		owner.inventory.setInventorySlotContents(1, (new ItemStack(Items.STONE_PICKAXE)));
		owner.inventory.setInventorySlotContents(2, (new ItemStack(Items.GOLDEN_PICKAXE)));
		owner.inventory.setInventorySlotContents(3, (new ItemStack(Items.DIAMOND_PICKAXE)));
		
	}
}
