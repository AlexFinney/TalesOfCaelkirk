package skeeter144.toc.magic;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public class EarthCrumbleSpell extends ElementalSpell {

	public EarthCrumbleSpell(String name, String iconName, int damage, int cooldown, int trailId) {
		super(TOCEntityType.EARTH_CRUMBLE, name ,iconName, damage, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		super.onProjectileImpact(res, proj);
		if(proj.world.isRemote) return;
		
		if(res instanceof EntityRayTraceResult) {
			Entity e = ((EntityRayTraceResult)res).getEntity();
			if(e != null  && !e.world.isRemote) {
				if(e instanceof LivingEntity){
					((LivingEntity)e).attackEntityFrom(new TOCDamageSource(DamageType.MAGICAL, proj.getThrower()), damage);
					((LivingEntity)e).setRevengeTarget(proj.getThrower());
				}
			}
		}else if(res instanceof BlockRayTraceResult) {
			BlockRayTraceResult r = (BlockRayTraceResult)res;
			proj.world.setBlockState(r.getPos(), Blocks.DIAMOND_BLOCK.getDefaultState());
		}
	}

	@Override
	public int getManaCost() {
		return 21;
	}

	@Override
	public void performSpellAction(Entity caster) {}
	
}
