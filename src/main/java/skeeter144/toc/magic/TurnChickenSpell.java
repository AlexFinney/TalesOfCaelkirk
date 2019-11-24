package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.effects.ChickenEffect;

public class TurnChickenSpell extends ShootableSpell{

	public TurnChickenSpell(String name, String iconName, int cooldown, int trailId) {
		super(name, iconName, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		if(proj.world.isRemote)
			return;
		EntityRayTraceResult entRay = null;
		if(res instanceof EntityRayTraceResult) {
			entRay= (EntityRayTraceResult)res;
		}else {
			return;
		}
		
		Entity e = entRay.getEntity();
		if(entRay.getEntity() == null || !(entRay.getEntity() instanceof LivingEntity) && !(entRay.getEntity() instanceof LivingEntity))
			return;
		
		if(entRay.getEntity() instanceof PlayerEntity) {
			turnPlayerToChicken((ServerPlayerEntity)entRay.getEntity());
		}else {
			turnMobToChicken((LivingEntity) entRay.getEntity());
		}
	
	}

	
	private void turnPlayerToChicken(ServerPlayerEntity player) {
		ServerEffectHandler.attemptAddNewEffect(player.getUniqueID(), new ChickenEffect(player, null, 300));
	}
	
	private void turnMobToChicken(LivingEntity entity) {
		ServerEffectHandler.attemptAddNewEffect(entity.getUniqueID(), new ChickenEffect(entity, null, 300));
	}
	
	@Override
	public int getManaCost() {
		return 5;
	}

	@Override
	public void performSpellAction(Entity caster) {}

}
