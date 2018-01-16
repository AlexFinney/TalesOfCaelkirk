package skeeter144.toc.entity.projectile;

import java.lang.reflect.Field;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
		Entity entity = raytraceResultIn.entityHit;

		if (entity != null) {
			int i = (int) this.getDamage();

			DamageSource damagesource;

			if (this.shootingEntity == null) {
				damagesource = DamageSource.causeArrowDamage(this, this);
			} else {
				damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
			}

			if (entity.attackEntityFrom(damagesource, (float) i)) {
				if (entity instanceof EntityLivingBase) {
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

					if (!this.world.isRemote) {
						entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
					}

					if (this.shootingEntity instanceof EntityLivingBase) {
						EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
						EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity,
								entitylivingbase);
					}

					this.arrowHit(entitylivingbase);

					if (this.shootingEntity != null && entitylivingbase != this.shootingEntity
							&& entitylivingbase instanceof EntityPlayer
							&& this.shootingEntity instanceof EntityPlayerMP) {
						((EntityPlayerMP) this.shootingEntity).connection
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
				this.setDead();
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

					this.setDead();
				}
			}
		} else {
			BlockPos blockpos = raytraceResultIn.getBlockPos();
			IBlockState iblockstate = this.world.getBlockState(blockpos);
			try {
				Field[] fields = super.getClass().getSuperclass().getSuperclass().getDeclaredFields();

				for (Field f : fields) {
					if (f.getName().equals("xTile")) {
						f.setAccessible(true);
						f.set(this, blockpos.getX());
					} else if (f.getName().equals("yTile")) {
						f.setAccessible(true);
						f.set(this, blockpos.getY());
					}
					if (f.getName().equals("zTile")) {
						f.setAccessible(true);
						f.set(this, blockpos.getZ());
					}
					if (f.getName().equals("inTile")) {
						f.setAccessible(true);
						f.set(this, iblockstate.getBlock());
					}
					if (f.getName().equals("inData")) {
						f.setAccessible(true);
						f.set(this, iblockstate.getBlock().getMetaFromState(iblockstate));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.motionX = (double) ((float) (raytraceResultIn.hitVec.x - this.posX));
			this.motionY = (double) ((float) (raytraceResultIn.hitVec.y - this.posY));
			this.motionZ = (double) ((float) (raytraceResultIn.hitVec.z - this.posZ));
			float f2 = MathHelper
					.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
			this.posX -= this.motionX / (double) f2 * 0.05000000074505806D;
			this.posY -= this.motionY / (double) f2 * 0.05000000074505806D;
			this.posZ -= this.motionZ / (double) f2 * 0.05000000074505806D;
			this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			this.inGround = true;
			this.arrowShake = 7;
			this.setIsCritical(false);

			if (iblockstate.getMaterial() != Material.AIR) {
				iblockstate.getBlock().onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		for (int k = 0; k < 4; ++k) {
			this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double) k / 4.0D,
					this.posY + this.motionY * (double) k / 4.0D, this.posZ + this.motionZ * (double) k / 4.0D,
					-this.motionX, -this.motionY + 0.2D, -this.motionZ);
		}
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(toc_arrow);
	}

}
