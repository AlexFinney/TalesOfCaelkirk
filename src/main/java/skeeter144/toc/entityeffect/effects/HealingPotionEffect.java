package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.player.TOCPlayer;

public class HealingPotionEffect extends EntityEffect {
	float addedRegen = 0;
	TOCPlayer player;
	int duration;
	public HealingPotionEffect(Entity effected, int duration, float totalHealthHealed) {
		super(effected, "healing_potion", duration);
		addedRegen = totalHealthHealed / (duration / 20);
		player = TOCMain.pm.getPlayer((EntityPlayer) effected);
		this.duration = duration;
	}
	
	@Override
	protected void onEffectStart() {
		player.setHealthAndManaRegen(player.getHealthRegen() + addedRegen, player.getManaRegen());
		
		Network.INSTANCE.sendToAllAround(new SpawnParticlesPKT(ParticleSystem.HEAL_WOUNDS_EFFECT_ID, effected.posX, 
				effected.posY, effected.posZ, effected.getEntityId(), duration), player.mcEntity.world.getChunk(player.mcEntity.getPosition()));
	}
	
	@Override
	protected void onEffectEnd(EffectEndType type) {
		player.setHealthAndManaRegen(player.getHealthRegen() - addedRegen, player.getManaRegen());
	}
	
	@Override
	protected void onEffectTick() {}

}
