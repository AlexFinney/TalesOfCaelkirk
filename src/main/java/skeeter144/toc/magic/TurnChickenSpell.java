package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
		
		if(res.entity == null || !(res.entity instanceof EntityLiving) && !(res.entity instanceof EntityLivingBase))
			return;
		
		if(res.entity instanceof EntityPlayer) {
			turnPlayerToChicken((EntityPlayerMP)res.entity);
		}else {
			turnMobToChicken((EntityLiving) res.entity);
		}
	
	}

	
	private void turnPlayerToChicken(EntityPlayerMP player) {
		ServerEffectHandler.attemptAddNewEffect(player.getUniqueID(), new ChickenEffect(player, null, 300));
	}
	
	private void turnMobToChicken(EntityLiving entity) {
		ServerEffectHandler.attemptAddNewEffect(entity.getUniqueID(), new ChickenEffect(entity, null, 300));
	}
	
	@Override
	public int getManaCost() {
		return 5;
	}

	@Override
	public void performSpellAction(Entity caster) {}

}
