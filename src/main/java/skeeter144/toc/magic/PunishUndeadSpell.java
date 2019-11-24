package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public class PunishUndeadSpell extends ShootableSpell {
	
	static final int DAMAGE = 25;
	public PunishUndeadSpell(String name, String iconName, int cooldown, int trailId) {
		super(name, iconName, cooldown, trailId);
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
				if(e instanceof ZombieEntity || e instanceof ZombieHorseEntity || e instanceof ZombieVillagerEntity || e instanceof SkeletonEntity) {
					((LivingEntity)e).attackEntityFrom(new TOCDamageSource(DamageType.MAGICAL, proj.getThrower()), DAMAGE);
					((LivingEntity)e).setRevengeTarget(proj.getThrower());
				}
				
			}
		}
	}

	@Override
	public int getManaCost() {
		return 15;
	}

	@Override
	public void performSpellAction(Entity caster) {}

}
