package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.ServerEffectHandler.EffectAddResult;
import skeeter144.toc.entityeffect.effects.HealWoundsEffect;

public class HealWoundsSpell extends Spell {

	public HealWoundsSpell(String name, String iconName, int cooldown) {
		super(name, iconName, cooldown);
	}

	@Override
	public void onCast(Entity caster) {
		super.onCast(caster);
		if(!caster.world.isRemote) {
			
		}
	}

	@Override
	public int getManaCost() {
		return 0;
	}

	@Override
	public void performSpellAction(Entity caster) {
		LivingEntity el = (LivingEntity)caster;
		EffectAddResult effRes = ServerEffectHandler.attemptAddNewEffect(el.getUniqueID(), new HealWoundsEffect(el, 400));
	}
	
}
