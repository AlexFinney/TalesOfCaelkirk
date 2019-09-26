package skeeter144.toc.particles.system;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import skeeter144.toc.TOCMain;
import skeeter144.toc.particles.particle.OrbitingEffectParticle;

public class ManaEffectSystem extends ParticleSystem{

	int numParticles = 40;
	List<OrbitingEffectParticle> particles;
	Entity target;
	
	public ManaEffectSystem() {
		super();
		particles = new ArrayList<OrbitingEffectParticle>();
	}
	
	
	@Override
	public void spawnParticles() {
		target = Minecraft.getInstance().player.world.getEntityByID((int)optionalParams[4]);
		int duration = (int)optionalParams[5];
		for(int i = 0; i < 60; i++) {
			particles.add(new OrbitingEffectParticle(target, TOCMain.rand.nextFloat() / 4 + .5f, duration, 0x0000FFFF));
			Minecraft.getInstance().particles.addEffect(particles.get(i));
		}
	}
}
