package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.chunk.Chunk;
import skeeter144.toc.TOCMain;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.player.TOCPlayer;

public class ManaPotionEffect extends EntityEffect {
	float addedRegen = 0;
	TOCPlayer player;
	int duration;
	public ManaPotionEffect(Entity effected, int duration, float totalHealthHealed) {
		super(effected, "mana_potion", duration);
		addedRegen = totalHealthHealed / (duration / 20);
		player = TOCMain.pm.getPlayer((PlayerEntity) effected);
		this.duration = duration;
	}
	
	@Override
	protected void onEffectStart() {
		player.setHealthAndManaRegen(player.getHealthRegen(), player.getManaRegen() + addedRegen);
		
		Network.INSTANCE.sendToAllAround(new SpawnParticlesPKT(ParticleSystem.MANA_REGEN_EFFECT_ID, effected.getPosition(), effected.getEntityId(), duration), 
				(Chunk)effected.world.getChunk(effected.getPosition()));
	}
	
	@Override
	protected void onEffectEnd(EffectEndType type) {
		player.setHealthAndManaRegen(player.getHealthRegen(), player.getManaRegen() - addedRegen);
	}
	
	@Override
	protected void onEffectTick() {}

}
