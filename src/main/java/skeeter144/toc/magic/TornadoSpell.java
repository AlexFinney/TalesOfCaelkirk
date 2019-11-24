package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.chunk.Chunk;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.ServerEffectHandler.EffectAddResult;
import skeeter144.toc.entityeffect.effects.TornadoEffect;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesPKT;
import skeeter144.toc.particles.system.ParticleSystem;

public class TornadoSpell extends ShootableSpell{

	public TornadoSpell(String name, String iconName, int trailId) {
		super(name, iconName, 200, trailId);
		this.levelRequirement = 1;
	}

	@Override
	public void onCast(Entity caster) {
		super.onCast(caster);
		if(!caster.world.isRemote)
			launchProjectile(caster, id);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		EntityRayTraceResult entRay = null;
		if(res instanceof EntityRayTraceResult) {
			entRay= (EntityRayTraceResult)res;
		}else {
			return;
		}
		
		Entity e = entRay.getEntity();
		if(e != null && !e.world.isRemote) {
			if(e instanceof LivingEntity){
				LivingEntity el = (LivingEntity)e;
				EffectAddResult effRes = ServerEffectHandler.attemptAddNewEffect(e.getUniqueID(), new TornadoEffect(e, 1));
				if(effRes == EffectAddResult.SUCCESS) {
					Network.INSTANCE.sendToAllAround(new SpawnParticlesPKT(ParticleSystem.TORNADO_SYSTEM, el.getPosition()), 
																		  (Chunk) el.world.getChunk(el.getPosition()));
				}
					
			}
		}
	}

	@Override
	public int getManaCost() {
		return 40;
	}

	@Override
	public void performSpellAction(Entity caster) {}

	
}
