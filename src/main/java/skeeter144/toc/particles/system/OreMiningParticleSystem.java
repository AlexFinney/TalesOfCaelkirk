package skeeter144.toc.particles.system;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import skeeter144.toc.TOCMain;
import skeeter144.toc.particles.particle.OreParticle;

public class OreMiningParticleSystem extends ParticleSystem{

	ArrayList<Particle> particles;
	public OreMiningParticleSystem() {
		particles = new ArrayList<Particle>();
	}
	
	@Override
	public void spawnParticles() {
		for(int i = 0; i < 5; ++i) {
			float xVel = TOCMain.rand.nextFloat() - .5f;
			float yVel = TOCMain.rand.nextFloat() + .2f;
			float zVel = TOCMain.rand.nextFloat() - .5f;
			
			float xOff = TOCMain.rand.nextFloat();
			float zOff = TOCMain.rand.nextFloat();
			
			particles.add(new OreParticle(this.world, this.posX + xOff, this.posY + TOCMain.rand.nextFloat(), this.posZ + zOff, xVel * 2, yVel, zVel * 2));
			Minecraft.getInstance().effectRenderer.addEffect(particles.get(i));
		}
	}

}
