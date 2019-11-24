package skeeter144.toc.entity.mob.mount.basic_horse;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class EntityMuleMount extends EntityAbstractHorseMount {
	public EntityMuleMount(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn) {
		this(type, worldIn, null);
	}

	public EntityMuleMount(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn, UUID uuid) {
		super(type, worldIn, uuid);
//		this.tasks.taskEntries.clear();
//		this.targetTasks.taskEntries.clear();
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.11F);
	}

	protected ResourceLocation getLootTable() {
		return null;
	}

	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.ENTITY_MULE_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.ENTITY_MULE_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		super.getHurtSound(p_184601_1_);
		return SoundEvents.ENTITY_MULE_HURT;
	}

	protected void playChestEquipSound() {
		this.playSound(SoundEvents.ENTITY_MULE_CHEST, 1.0F,
				(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
	}
}
