package skeeter144.toc.entity.mob.monster;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.sounds.Sounds;

public class EntityGoblin extends CustomMob{

	public EntityGoblin(World worldIn) {
		this(TOCEntityType.GOBLIN, worldIn);
	}
	
	public EntityGoblin(EntityType<? extends CustomMob> type, World worldIn) {
		super(type, worldIn);
		
		this.attackLevel = 3;
		this.strengthLevel = 5;
		this.hpLevel = 4;
		this.defenseLevel = 1;
		this.magicLevel = 1;
		this.xpGiven = 12;
		
		this.setHealth(10f);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		
		//this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(EntityGoblin.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 0, true, true, (e) ->  {return e.getPosition().distanceSq(this.getPosition()) < 100f;}));
	}
	
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.25);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(.4f);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		// 10% chance of a laugh instead of breathe
		return rand.nextInt(10) == 0 ? Sounds.goblin_laugh : Sounds.goblin_breathe;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return Sounds.goblin_die;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource d) {
		return Sounds.goblin_snarl;
	}
	
	@Override
	protected void dropLoot(DamageSource src, boolean wasRecentlyHit) {
		super.dropLoot(src, wasRecentlyHit);
		this.entityDropItem(new ItemStack(TOCItems.goblin_ear));
	}
	
	@Override
	public boolean isAIDisabled() {
		return false;
	}
	
	@Override
	public CreatureAttribute getCreatureAttribute() {
		// TODO Auto-generated method stub
		return CreatureAttribute.UNDEAD;
	}
	
}
