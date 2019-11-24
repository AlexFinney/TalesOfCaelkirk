package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public class EarthCrumbleSpell extends ElementalSpell {

	public EarthCrumbleSpell(String name, String iconName, int damage, int cooldown, int trailId) {
		super(name, iconName, damage, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		if(res instanceof EntityRayTraceResult) {
			Entity e = ((EntityRayTraceResult)res).getEntity();
			if(e != null  && !e.world.isRemote) {
				if(e instanceof LivingEntity){
					((LivingEntity)e).attackEntityFrom(new TOCDamageSource(DamageType.MAGICAL, proj.getThrower()), damage);
					((LivingEntity)e).setRevengeTarget(proj.getThrower());
				}
			}
		}
		
	}

	@Override
	public int getManaCost() {
		return 21;
	}

	@Override
	public void performSpellAction(Entity caster) {}

}
