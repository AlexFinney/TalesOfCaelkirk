package skeeter144.toc.entity.mob.mount.basic_horse;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public abstract class EntityAbstractHorseMount extends AbstractChestHorse{

	int secondsSinceLastRide = 0;
	public UUID horseOwner = null;
	public EntityPlayer followTarget = null;
	
	public EntityAbstractHorseMount(EntityType<?> type, World worldIn) {
		super(type, worldIn);
		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	}
	
	
	public EntityAbstractHorseMount(EntityType<?> type, World worldIn, UUID horseOwner) {
		super(type, worldIn);
		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();
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
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		if(horseOwner != null)
			compound.setUniqueId("horseOwner", horseOwner);
		return super.serializeNBT();
	}

	
	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		horseOwner = compound.getUniqueId("horseOwner");
	}
	
	@Override
	protected boolean canMate() {
		return false;
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata, NBTTagCompound compound) {
		IEntityLivingData data =  super.onInitialSpawn(difficulty, livingdata, compound);
		this.setGrowingAge(0);
		return data;
	}
	
	@Override
	public boolean isHorseSaddled() {
		return true;
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
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
            EntityLivingBase entitylivingbase = this.getAttackingEntity();

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

                if (this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
                {
                    boolean flag = this.recentlyHit > 0;
                    this.dropLoot(flag, i, cause);
                }
                if (!net.minecraftforge.common.ForgeHooks.onLivingDrops(this, cause, captureDrops(), i, recentlyHit > 0))
                {
                    for (EntityItem item : captureDrops())
                    {
                        world.spawnEntity(item);
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
