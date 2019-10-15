package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import skeeter144.toc.network.AdjustScreenDimPKT;
import skeeter144.toc.network.Network;

public class ReducedVision extends EntityEffect{

	
	
	float lightBlockedPct = 0;
	public ReducedVision(Entity effected, String name, float lightBlockedPct) {
		super(effected, name, 9999);
		this.lightBlockedPct = lightBlockedPct;
	}

	@Override
	protected void onEffectStart() {
		if(effected instanceof EntityPlayerMP) {
			Network.INSTANCE.sendTo(new AdjustScreenDimPKT(lightBlockedPct), (EntityPlayerMP)effected);
		}
	}

	@Override
	protected void onEffectEnd(EffectEndType type) {
		if(effected instanceof EntityPlayerMP) {
			Network.INSTANCE.sendTo(new AdjustScreenDimPKT(0), (EntityPlayerMP)effected);
		}
	}

	@Override
	protected void onEffectTick() {
		ticksRemaining = 9999;
	}
	
}
