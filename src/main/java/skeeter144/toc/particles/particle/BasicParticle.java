package skeeter144.toc.particles.particle;

import java.awt.Color;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.world.World;

public class BasicParticle extends Particle {
	
	
	public BasicParticle(World worldIn, double posXIn, double posYIn, double posZIn, float size, int color, float xVel, float yVel, float zVel, boolean useGravity) {
		super(worldIn, posXIn, posYIn, posZIn);
		
		this.motionX = xVel;
		this.motionY = yVel;
		this.motionZ = zVel;
		
		multipleParticleScaleBy(size);
		this.setMaxAge(20);
		
		Color c = new Color(color);
		this.setColor((float) c.getRed() / 255, (float)c.getGreen() / 255, (float)c.getBlue() / 255);
		this.particleGravity = useGravity ? 1 : 0;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entityIn, float partialTicks, float rotationX,
			float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_LIT;
	}
	
	
}
