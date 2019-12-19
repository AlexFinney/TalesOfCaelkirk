package skeeter144.toc.magic;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ThrowableEntity;
import skeeter144.toc.entity.projectile.EntityWandProjectile;

public abstract class ElementalSpell extends ShootableSpell{
	
	protected int damage;
	public ElementalSpell(EntityType<? extends EntityWandProjectile> entityType, String name, String iconName, int damage, int cooldown, int trailId) {
		super(entityType, name, iconName, cooldown, trailId);
		this.damage = damage;
	}


	
}
