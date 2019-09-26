package skeeter144.toc.particles.particle;

import javax.vecmath.Vector3d;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;

public class TornadoParticle extends Particle{

	float radius;
	Vector3d origin;
	
	int ageOffset;
	
	public TornadoParticle(World worldIn, double posX, double posY, double posZ, float radius, int maxTicks) {
		super(worldIn, posX, posY, posZ);
		this.setMaxAge(maxTicks);
		particleScale = 2.0f + radius * 5;
		int randRadius = TOCMain.rand.nextInt(2) + 1;
		this.radius = radius / randRadius;
		
		origin = new Vector3d(posX, posY, posZ);
		ageOffset = TOCMain.rand.nextInt() % 200;
		int usedAge = age * 2 + ageOffset;
		double x = origin.x + radius * Math.cos((usedAge % 20) /(2 * Math.PI));
		double z = origin.z + radius * Math.sin((usedAge % 20) / (2 * Math.PI));
		
		this.setPosition(x,  posY,  z);
		
		this.setParticleTextureIndex(86);
	}
	
	@Override
	public void tick() {
		 super.tick();
		 
		int usedAge = age + ageOffset;
		double x = origin.x + radius * Math.cos((usedAge % 200) / (2 * Math.PI));
		double z = origin.z + radius * Math.sin((usedAge % 200) / (2 * Math.PI));
		this.setPosition(x,  posY,  z);
	}
	
	
	

}
