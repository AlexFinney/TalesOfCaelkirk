package skeeter144.toc.particles.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OreParticle extends Particle{

	public OreParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.setParticleTexture(Minecraft.getMinecraft().getBlockRendererDispatcher()
				.getBlockModelShapes().getTexture(worldIn.getBlockState(new BlockPos(xCoordIn, (int)yCoordIn, zCoordIn))));
	
		this.particleScale /= 4;
		this.particleGravity = .9f;
		this.particleRed = .7f;
		this.particleGreen = .7f;
		this.particleBlue= .7f;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}
	
}
