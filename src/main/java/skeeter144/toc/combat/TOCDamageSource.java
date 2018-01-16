package skeeter144.toc.combat;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import skeeter144.toc.combat.CombatManager.DamageType;

public class TOCDamageSource extends DamageSource{
	public final DamageType type;
	public final Entity source;
	public TOCDamageSource(DamageType type) {
		super("");
		this.type = type;
		source = null;
	}
	
	public TOCDamageSource(DamageType type, Entity source) {
		super("");
		this.type = type;
		this.source = source;
	}
	
	public TOCDamageSource(DamageType type, DamageSource other) {
		super("");
		this.type = type;
		source = other.getTrueSource();
	}

	@Override
	public Entity getTrueSource() {
		return source;
	}
}
