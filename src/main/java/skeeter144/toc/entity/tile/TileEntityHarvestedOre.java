package skeeter144.toc.entity.tile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.TOCMain;
import skeeter144.toc.blocks.TOCBlocks;

public class TileEntityHarvestedOre extends TileEntity implements ITickable{
	
	public TileEntityHarvestedOre() {
		super(TOCBlocks.te_harvested_ore);
	}
	
	public TileEntityHarvestedOre(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public BlockState resourceBlockState;
	public int minSecs = 3, maxSecs = 6;
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("secsRemaining", secsRemaining);
		compound.putInt("minSecs", minSecs);
		compound.putInt("maxSecs", maxSecs);
		compound.putString("oreName", resourceBlockState.getBlock().getRegistryName().toString());
		return compound;
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		secsRemaining = compound.getInt("secsRemaining");
		minSecs = compound.getInt("minSecs");
		maxSecs = compound.getInt("maxSecs");
		String oreName = compound.getString("oreName");
		resourceBlockState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(oreName)).getDefaultState();
	}

	private int ticksAlive = 1;
	private int secsRemaining = -1;
	@Override
	public void tick() {
		if(this.world.isRemote)
			return;

		if(secsRemaining == -1) {
			secsRemaining = TOCMain.rand.nextInt(maxSecs - minSecs) + minSecs;
		}
		
		if(ticksAlive % 20 == 0) {
			if(--secsRemaining <= 0) {
				if(resourceBlockState != null) {
					this.world.setBlockState(this.pos, resourceBlockState);
				} else {
					this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
					TOCMain.LOGGER.warn("Harvested Ore has an invalid resource block state. ");
				}
			}
		}
		++ticksAlive;	
	}
	
}
