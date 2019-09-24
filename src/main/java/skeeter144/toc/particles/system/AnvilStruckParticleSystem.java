package skeeter144.toc.particles.system;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import skeeter144.toc.particles.particle.FireSpellTrailParticle;

public class AnvilStruckParticleSystem extends ParticleSystem{

	ArrayList<Particle> particles = new ArrayList<Particle>();
	
	@Override
	public void spawnParticles() {
		for(int i = 0; i < 10; ++i) {
			particles.add(new FireSpellTrailParticle(world, posX + .5f, posY + 1, posZ + .5f, .5f, 0xFFFF00, .3f));
			Minecraft.getInstance().effectRenderer.addEffect(particles.get(i));
		}
	}

}
