package skeeter144.toc.entity.mob.mount.basic_horse;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityDonkeyMount extends EntityAbstractHorseMount {
	public EntityDonkeyMount(World worldIn) {
		this(worldIn, null);
	}

	public EntityDonkeyMount(World worldIn, UUID uuid) {
		super(worldIn, uuid);
		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.09F);
	}

	protected ResourceLocation getLootTable() {
		return null;
	}

	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.ENTITY_DONKEY_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.ENTITY_DONKEY_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		super.getHurtSound(p_184601_1_);
		return SoundEvents.ENTITY_DONKEY_HURT;
	}

	protected void playChestEquipSound() {
		this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F,
				(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
	}
}
