package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.TOCEntity;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesMessage;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.player.TOCPlayer;

public class HealingPotionEffect extends EntityEffect {
	float addedRegen = 0;
	TOCPlayer player;
	int duration;
	public HealingPotionEffect(Entity effected, int duration, float totalHealthHealed) {
		super(effected, "healing_potion", duration);
		addedRegen = totalHealthHealed / (duration / 20);
		player = TOCMain.pm.getPlayer(effected.getUniqueID());
		this.duration = duration;
	}
	
	@Override
	protected void onEffectStart() {
		player.setHealthAndManaRegen(player.getHealthRegen() + addedRegen, player.getManaRegen());
		
		Network.INSTANCE.sendToAllAround(new SpawnParticlesMessage(ParticleSystem.HEAL_WOUNDS_EFFECT_ID, effected.posX, 
				effected.posY, effected.posZ, effected.getEntityId(), duration), 
				new NetworkRegistry.TargetPoint(effected.world.provider.getDimension(), effected.posX, effected.posY, effected.posZ, 100));
	}
	
	@Override
	protected void onEffectEnd(EffectEndType type) {
		player.setHealthAndManaRegen(player.getHealthRegen() - addedRegen, player.getManaRegen());
	}
	
	@Override
	protected void onEffectTick() {}

}
