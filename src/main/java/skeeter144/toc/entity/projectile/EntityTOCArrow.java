package skeeter144.toc.entity.projectile;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.items.weapons.TOCArrow;

public class EntityTOCArrow extends EntityTippedArrow {

	public TOCArrow toc_arrow;

	public EntityTOCArrow(World worldIn) {
		super(worldIn);
	}

	public EntityTOCArrow(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityTOCArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
	}

	protected void onHit(RayTraceResult raytraceResultIn) {
		Entity entity = raytraceResultIn.entity;

		if (entity != null) {
			int i = (int) this.getDamage();

			DamageSource damagesource;

			if (this.shootingEntity == null) {
				damagesource = DamageSource.causeArrowDamage(this, this);
			} else {
				damagesource = DamageSource.causeArrowDamage(this, func_212360_k());
			}

			if (entity.attackEntityFrom(damagesource, (float) i)) {
				if (entity instanceof EntityLivingBase) {
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
					Entity shooter = func_212360_k();
					
					if (!this.world.isRemote) {
						entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
					}

					if (shooter != null && shooter instanceof EntityLivingBase) {
						EnchantmentHelper.applyThornEnchantments(entitylivingbase, shooter);
						EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) shooter,
								entitylivingbase);
					}

					this.arrowHit(entitylivingbase);

					if (shooter != null && entitylivingbase != shooter
							&& entitylivingbase instanceof EntityPlayer
							&& shooter instanceof EntityPlayerMP) {
						((EntityPlayerMP) shooter).connection
								.sendPacket(new SPacketChangeGameState(6, 0.0F));
					}
				}

				this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

				if (TOCMain.rand.nextFloat() > toc_arrow.destroyChance) {
					EntityItem itemEntity = new EntityItem(this.world);
					itemEntity.setItem(new ItemStack(toc_arrow));
					itemEntity.setPosition(this.posX, this.posY, this.posZ);
					this.world.spawnEntity(itemEntity);
					System.out.println("spawned");
				}
			} else {
				this.motionX *= -0.10000000149011612D;
				this.motionY *= -0.10000000149011612D;
				this.motionZ *= -0.10000000149011612D;
				this.rotationYaw += 180.0F;
				this.prevRotationYaw += 180.0F;

				if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY
						+ this.motionZ * this.motionZ < 0.0010000000474974513D) {
					if (this.pickupStatus == EntityArrow.PickupStatus.ALLOWED) {
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
ParticleType.registerAll();
		for (int k = 0; k < 4; ++k) {
			this.world.spawnParticle(Particles.CRIT, this.posX + this.motionX * (double) k / 4.0D,
					this.posY + this.motionY * (double) k / 4.0D, this.posZ + this.motionZ * (double) k / 4.0D,
					-this.motionX, -this.motionY + 0.2D, -this.motionZ);
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
