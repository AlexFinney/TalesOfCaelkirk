package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;

public class PoisonEffect extends EntityEffect{
	
	int dps;
	public PoisonEffect(Entity effected, int dps, int duration) {
		super(effected, "Poison", duration);
		this.dps = dps;
		this.ticksRemaining = duration;
		this.effectName = "Poison";
		this.id = 2;
	}

	@Override
	protected void onEffectStart() {}

	@Override
	protected void onEffectEnd(EffectEndType type) {}

	@Override
	protected void onEffectTick() {
		if(this.ticksRemaining % 20 == 0) {
			effected.attackEntityFrom(new TOCDamageSource(DamageType.PURE), dps);
		}
	}

}
