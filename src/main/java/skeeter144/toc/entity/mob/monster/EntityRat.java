package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.AI.EntityAICrazyRunIdle;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.handlers.PlayerInventoryHandler.ItemAddedToInventoryEvent;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Util;

public class EntityRat extends CustomMob{

	public EntityRat(EntityType<?> type, World worldIn) {
		super(type, worldIn);

		this.attackLevel = 1;
		this.strengthLevel = 1;
		this.hpLevel = 1;
		this.defenseLevel = 1;
		this.magicLevel = 1;
		this.xpGiven = 10;
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 2.0, false));
		this.tasks.addTask(3, new EntityAICrazyRunIdle(this, 2));
		this.tasks.addTask(5, new  EntityAIWander(this, 1.0));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		
		this.setSize(.5f, .5f);
		this.setHealth(3f);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.15);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.2f);
	}
	

	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		if(this.world.isRemote)
			return; 
		EntityPlayer pl = Util.getPlayerFromDamageSource(cause);
		if(pl != null) {
			pl.addItemStackToInventory(new ItemStack(TOCItems.rat_tail));
			//int coins = TOCMain.rand.nextInt(5) + 1;
			//ItemStack is = new ItemStack(TOCItems.copper_coin, coins);
			//MinecraftForge.EVENT_BUS.post(new ItemAddedToInventoryEvent(pl, is));
		}
	}
	
	
	@Override
	protected SoundEvent getAmbientSound() {
		return Sounds.rat_squeak;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return Sounds.rat_die;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource d) {
		return Sounds.rat_hurt;
	}
}
