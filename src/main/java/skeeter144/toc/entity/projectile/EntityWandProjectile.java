package skeeter144.toc.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import skeeter144.toc.magic.IShootableSpell;
import skeeter144.toc.magic.Spells;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.particles.system.PunishUndeadSystem;

@Mod.EventBusSubscriber
public class EntityWandProjectile extends EntityThrowable{

	int spellId;
	ParticleSystem trail;	
	protected EntityLivingBase thrower;
	public Vec3d movementVector;
	boolean repeatedTrailSpawn = true;
	boolean spawnedOnce = false;
	public EntityWandProjectile(World worldIn, EntityLivingBase throwerIn, int spellId, ParticleSystem trail) {
		super(worldIn, throwerIn);
		this.spellId = spellId;
		Spells.getSpell(spellId);
		this.thrower = throwerIn;
		this.trail = trail;
		
		if(throwerIn.world.isRemote)
			movementVector = thrower.getLookVec();
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		if(result.entityHit != null && result.entityHit == thrower) { //retrun on server or client if player shoots himself
			return;
		}
		((IShootableSpell)Spells.getSpell(spellId)).onProjectileImpact(result, this);
		setDead();
		
		if(trail instanceof PunishUndeadSystem) {
			((PunishUndeadSystem)trail).kill();
		}
	}
	
	public EntityLivingBase getThrower() {
		return thrower;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();		
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
		
		if(ticksExisted > 60)
			setDead();
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
}
