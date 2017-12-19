package skeeter144.toc.entityeffect.effects;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import skeeter144.toc.network.AdjustPlayersScreenDim;
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
			Network.INSTANCE.sendTo(new AdjustPlayersScreenDim(lightBlockedPct), (EntityPlayerMP)effected);
		}
	}

	@Override
	protected void onEffectEnd(EffectEndType type) {
		if(effected instanceof EntityPlayerMP) {
			Network.INSTANCE.sendTo(new AdjustPlayersScreenDim(0), (EntityPlayerMP)effected);
		}
	}

	@Override
	protected void onEffectTick() {
		ticksRemaining = 9999;
	}
	
}
