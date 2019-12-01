package skeeter144.toc.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import skeeter144.toc.magic.IShootableSpell;
import skeeter144.toc.magic.Spells;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.particles.system.PunishUndeadSystem;

public class EntityWandProjectile extends ThrowableEntity{

	int spellId;
	ParticleSystem trail;	
	protected LivingEntity thrower;
	public Vec3d movementVector;
	boolean repeatedTrailSpawn = true;
	boolean spawnedOnce = false;
	public EntityWandProjectile(EntityType<? extends ThrowableEntity> type, World worldIn, LivingEntity throwerIn, int spellId, ParticleSystem trail) {
		super(type, worldIn);
		this.spellId = spellId;
		Spells.getSpell(spellId);
		this.thrower = throwerIn;
		this.trail = trail;
		
		if(throwerIn.world.isRemote)
			movementVector = thrower.getLookVec();
	}
	
	public LivingEntity getThrower() {
		return thrower;
	}
	
	@Override
	public void tick() {
		super.tick();		
		if(world.isRemote) 
		{
			if(ticksExisted % 1 == 0)
			{
				if(trail != null) 
				{
					trail.updatePosition(world, posX, posY, posZ);
					if(repeatedTrailSpawn)
						trail.spawnParticles();
					else {
						if(!spawnedOnce) {
							trail.spawnParticles();
							spawnedOnce = true;
							
						}
						if(trail instanceof PunishUndeadSystem) {
							((PunishUndeadSystem)trail).updatePosition(new Vec3d(this.posX, this.posY, this.posZ));
						}
					}
				}
			}
		}
	}
	
	public ParticleSystem getTrail() {
		return trail;
	}
	
	public void setTrail(ParticleSystem trail) {
		this.trail = trail;
	}

	@Override
	protected float getGravityVelocity() {
		return 0f;
	}
	
	enum ParticleType{
		SMALL,
		MEDIUM,
		LARGE
	}
	
	public void disableRepeatedParticles() {
		repeatedTrailSpawn = false;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		Entity entity = null;
		if(result instanceof EntityRayTraceResult)
			entity = ((EntityRayTraceResult)result).getEntity();
		
		if(entity != null && entity == thrower) { //return on server or client if player shoots himself
			return;
		}
		
		((IShootableSpell)Spells.getSpell(spellId)).onProjectileImpact(result, this);
		
		if(trail instanceof PunishUndeadSystem) {
			((PunishUndeadSystem)trail).kill();
		}
	}

	@Override
	protected void registerData() {
		// TODO Auto-generated method stub
		
	}
}
