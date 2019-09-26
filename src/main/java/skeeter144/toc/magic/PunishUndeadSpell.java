package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public class PunishUndeadSpell extends ShootableSpell {
	
	static final int DAMAGE = 25;
	public PunishUndeadSpell(String name, String iconName, int cooldown, int trailId) {
		super(name, iconName, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		Entity e = res.entity;
		if(e != null  && !e.world.isRemote) {
			if(e instanceof EntityLiving){
				if(e instanceof EntityZombie || e instanceof EntityZombieHorse || e instanceof EntityZombieVillager || e instanceof AbstractSkeleton) {
					((EntityLiving)e).attackEntityFrom(new TOCDamageSource(DamageType.MAGICAL, proj.getThrower()), DAMAGE);
					((EntityLiving)e).setRevengeTarget(proj.getThrower());
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
