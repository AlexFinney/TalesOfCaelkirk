package skeeter144.toc.entity.mob.mount.basic_horse;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class EntityAbstractHorseMount extends AbstractChestedHorseEntity{

	int secondsSinceLastRide = 0;
	public UUID horseOwner = null;
	public PlayerEntity followTarget = null;
	
	public EntityAbstractHorseMount(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn) {
		super(type, worldIn);
//		this.tasks.taskEntries.clear();
//		this.targetTasks.taskEntries.clear();
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	}
	
	
	public EntityAbstractHorseMount(EntityType<? extends AbstractChestedHorseEntity> type, World worldIn, UUID horseOwner) {
		super(type, worldIn);
//		this.tasks.taskEntries.clear();
//		this.targetTasks.taskEntries.clear();
		this.horseOwner = horseOwner;
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	}
	
	@Override
	public void tick() {
		super.tick();
		if(!this.world.isRemote) {
			if(this.ticksExisted % 20 == 0) {
				if(!this.isBeingRidden()) {
					if(followTarget != null && followTarget.getPosition().distanceSq(this.getPosition()) > 4) {
						this.navigator.tryMoveToEntityLiving(followTarget, this.getAIMoveSpeed());
						secondsSinceLastRide = 0;
					}else {
						followTarget = null;
					}
					++secondsSinceLastRide;
				}else{
					secondsSinceLastRide = 0;
				}

				if(secondsSinceLastRide > 60)
					onDeath(DamageSource.GENERIC);
			}
		}
	}
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		if(horseOwner != null)
			compound.putUniqueId("horseOwner", horseOwner);
		return super.serializeNBT();
	}

	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		horseOwner = compound.getUniqueId("horseOwner");
	}
	
	@Override
	protected boolean canMate() {
		return false;
	}
	
	 //onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData livingdata, CompoundNBT compound) {
		ILivingEntityData data =  super.onInitialSpawn(worldIn, difficulty, reason, livingdata, compound);
		this.setGrowingAge(0);
		return data;
	}
	
	@Override
	public boolean isHorseSaddled() {
		return true;
	}
	
	@Override
	public boolean processInteract(PlayerEntity player, Hand hand) {
		return super.processInteract(player, hand);
	}
	
	@Override
	public boolean isTame() {
		return true;
	}
	
	@Override
	public boolean hasChest() {
		return true;
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, cause)) return;
        if (!this.dead)
        {
            Entity entity = cause.getTrueSource();
            LivingEntity entitylivingbase = this.getAttackingEntity();

            if (this.scoreValue >= 0 && entitylivingbase != null)
            {
                entitylivingbase.awardKillScore(this, this.scoreValue, cause);
            }

            if (entity != null)
            {
                entity.onKillEntity(this);
            }

            this.dead = true;
            this.getCombatTracker().reset();

            if (!this.world.isRemote)
            {
                int i = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, entity, cause);

                if (true)//this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
                {
                    boolean flag = this.recentlyHit > 0;
                    //this.dropLoot(flag, i, cause);
                }
                
                if (!net.minecraftforge.common.ForgeHooks.onLivingDrops(this, cause, captureDrops(), i, recentlyHit > 0))
                {
                    for (ItemEntity item : captureDrops())
                    {
                        world.addEntity(item);
                    }
                }
            }

            this.world.setEntityState(this, (byte)3);
        }
        
        

        if (!this.world.isRemote && this.horseChest != null)
        {
            for (int i = 0; i < this.horseChest.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.horseChest.getStackInSlot(i);

                if (!itemstack.isEmpty())
                {
                    this.entityDropItem(itemstack, 0.0F);
                }
            }
        }
	}
	
	@Override
	public boolean canPassengerSteer() {
		return true;
	}
	
}
