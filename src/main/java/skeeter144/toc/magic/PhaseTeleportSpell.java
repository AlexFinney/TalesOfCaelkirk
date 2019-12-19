package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public class PhaseTeleportSpell extends ShootableSpell {

	public PhaseTeleportSpell(String name, String iconName, int cooldown, int trailId) {
		super(TOCEntityType.PHASE_TELEPORT, name, iconName, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		Vec3d hitPos = res.getHitVec();
		if (hitPos.distanceTo(proj.getThrower().getPositionVector()) < 100) {
			
			for (int i = 0; i < 32; ++i) {
				proj.world.addParticle(ParticleTypes.PORTAL, proj.getThrower().posX, proj.getThrower().posY + TOCMain.rand.nextDouble() * 2.0D,
						 proj.getThrower().posZ, TOCMain.rand.nextGaussian(), 0.0D, TOCMain.rand.nextGaussian());
			}
			if(res instanceof EntityRayTraceResult) {
				EntityRayTraceResult resEnt = (EntityRayTraceResult)res;
				if(resEnt.getEntity() != null && (resEnt.getEntity() instanceof LivingEntity || resEnt.getEntity() instanceof LivingEntity)) {
					resEnt.getEntity().setPositionAndUpdate(proj.getThrower().posX, proj.getThrower().posY, proj.getThrower().posZ);
					resEnt.getEntity().fallDistance = 0;
				}
			}
			
			if(!proj.world.isRemote) {
				proj.getThrower().setPositionAndUpdate(hitPos.x, hitPos.y, hitPos.z);
				proj.getThrower().fallDistance = 0.0F;
			}
			
			for (int i = 0; i < 32; ++i) {
				proj.world.addParticle(ParticleTypes.PORTAL, proj.getThrower().posX, proj.getThrower().posY + TOCMain.rand.nextDouble() * 2.0D,
						 proj.getThrower().posZ, TOCMain.rand.nextGaussian(), 0.0D, TOCMain.rand.nextGaussian());
			}
		} else {
			proj.getThrower().sendMessage(new StringTextComponent("You can only Phase-Teleport within 100 blocks!"));
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
