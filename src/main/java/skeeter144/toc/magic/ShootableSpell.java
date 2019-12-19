package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public abstract class ShootableSpell extends Spell implements IShootableSpell{
	EntityType<? extends EntityWandProjectile> entityType;
	protected int trailId;
	public ShootableSpell(EntityType<? extends EntityWandProjectile> entityType, String name, String iconName, int cooldown, int trailId) {
		super(name, iconName, cooldown);
		this.trailId = trailId;
		this.entityType = entityType;
	}
	
	@Override
	public int getSpellTrailId() {
		return trailId;
	}
	
	public void onCast(Entity caster) {
		super.onCast(caster);
		if(!caster.world.isRemote)
			launchProjectile(caster, id);
	}

	public EntityType<? extends EntityWandProjectile> getEntityType()
	{
		return entityType;
	}
	
	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		proj.remove();
	}
}
