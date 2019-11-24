package skeeter144.toc.particles.particle;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.world.World;

public class OreParticle extends Particle{

	public OreParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
//		this.setParticleTexture(Minecraft.getInstance().getBlockRendererDispatcher()
//				.getBlockModelShapes().getTexture(worldIn.getBlockState(new BlockPos(xCoordIn, (int)yCoordIn, zCoordIn))));
	
		this.multipleParticleScaleBy(.25f);
		this.particleGravity = .9f;
		this.particleRed = .7f;
		this.particleGreen = .7f;
		this.particleBlue= .7f;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entityIn, float partialTicks, float rotationX,
			float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IParticleRenderType getRenderType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
