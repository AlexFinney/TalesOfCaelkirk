package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.sounds.Sounds;

public class WindGustSpell extends ElementalSpell{

	public WindGustSpell(String name, String iconName, int damage, int cooldown, int trailId) {
		super(name, iconName, damage, cooldown, trailId);
		levelRequirement = 1;
		this.sound = Sounds.spell_wind_gust;
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		EntityRayTraceResult entRay = null;
		if(res instanceof EntityRayTraceResult) {
			entRay= (EntityRayTraceResult)res;
		}else {
			return;
		}
		
		Entity e = entRay.getEntity();
		if(e != null  && !e.world.isRemote) {
			if(e instanceof LivingEntity){
				((LivingEntity)e).attackEntityFrom(new TOCDamageSource(DamageType.MAGICAL, proj.getThrower()), damage);
				if(e instanceof LivingEntity)
					((LivingEntity)e).setRevengeTarget(proj.getThrower());
			}
		}
	}

	@Override
	public int getManaCost() {
		return 2;
	}

	@Override
	public void performSpellAction(Entity caster) {}
}
