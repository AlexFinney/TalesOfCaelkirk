package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.ServerEffectHandler.EffectAddResult;
import skeeter144.toc.entityeffect.effects.TornadoEffect;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnParticlesMessage;
import skeeter144.toc.particles.system.ParticleSystem;

public class TornadoSpell extends ShootableSpell{

	public TornadoSpell(String name, String iconName, int trailId) {
		super(name, iconName, 200, trailId);
		this.levelRequirement = 1;
		this.manaCost = 40;
	}

	@Override
	public void onCast(Entity caster) {
		super.onCast(caster);
		if(!caster.world.isRemote)
			launchProjectile(caster, id);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		Entity e = res.entityHit;
		if(e != null && !e.world.isRemote) {
			if(e instanceof EntityLivingBase){
				EntityLivingBase el = (EntityLivingBase)e;
				EffectAddResult effRes = ServerEffectHandler.attemptAddNewEffect(e.getUniqueID(), new TornadoEffect(e, 1));
				if(effRes == EffectAddResult.SUCCESS) {
					Network.INSTANCE.sendToAllAround(new SpawnParticlesMessage(ParticleSystem.TORNADO_SYSTEM, el.posX, el.posY, el.posZ), 
							new TargetPoint(el.dimension, el.posX, el.posY, el.posZ, 200));
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
