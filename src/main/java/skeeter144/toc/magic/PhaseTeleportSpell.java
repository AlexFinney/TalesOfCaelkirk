package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Particles;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public class PhaseTeleportSpell extends ShootableSpell {

	public PhaseTeleportSpell(String name, String iconName, int cooldown, int trailId) {
		super(name, iconName, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		Vec3d hitPos = res.hitVec;
		if (hitPos.distanceTo(proj.getThrower().getPositionVector()) < 100) {
			
			for (int i = 0; i < 32; ++i) {
				proj.world.spawnParticle(Particles.PORTAL, proj.getThrower().posX, proj.getThrower().posY + TOCMain.rand.nextDouble() * 2.0D,
						 proj.getThrower().posZ, TOCMain.rand.nextGaussian(), 0.0D, TOCMain.rand.nextGaussian());
			}
			
			if(res.entity != null && (res.entity instanceof EntityLiving || res.entity instanceof EntityLivingBase)) {
				res.entity.setPositionAndUpdate(proj.getThrower().posX, proj.getThrower().posY, proj.getThrower().posZ);
				res.entity.fallDistance = 0;
			}
			
			if(!proj.world.isRemote) {
				proj.getThrower().setPositionAndUpdate(hitPos.x, hitPos.y, hitPos.z);
				proj.getThrower().fallDistance = 0.0F;
			}
			
			for (int i = 0; i < 32; ++i) {
				proj.world.spawnParticle(Particles.PORTAL, proj.getThrower().posX, proj.getThrower().posY + TOCMain.rand.nextDouble() * 2.0D,
						 proj.getThrower().posZ, TOCMain.rand.nextGaussian(), 0.0D, TOCMain.rand.nextGaussian());
			}
		} else {
			proj.getThrower().sendMessage(new TextComponentString("You can only Phase-Teleport within 100 blocks!"));
		}
	}

	@Override
	public int getManaCost() {
		return 5;
	}

	@Override
	public void performSpellAction(Entity caster) {
	}

}
