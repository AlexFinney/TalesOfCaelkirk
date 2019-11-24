package skeeter144.toc.entity.mob.mount.basic_horse;

import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityVariableHorseMount extends EntityAbstractHorseMount{
	
	private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.<Integer>createKey(EntityAbstractHorseMount.class, DataSerializers.VARINT);
	
	public int horseVariant = 0;
	public EntityVariableHorseMount(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn) {
		this(type, worldIn, null, 0);
	}
	
	
	public EntityVariableHorseMount(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn, UUID horseOwner, int horseVariant) {
		super(type, worldIn, horseOwner);
		this.horseVariant = horseVariant;
		
		if(!worldIn.isRemote)
			this.dataManager.set(HORSE_VARIANT, horseVariant);
	}

	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("horseVariant", horseVariant);
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		this.horseVariant = compound.getInt("horseVariant");
		this.dataManager.set(HORSE_VARIANT, horseVariant);
	}
	
	public int getHorseVariant() {
		return this.dataManager.get(HORSE_VARIANT);
	}
	
}
