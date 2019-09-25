package skeeter144.toc.entity.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
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

	public IBlockState resourceBlockState;
	public int minSecs, maxSecs;
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInt("secsRemaining", secsRemaining);
		compound.setString("oreName", resourceBlockState.getBlock().getRegistryName().toString());
		return compound;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		secsRemaining = compound.getInt("secsRemaining");
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
			if(--secsRemaining <= 0)
				this.world.setBlockState(this.pos, resourceBlockState);
		}
		++ticksAlive;	
	}
	
}
