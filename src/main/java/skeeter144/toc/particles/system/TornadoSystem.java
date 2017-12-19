package skeeter144.toc.particles.system;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import skeeter144.toc.entityeffect.effects.TornadoEffect;
import skeeter144.toc.particles.particle.TornadoParticle;

public class TornadoSystem extends ParticleSystem{

	float height;
	int level = 1;
	public TornadoSystem() {
		this.height = TornadoEffect.getHeightForLevel(level);
	}

	
	@Override
	public void spawnParticles() {
		int particles = 100;
		Random rand = new Random(System.currentTimeMillis());
		for(float i = 0; i <= particles; ++i)
		{
			float curHeight = i / height;
			Minecraft.getMinecraft().effectRenderer.addEffect(new TornadoParticle(world, posX, posY + curHeight * 2, posZ, getRadiusForheight(curHeight), TornadoEffect.getDurationForLevel(level)));
		}
	}

	private float getRadiusForheight(float height)
	{
		return .2f + .2f * height;
	}
	
}
