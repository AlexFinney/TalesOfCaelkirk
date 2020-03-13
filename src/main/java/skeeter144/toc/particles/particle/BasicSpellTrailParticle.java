package skeeter144.toc.particles.particle;

import java.awt.Color;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;

public class BasicSpellTrailParticle extends Particle{

	public boolean fizzleOut = true;
	public BasicSpellTrailParticle(World worldIn, double posXIn, double posYIn, double posZIn, float size, int color, float pVel, boolean useGravity) {
		super(worldIn, posXIn, posYIn, posZIn);
		
		double velX = (TOCMain.rand.nextDouble() - .5f) * pVel;
		double velY = (TOCMain.rand.nextDouble() - .5f) * pVel;
		double velZ = (TOCMain.rand.nextDouble() - .5f) * pVel;
		
		
		this.motionX = velX;
		this.motionY = velY;
		this.motionZ = velZ;
		
		this.multipleParticleScaleBy(size);
		this.setMaxAge(20);
		
		Color c = new Color(color);
		this.setColor((float) c.getRed() / 255, (float)c.getGreen() / 255, (float)c.getBlue() / 255);
	}
	
	@Override
	public void tick() {
		super.tick();
		float i = ((float)age) / (maxAge) * (7);
		//if(fizzleOut)
		//	this.setParticleTextureIndex((int)(7-i));
	}

	@Override
	public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float v)
	{

	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.CUSTOM;
	}
}
