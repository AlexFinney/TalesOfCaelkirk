package skeeter144.toc.magic;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.particles.system.ParticleSystem;

public interface IShootableSpell {
	
	default void launchProjectile(Entity caster, int spellId) {
		World worldIn = caster.world;
		PlayerEntity playerIn = (PlayerEntity)caster;
		
		EntityWandProjectile spell = new EntityWandProjectile(TOCEntityType.EARTH_CRUMBLE, worldIn, playerIn, spellId, 
																ParticleSystem.getNewParticleSystem(spellId));
		 Vec3d vec3d = playerIn.getLook(1.0F);
		spell.shoot(vec3d.getX(), vec3d.getY(), vec3d.getZ(), spell.speed, 0);
		//taken from Entity getForward(). Don't know why it's client only
		Vec3d forward = Vec3d.fromPitchYaw(caster.getPitchYaw());
		Vec3d pos = caster.getEyePosition(1);
		spell.setPosition(pos.x, pos.y, pos.z);
		caster.world.addEntity(spell);	
	}

	//called when the EntityWandProjectile hits something. The projectile is self-disposing
	public void onProjectileImpact(RayTraceResult res,  EntityWandProjectile proj);
	public int getSpellTrailId();
}
