package skeeter144.toc.entity.projectile;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.magic.ShootableSpell;
import skeeter144.toc.magic.Spells;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.particles.system.PunishUndeadSystem;

public class EntityWandProjectile extends Entity implements IProjectile{

	public float speed = 1;
	int spellId;
	ParticleSystem trail;	
	protected LivingEntity thrower;
	public Vec3d movementVector;
	boolean repeatedTrailSpawn = true;
	boolean spawnedOnce = false;
	public EntityWandProjectile(EntityType<? extends EntityWandProjectile> type, World worldIn, LivingEntity throwerIn, int spellId, ParticleSystem trail) {
		this(type, worldIn);
		init(spellId, throwerIn, trail);
	}
	
	public EntityWandProjectile(EntityType<? extends EntityWandProjectile> type, World worldIn)
	{
		super(type, worldIn);
		this.setNoGravity(true);
	}
	
	public EntityWandProjectile(FMLPlayMessages.SpawnEntity packet, World worldIn) {
		this(TOCEntityType.EARTH_CRUMBLE, worldIn);
	}

	public void init(int spellId, LivingEntity thrower, ParticleSystem trail)
	{
		this.spellId = spellId;
		Spells.getSpell(spellId);
		this.thrower = thrower;
		this.trail = trail;
		movementVector = thrower.getLookVec();
	}
	
	public LivingEntity getThrower() {
		return thrower;
	}
	
	@Override
	public void tick() {
		if(ticksExisted > 40) {
			this.remove();
			return;
		}
		
		super.tick();	
		handleMovement();
		if(world.isRemote) 
		{
			if(ticksExisted % 1 == 0)
			{
				if(trail != null) 
				{
					trail.updatePosition(world, getPosX(), getPosY(), getPosZ());
					if(repeatedTrailSpawn)
						trail.spawnParticles();
					else {
						if(!spawnedOnce) {
							trail.spawnParticles();
							spawnedOnce = true;
							
						}
						if(trail instanceof PunishUndeadSystem) {
							((PunishUndeadSystem)trail).updatePosition(new Vec3d(this.getPosX(), this.getPosY(), this.getPosZ()));
						}
					}
				}
			}
		}
	}
	
	void handleMovement()
	{
//		 Vec3d vec3d = this.getMotion();
//	      RayTraceResult raytraceresult = ProjectileHelper.func_221267_a(this, this.getBoundingBox().expand(vec3d).grow(1.0D), (p_213879_1_) -> {
//	         return !p_213879_1_.isSpectator() && p_213879_1_ != this.thrower;
//	      }, RayTraceContext.BlockMode.OUTLINE, true);
//	      if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
//	         this.onHit(raytraceresult);
//	      }
//
//	      this.getPosX() += vec3d.x;
//	      this.getPosY() += vec3d.y;
//	      this.getPosZ() += vec3d.z;
//	      float f = MathHelper.sqrt(func_213296_b(vec3d));
//	      this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
//
//	      for(this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
//	         ;
//	      }

	      while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
	         this.prevRotationPitch += 360.0F;
	      }

	      while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
	         this.prevRotationYaw -= 360.0F;
	      }

	      while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
	         this.prevRotationYaw += 360.0F;
	      }

	      this.rotationPitch = MathHelper.lerp(0.2F, this.prevRotationPitch, this.rotationPitch);
	      this.rotationYaw = MathHelper.lerp(0.2F, this.prevRotationYaw, this.rotationYaw);
	      float f1 = 0.99F;
	      float f2 = 0.06F;
	      if (!this.world.isMaterialInBB(this.getBoundingBox(), Material.AIR)) {
	         this.remove();
	      } else if (this.isInWaterOrBubbleColumn()) {
	         this.remove();
	      } else {
//	         this.setMotion(vec3d.scale((double)0.99F));
	         if (!this.hasNoGravity()) {
	            this.setMotion(this.getMotion().add(0.0D, (double)-0.06F, 0.0D));
	         }

	         this.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
	      }
	}
	
	public ParticleSystem getTrail() {
		return trail;
	}
	
	public void setTrail(ParticleSystem trail) {
		this.trail = trail;
	}
	
	enum ParticleType{
		SMALL,
		MEDIUM,
		LARGE
	}
	
	public void disableRepeatedParticles() {
		repeatedTrailSpawn = false;
	}

//	@Override
//	protected void onImpact(RayTraceResult result) {
//		Entity entity = null;
//		if(result instanceof EntityRayTraceResult)
//			entity = ((EntityRayTraceResult)result).getEntity();
//		
//		if(entity != null && entity == thrower) { //return on server or client if player shoots himself
//			return;
//		}
//		
//		((IShootableSpell)Spells.getSpell(spellId)).onProjectileImpact(result, this);
//		
//		if(trail instanceof PunishUndeadSystem) {
//			((PunishUndeadSystem)trail).kill();
//		}
//	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		Vec3d vec3d = (new Vec3d(x, y, z)).normalize()
				.add(this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy,
						this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy,
						this.rand.nextGaussian() * (double) 0.0075F * (double) inaccuracy)
				.scale((double) velocity);
		this.setMotion(vec3d);
//		float f = MathHelper.sqrt(func_213296_b(vec3d));
//		this.rotationYaw = (float) (MathHelper.atan2(vec3d.x, z) * (double) (180F / (float) Math.PI));
//		this.rotationPitch = (float) (MathHelper.atan2(vec3d.y, (double) f) * (double) (180F / (float) Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}

	public void onHit(RayTraceResult res) {
		ShootableSpell spell = (ShootableSpell) Spells.getSpell(spellId);
		RayTraceResult.Type raytraceresult$type = res.getType();
		if (raytraceresult$type == RayTraceResult.Type.ENTITY && this.thrower != null 
				|| raytraceresult$type == RayTraceResult.Type.BLOCK && !this.world.isRemote) 
		{
			spell.onProjectileImpact(res, this);
			this.remove();
		} 
	}

	@Override
	protected void registerData() {
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		spellId = compound.getInt("spellId");
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putInt("spellId", spellId);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
