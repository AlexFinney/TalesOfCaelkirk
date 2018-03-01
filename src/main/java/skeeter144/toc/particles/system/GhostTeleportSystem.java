package skeeter144.toc.particles.system;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import skeeter144.toc.particles.particle.BasicParticle;

public class GhostTeleportSystem extends ParticleSystem {

	@Override
	public void spawnParticles() {
		Random r = new Random();
		for(int i = 0; i < 100; ++i) {
			Particle p = new BasicParticle(world, posX, posY, posZ, 1, 0xFF00FF, 
					r.nextFloat() * 3 - 1.5f, 
					r.nextFloat() * .25f - .125f,
					r.nextFloat() * 3 - 1.5f, 
					false);
					   
			Minecraft.getMinecraft().effectRenderer.addEffect(p);
		}
	}
}
