package skeeter144.toc.particles.system;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import skeeter144.toc.particles.particle.BasicSpellTrailParticle;

public class TornadoSpellTrail extends ParticleSystem{

	public TornadoSpellTrail() {
		super();
		orbitingParticles = new ArrayList<BasicSpellTrailParticle>();
	}
	
	List<BasicSpellTrailParticle> orbitingParticles;
	boolean spawned = false;
	@Override
	public void spawnParticles() {
		for(int i = 0; i < 3; ++i) {
			BasicSpellTrailParticle stp = new BasicSpellTrailParticle(world, posX, posY, posZ, 3, 0x9F9F9F, .3f, false);
			Minecraft.getInstance().particles.addEffect(stp);
		}
	}
}
