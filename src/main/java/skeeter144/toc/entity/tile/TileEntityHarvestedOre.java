package skeeter144.toc.entity.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import skeeter144.toc.TOCMain;

public class TileEntityHarvestedOre extends TileEntity implements ITickable{
	
	public IBlockState resourceBlockState;
	public int minSecs, maxSecs;
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("secsRemaining", secsRemaining);
		compound.setString("oreName", resourceBlockState.getBlock().getRegistryName().toString());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		secsRemaining = compound.getInteger("secsRemaining");
		String oreName = compound.getString("oreName");
		resourceBlockState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(oreName)).getDefaultState();
	}

	private int ticksAlive = 1;
	private int secsRemaining = -1;
	@Override
	public void update() {
		if(this.world.isRemote)
			return;

		if(secsRemaining == -1) {
			secsRemaining = TOCMain.rand.nextInt(maxSecs - minSecs) + minSecs;
		}
		
		if(ticksAlive % 20 == 0) {
			if(--secsRemaining <= 0)
				this.world.setBlockState(this.pos, resourceBlockState);
		}
		++ticksAlive;	
	}
}
