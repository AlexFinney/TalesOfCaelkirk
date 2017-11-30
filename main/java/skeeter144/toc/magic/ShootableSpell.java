package skeeter144.toc.magic;

import net.minecraft.entity.Entity;

public abstract class ShootableSpell extends Spell implements IShootableSpell{

	protected int trailId;
	public ShootableSpell(String name, String iconName, int cooldown, int trailId) {
		super(name, iconName, cooldown);
		this.trailId = trailId;
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

}
