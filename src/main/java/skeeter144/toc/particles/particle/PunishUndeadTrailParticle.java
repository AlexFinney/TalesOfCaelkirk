package skeeter144.toc.particles.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PunishUndeadTrailParticle extends Particle{

	float offsetX, offsetY, offsetZ;
	Vec3d forward;
	public PunishUndeadTrailParticle(World worldIn, double posXIn, double posYIn, double posZIn, Vec3d forward) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = false;
		this.particleGravity = 0;
		this.motionX = 0;
		this.motionY = 0; 
		this.motionZ = 0;
		this.forward = forward;
		this.setMaxAge(100);
		this.setParticleTextureIndex(1);
		this.particleScale = 2.0f;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
	    this.prevPosY = this.posY;
	    this.prevPosZ = this.posZ;

	     if (this.particleAge++ >= this.particleMaxAge)
	     {
	    	 this.setExpired();
	     }
	}
	
	public void updatePos(float dist) {
		 this.prevPosX = posX;
		 this.prevPosY = posY;
		 this.prevPosZ = posZ;
		 
		 this.posX = posX + forward.x * dist;
	     this.posY = posY + forward.y * dist;
	     this.posZ = posZ + forward.z * dist;
	}
}
