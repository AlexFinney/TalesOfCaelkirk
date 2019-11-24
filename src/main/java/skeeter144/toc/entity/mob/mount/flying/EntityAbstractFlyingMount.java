package skeeter144.toc.entity.mob.mount.flying;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityAbstractHorseMount;

public class EntityAbstractFlyingMount extends EntityAbstractHorseMount{

	private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.<Boolean>createKey(EntityAbstractHorseMount.class, DataSerializers.BOOLEAN);
	
	public boolean isFlying = false;
	public EntityAbstractFlyingMount(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn) {
		this(type, worldIn, null);
	}
	
	public EntityAbstractFlyingMount(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn, UUID uuid) {
		super(type, worldIn, uuid);
		
		//if(!worldIn.isRemote)
		//	this.dataManager.set(IS_FLYING, isFlying);
	}

	
	@Override
	public void tick() {
		super.tick();
		if(this.onGround) {
			this.dataManager.set(IS_FLYING, false);
			this.setNoGravity(false);
		}
	}
	
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putBoolean("isFlying", isFlying);
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		this.isFlying = compound.getBoolean("isFlying");
		this.dataManager.set(IS_FLYING, isFlying);
	}
	
	public void setIsFlying(boolean flying) {
		this.dataManager.set(IS_FLYING, flying);
	}
	
	public boolean getIsFlying() {
		return this.dataManager.get(IS_FLYING);
	}
	
}
