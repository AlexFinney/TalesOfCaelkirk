package skeeter144.toc.particles.particle;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3d;

import org.lwjgl.util.vector.Quaternion;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;

public class OrbitingEffectParticle extends Particle{

	Vector3d axis;
	float radius;
	float heightOffset;
	Vector3d origin;
	Entity target;
	int ageOffset;
	double rotateSpeed = 1;
	public OrbitingEffectParticle(Entity target, float radius, int duration, int color) {
		super(target.world, target.posX, target.posY, target.posZ);
		this.particleMaxAge = duration;
		this.radius = radius;
		this.target = target;
		
		
		this.setParticleTextureIndex(144);
		
		ageOffset = TOCMain.rand.nextInt() % 200;
		int usedAge = particleAge * 2 + ageOffset;
		this.setRBGColorF((color << 24 & 0xFF) / 255, (color >> 16 & 0xFF) / 255, (color >> 8 & 0xFF) / 255);
		heightOffset = (float)TOCMain.rand.nextDouble() * target.getEyeHeight() + .2f;
		this.posY = target.posY +  heightOffset;
		this.particleGravity = 0;
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		
		origin = new Vector3d(posX, posY, posZ);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		int usedAge = particleAge * 2 + ageOffset;
		double x = target.posX + radius * Math.cos((usedAge / 2f) / (2 * Math.PI));
		double z = target.posZ + radius * Math.sin((usedAge / 2f) / (2 * Math.PI));
		this.setPosition(x,  target.posY + heightOffset,  z);
		
	}
	
	
	
}


