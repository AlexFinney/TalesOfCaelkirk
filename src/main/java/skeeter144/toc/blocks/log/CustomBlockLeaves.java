package skeeter144.toc.blocks.log;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleCloud;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import skeeter144.toc.Reference;
import skeeter144.toc.particles.particle.BasicSpellTrailParticle;

public class CustomBlockLeaves extends BlockLeaves {

	public CustomBlockLeaves(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(false))
				.withProperty(DECAYABLE, Boolean.valueOf(false)));
		
		this.setHardness(-1);
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world,
			BlockPos pos, int fortune) {
		return null;
	}

	@Override
	public EnumType getWoodType(int meta) {
		return null;

	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	
	   public IBlockState getStateFromMeta(int meta)
	    {
	        return this.getDefaultState().withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, false);
	    }

	    public int getMetaFromState(IBlockState state)
	    {
	        return 0;
	    }
	
	    protected BlockStateContainer createBlockState()
	    {
	        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
	    }
	    
	@Override
	public void setGraphicsLevel(boolean fancy) {
		super.setGraphicsLevel(fancy);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {}
	
	
}
