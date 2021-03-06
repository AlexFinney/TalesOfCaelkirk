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

public class ManaPotionEffect extends EntityEffect {
	float addedRegen = 0;
	TOCPlayer player;
	int duration;
	public ManaPotionEffect(Entity effected, int duration, float totalHealthHealed) {
		super(effected, "mana_potion", duration);
		addedRegen = totalHealthHealed / (duration / 20);
		player = TOCMain.pm.getPlayer((EntityPlayer) effected);
		this.duration = duration;
	}
	
	@Override
	protected void onEffectStart() {
		player.setHealthAndManaRegen(player.getHealthRegen(), player.getManaRegen() + addedRegen);
		
		Network.INSTANCE.sendToAllAround(new SpawnParticlesMessage(ParticleSystem.MANA_REGEN_EFFECT_ID, effected.posX, 
				effected.posY, effected.posZ, effected.getEntityId(), duration), 
				new NetworkRegistry.TargetPoint(effected.world.provider.getDimension(), effected.posX, effected.posY, effected.posZ, 100));
	}
	
	@Override
	protected void onEffectEnd(EffectEndType type) {
		player.setHealthAndManaRegen(player.getHealthRegen(), player.getManaRegen() - addedRegen);
	}
	
	@Override
	protected void onEffectTick() {}

}
