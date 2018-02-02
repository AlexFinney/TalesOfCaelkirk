package skeeter144.toc.blocks.log;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skeeter144.toc.particles.particle.BasicSpellTrailParticle;

public class BlockMagicLeaves extends CustomBlockLeaves{

	public BlockMagicLeaves(String name) {
		super(name);
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(worldIn.rand.nextInt(5) != 0)
			return;
		
		BasicSpellTrailParticle p = new BasicSpellTrailParticle(worldIn,  pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5, 
				2, 0x00FFFF, 1f, true);
		try {
			Field f = p.getClass().getSuperclass().getDeclaredField("particleGravity");
			f.setAccessible(true);
			f.set(p, 3);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Minecraft.getMinecraft().effectRenderer.addEffect(p);
	}

}
