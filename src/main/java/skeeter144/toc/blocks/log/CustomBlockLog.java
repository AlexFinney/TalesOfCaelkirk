package skeeter144.toc.blocks.log;

import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Reference;

public class CustomBlockLog extends LogBlock {

	static final SoundType customType = new SoundType(1, 1, SoundEvents.BLOCK_WOOD_BREAK, SoundEvents.BLOCK_WOOD_STEP,
			SoundEvents.BLOCK_WOOD_PLACE, Sounds.fake_wood_hit, SoundEvents.BLOCK_WOOD_BREAK);

	public CustomBlockLog(String name) {
		super(MaterialColor.WOOD, Properties.create(Material.WOOD).sound(customType).hardnessAndResistance(-1));

		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y));
	}

	public BlockState getStateFromMeta(int meta) {
		BlockState state = this.getDefaultState();

		switch (meta & 0b1100) {
		case 0b0000:
			state = state.with(AXIS, Direction.Axis.Y);
			break;

		case 0b0100:
			state = state.with(AXIS, Direction.Axis.X);
			break;

		case 0b1000:
			state = state.with(AXIS, Direction.Axis.Z);
			break;
		}

		return state;
	}

	public int getMetaFromState(BlockState state) {
		switch ((Direction.Axis) state.get(AXIS)) {
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
