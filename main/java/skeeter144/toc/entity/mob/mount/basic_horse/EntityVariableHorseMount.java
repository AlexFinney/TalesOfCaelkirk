package skeeter144.toc.entity.mob.mount.basic_horse;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityVariableHorseMount extends EntityAbstractHorseMount{
	
	private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.<Integer>createKey(EntityAbstractHorseMount.class, DataSerializers.VARINT);
	
	public int horseVariant = 0;
	public EntityVariableHorseMount(World worldIn) {
		this(worldIn, null, 0);
	}
	
	
	public EntityVariableHorseMount(World worldIn, UUID horseOwner, int horseVariant) {
		super(worldIn, horseOwner);
		this.horseVariant = horseVariant;
		
		if(!worldIn.isRemote)
			this.dataManager.set(HORSE_VARIANT, horseVariant);
	}

	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(HORSE_VARIANT, horseVariant);
    }
	
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("horseVariant", horseVariant);
		
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.horseVariant = compound.getInteger("horseVariant");
		this.dataManager.set(HORSE_VARIANT, horseVariant);
	}
	
	public int getHorseVariant() {
		return this.dataManager.get(HORSE_VARIANT);
	}
	
}
