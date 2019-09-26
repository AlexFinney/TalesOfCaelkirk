package skeeter144.toc.particles.system;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;
import skeeter144.toc.particles.particle.PunishUndeadTrailParticle;

public class PunishUndeadSystem extends ParticleSystem {

	List<PunishUndeadTrailParticle> crossParticles = new ArrayList<PunishUndeadTrailParticle>();
	
	boolean spawned = false;
	@Override
	public void spawnParticles() {
		Vec3d pos = new Vec3d(posX, posY, posZ);
		prevPos = pos;
		Vec3d relRight = forwardVec.crossProduct(new Vec3d(0,1,0));
		Vec3d relUp = relRight.crossProduct(forwardVec);
		
		for(int i = -4; i < 4; ++i) {
			float spacing = .08f * i;
			PunishUndeadTrailParticle particle = new PunishUndeadTrailParticle(world, pos.x + relRight.x * spacing, pos.y + .07f, pos.z + relRight.z * spacing, forwardVec);
			crossParticles.add(particle);
			Minecraft.getInstance().particles.addEffect(particle);
		}
		
		for(int i = -6; i < 5; ++i) {
			float spacing = .05f * i;
			PunishUndeadTrailParticle particle = new PunishUndeadTrailParticle(world, pos.x, pos.y + relUp.y * spacing, pos.z, forwardVec);
			crossParticles.add(particle);
			Minecraft.getInstance().particles.addEffect(particle);
		}
	}
	
	Vec3d prevPos;
	public void updatePosition(Vec3d position) {
		double dist = position.distanceTo(prevPos);
		for(PunishUndeadTrailParticle p : crossParticles) {
			p.updatePos((float)dist);
		}
		this.prevPos = position;
	}
	
	Vec3d forwardVec;
	public void setForwardVector(Vec3d lookVec) {
		forwardVec = lookVec;
	}
	
	public void kill() {
		for (Particle p : crossParticles) {
			p.setExpired();
		}
	}

}
