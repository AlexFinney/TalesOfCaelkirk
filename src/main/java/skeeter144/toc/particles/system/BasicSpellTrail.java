package skeeter144.toc.particles.system;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import skeeter144.toc.particles.particle.BasicSpellTrailParticle;

public class BasicSpellTrail extends ParticleSystem{

	
	float pSize, pVel;
	int pNum, pColor;
	public BasicSpellTrail(int numParticlesPerSpawn, float particleSize, float particleVelocity, int particleColor) {
		pSize = particleSize;
		pVel = particleVelocity;
		pColor = particleColor;
		pNum = numParticlesPerSpawn;
		id = ParticleSystem.BASIC_SPELL_TRAIL_ID;
	}

	@Override
	public void spawnParticles() {
		for(int i = 0; i < pNum; ++i) {
			Particle stp = new BasicSpellTrailParticle(world, posX, posY, posZ, pSize, pColor, pVel, false);
			Minecraft.getInstance().particles.addEffect(stp);
		}
	}
	

	public void setColor(int color) {
		this.pColor = color;
	}
	
	public void setParticleSize(float particleSize) {
		this.pSize = particleSize;
	}
	
	public void setParticleVelocity(float particleVelocity) {
		this.pVel = particleVelocity;
	}
	
	
	
}
