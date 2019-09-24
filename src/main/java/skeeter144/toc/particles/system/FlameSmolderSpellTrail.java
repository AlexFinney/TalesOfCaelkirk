package skeeter144.toc.particles.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import skeeter144.toc.particles.particle.FireSpellTrailParticle;

public class FlameSmolderSpellTrail extends BasicSpellTrail{

	public FlameSmolderSpellTrail(int numParticlesPerSpawn, float particleSize, float particleVelocity, int particleColor) {
		super(numParticlesPerSpawn, particleSize, particleVelocity, particleColor);
	}
	
	@Override
	public void spawnParticles() {
		for(int i = 0; i < pNum; ++i) {
			Particle stp = new FireSpellTrailParticle(world, posX, posY, posZ, pSize, pColor, pVel);
			Minecraft.getInstance().effectRenderer.addEffect(stp);
		}
	}
	
}
