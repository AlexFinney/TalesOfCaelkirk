package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public class WindGustSpell extends ElementalSpell{

	public WindGustSpell(String name, String iconName, int damage, int cooldown, int trailId) {
		super(name, iconName, damage, cooldown, trailId);
		levelRequirement = 1;
		manaCost = 10;
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		Entity e = res.entityHit;
		if(e != null  && !e.world.isRemote) {
			if(e instanceof EntityLivingBase){
				((EntityLivingBase)e).attackEntityFrom(new TOCDamageSource(DamageType.MAGICAL, proj.getThrower()), damage);
				if(e instanceof EntityLiving)
					((EntityLiving)e).setRevengeTarget(proj.getThrower());
			}
		}
	}

	@Override
	public int getManaCost() {
		return 10;
	}

	@Override
	public void performSpellAction(Entity caster) {}
}
