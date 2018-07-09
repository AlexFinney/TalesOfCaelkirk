package skeeter144.toc.entity.mob.mount.flying;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityAbstractHorseMount;

public class EntityAbstractFlyingMount extends EntityAbstractHorseMount{

	private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.<Boolean>createKey(EntityAbstractHorseMount.class, DataSerializers.BOOLEAN);
	
	public boolean isFlying = false;
	public EntityAbstractFlyingMount(World worldIn) {
		this(worldIn, null);
	}
	
	public EntityAbstractFlyingMount(World worldIn, UUID uuid) {
		super(worldIn, uuid);
		
		if(!worldIn.isRemote)
			this.dataManager.set(IS_FLYING, isFlying);
	}

	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.onGround) {
			this.dataManager.set(IS_FLYING, false);
			this.setNoGravity(false);
		}
	}
	
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("isFlying", isFlying);
		
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.isFlying = compound.getBoolean("isFlying");
		this.dataManager.set(IS_FLYING, isFlying);
	}
	
	public void setIsFlying(boolean flying) {
		this.dataManager.set(IS_FLYING, flying);
	}
	
	public boolean getIsFlying() {
		return this.dataManager.get(IS_FLYING);
	}
	
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_FLYING, isFlying);
    }
	
	@Override
	public boolean isEntityInvulnerable(DamageSource source) {
		if(source.equals(DamageSource.FALL))
			return true;
		return super.isEntityInvulnerable(source);
	}
}
