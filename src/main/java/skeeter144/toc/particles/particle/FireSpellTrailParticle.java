package skeeter144.toc.particles.particle;

import net.minecraft.world.World;

public class FireSpellTrailParticle extends BasicSpellTrailParticle{

	public FireSpellTrailParticle(World worldIn, double posXIn, double posYIn, double posZIn, float size, int color, float pVel) {
		super(worldIn, posXIn, posYIn, posZIn, size, color, pVel, false);
		this.setColor((color & 0xFF0000) / 255f, (color & 0x00FF00) / 255f, (color & 0x0000FF) / 255f);
		this.particleGravity = .8f;
		this.multipleParticleScaleBy(.5f);
	}
}
