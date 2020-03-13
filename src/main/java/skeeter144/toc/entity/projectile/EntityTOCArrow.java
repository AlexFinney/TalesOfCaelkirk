package skeeter144.toc.entity.projectile;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.items.weapons.TOCArrow;

public class EntityTOCArrow extends ArrowEntity {

	public TOCArrow toc_arrow;

	public EntityTOCArrow(EntityType<? extends ArrowEntity> e, World worldIn) {
		super(e, worldIn);
	}

	public EntityTOCArrow(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityTOCArrow(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		Entity entity = null;
		RayTraceResult.Type type = raytraceResultIn.getType();
		if (type == RayTraceResult.Type.ENTITY) {
			EntityRayTraceResult entRay = ((EntityRayTraceResult) raytraceResultIn);
			entity = entRay.getEntity();
		}

		if (entity != null) {
			int i = (int) this.getDamage();

			DamageSource damagesource;

			if (this.shootingEntity == null) {
				damagesource = DamageSource.causeArrowDamage(this, this);
			} else {
				damagesource = DamageSource.causeIndirectDamage(this, (LivingEntity)getShooter());
			}

			if (entity.attackEntityFrom(damagesource, (float) i)) {
				if (entity instanceof LivingEntity) {
					LivingEntity entitylivingbase = (LivingEntity) entity;
					Entity shooter = getShooter();

					if (!this.world.isRemote) {
						entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
					}

					if (shooter != null && shooter instanceof LivingEntity) {
						EnchantmentHelper.applyThornEnchantments(entitylivingbase, shooter);
						EnchantmentHelper.applyArthropodEnchantments((LivingEntity) shooter, entitylivingbase);
					}

					this.arrowHit(entitylivingbase);

//					if (shooter != null && entitylivingbase != shooter && entitylivingbase instanceof PlayerEntity
//							&& shooter instanceof ServerPlayerEntity) {
//						((ServerPlayerEntity) shooter).connection.sendPacket(new IPacket(6, 0.0F));
//					}
				}

				this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

				if (TOCMain.rand.nextFloat() > toc_arrow.destroyChance) {
//					ItemEntity itemEntity = new ItemEntity(this.world);
//					itemEntity.setItem(new ItemStack(toc_arrow));
//					itemEntity.setPosition(this.posX, this.posY, this.posZ);
//					this.world.addEntity(itemEntity);
				}
			} else {
				this.getMotion().scale(-0.10000000149011612D);
				this.rotationYaw += 180.0F;
				this.prevRotationYaw += 180.0F;

				if (!this.world.isRemote
						&& this.getMotion().x * this.getMotion().x + this.getMotion().y * this.getMotion().y
								+ this.getMotion().z * this.getMotion().z < 0.0010000000474974513D) {
					if (this.pickupStatus == ArrowEntity.PickupStatus.ALLOWED) {
						this.entityDropItem(this.getArrowStack(), 0.1F);
					}
				}
			}
		}
		super.onHit(raytraceResultIn);
	}

	@Override
	public void tick() {
		super.tick();
		for (int k = 0; k < 4; ++k) {
			this.world.addParticle(ParticleTypes.CRIT, this.getPosX() + this.getMotion().x * (double) k / 4.0D,
					this.getPosY() + this.getMotion().y * (double) k / 4.0D,
					this.getPosZ() + this.getMotion().z * (double) k / 4.0D, -this.getMotion().x, -this.getMotion().y + 0.2D,
					-this.getMotion().z);
		}
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(toc_arrow);
	}

	@Override
	public int getColor() {
		return toc_arrow.color;
	}
}
