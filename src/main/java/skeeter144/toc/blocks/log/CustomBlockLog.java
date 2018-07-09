package skeeter144.toc.blocks.log;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Reference;

public class CustomBlockLog extends BlockLog{
	
	static final SoundType customType = new SoundType(1, 1, SoundEvents.BLOCK_WOOD_BREAK, SoundEvents.BLOCK_WOOD_STEP, SoundEvents.BLOCK_WOOD_PLACE, Sounds.fake_wood_hit, SoundEvents.BLOCK_WOOD_BREAK);
	
	public CustomBlockLog(String name) {
		super();
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setUnlocalizedName(name);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		this.setSoundType(customType);
	}
	
	  public IBlockState getStateFromMeta(int meta)
	    {
	        IBlockState state = this.getDefaultState();

	        switch (meta & 0b1100)
	        {
	            case 0b0000:
	                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
	                break;

	            case 0b0100:
	                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
	                break;

	            case 0b1000:
	                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
	                break;

	            case 0b1100:
	                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
	                break;
	        }

	        return state;
	    }

	    public int getMetaFromState(IBlockState state)
	    {
	        switch ((BlockLog.EnumAxis)state.getValue(LOG_AXIS))
	        {
	            case X: return 0b0100;
	            case Y: return 0b0000;
	            case Z: return 0b1000;
	            case NONE: return 0b1100;
	            default: return 0b0100;
	        }
	    }

	    protected BlockStateContainer createBlockState()
	    {
	        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
	    }
}
