package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ThrowableEntity;

public abstract class ShootableSpell extends Spell implements IShootableSpell{
	EntityType<? extends ThrowableEntity> entityType;
	protected int trailId;
	public ShootableSpell(String name, EntityType<? extends ThrowableEntity> entityType, String iconName, int cooldown, int trailId) {
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

	public EntityType<? extends ThrowableEntity> getEntityType()
	{
		return entityType;
	}
}
