package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.particles.system.HealEffectSystem;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.player.TOCPlayer;

public class HealWoundsEffect extends EntityEffect{

	static final float INITIAL_HEAL_PCT = .1f; 
	static final float HEALTH_REGEN_MULT = 2.5f;
	int DURATION = 100; //ticks
	
	ParticleSystem healEffect;
	
	public HealWoundsEffect(Entity effected, int duration) {
		super(effected, "heal_wounds", duration);
		healEffect = new HealEffectSystem();
	}

	float addedRegen = 0;
	@Override
	protected void onEffectStart() {
		TOCPlayer pl = TOCMain.pm.getPlayer((EntityPlayer) effected);
		int initialHeal = (int)(pl.getMaxHealth() *  INITIAL_HEAL_PCT);
		pl.adjustVitals(initialHeal, 0);
		addedRegen = pl.getHealthRegen() * HEALTH_REGEN_MULT;
		
		pl.setHealthAndManaRegen(pl.getHealthRegen() + addedRegen, pl.getManaRegen());
		
		Network.INSTANCE.sendToAllAround(new SpawnParticlesPKT(ParticleSystem.HEAL_WOUNDS_EFFECT_ID, effected.posX, 
				effected.posY, effected.posZ, effected.getEntityId(), ticksRemaining),	pl.mcEntity.world.getChunk(pl.mcEntity.getPosition()));
		
	}

	@Override
	protected void onEffectEnd(EffectEndType type) {
		TOCPlayer pl = TOCMain.pm.getPlayer((EntityPlayer) effected);
		pl.setHealthAndManaRegen(pl.getHealthRegen() - addedRegen, pl.getManaRegen());
	}

	@Override
	protected void onEffectTick() {}

}
