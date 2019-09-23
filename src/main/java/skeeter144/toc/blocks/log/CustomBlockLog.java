package skeeter144.toc.blocks.log;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Reference;

public class CustomBlockLog extends BlockLog {

	static final SoundType customType = new SoundType(1, 1, SoundEvents.BLOCK_WOOD_BREAK, SoundEvents.BLOCK_WOOD_STEP,
			SoundEvents.BLOCK_WOOD_PLACE, Sounds.fake_wood_hit, SoundEvents.BLOCK_WOOD_BREAK);

	public CustomBlockLog(String name) {
		super(MaterialColor.WOOD, Properties.create(Material.WOOD).sound(customType));

		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, EnumFacing.Axis.Y));
	}

	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();

		switch (meta & 0b1100) {
		case 0b0000:
			state = state.with(AXIS, EnumFacing.Axis.Y);
			break;

		case 0b0100:
			state = state.with(AXIS, EnumFacing.Axis.X);
			break;

		case 0b1000:
			state = state.with(AXIS, EnumFacing.Axis.Z);
			break;
		}

		return state;
	}

	public int getMetaFromState(IBlockState state) {
		switch ((EnumFacing.Axis) state.get(AXIS)) {
		case X:
			return 0b0100;
		case Y:
			return 0b0000;
		case Z:
			return 0b1000;
		default:
			return 0b0100;
		}
	}
}
