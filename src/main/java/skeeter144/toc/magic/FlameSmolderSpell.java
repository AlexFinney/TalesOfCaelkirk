package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.TOCMain;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public class FlameSmolderSpell extends ElementalSpell {

	public FlameSmolderSpell(String name, String iconName, int damage, int cooldown, int trailId) {
		super(name, iconName, damage, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		if(res instanceof EntityRayTraceResult) {
			Entity e = ((EntityRayTraceResult)res).getEntity();
			if(e instanceof LivingEntity){
				int fireSecs = TOCMain.rand.nextInt(4) + 1;
				e.setFire(fireSecs);
				
				((LivingEntity)e).attackEntityFrom(new TOCDamageSource(DamageType.MAGICAL, proj.getThrower()), damage);
				((LivingEntity)e).setRevengeTarget(proj.getThrower());
			}
		}
		//TODO client code on server
		/*if(proj.getThrower().world.isRemote) {
			Minecraft mc = Minecraft.getInstance();
			for(int i = 0; i < 20; ++i) {
				FireSpellTrailParticle p = new FireSpellTrailParticle(mc.world, res.hitVec.x, res.hitVec.y + (res.entity != null ? 1 : 0), res.hitVec.z,
						(float)(TOCMain.rand.nextGaussian() + 1f) * .75f, 0xFFFFFF,  .75f);
				
				p.setVectorMovementBias(null, .75f);
				mc.particles.addEffect(p);
			}	
		}
		*/
		
		
	}

	@Override
	public int getManaCost() {
		return 29;
	}

	@Override
	public void performSpellAction(Entity caster) {}

}
