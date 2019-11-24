package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.registries.ForgeRegistries;

public class ChickenEffect extends EntityEffect{
	
//	Set<EntityAITaskEntry> tasksCopy = new HashSet<EntityAITaskEntry>();
//	Set<EntityAITaskEntry> targetTasksCopy = new HashSet<EntityAITaskEntry>();
	AxisAlignedBB bb;
	public ChickenEffect(Entity effected, Entity effecter, int duration) {
		super(effected, "turn_chicken", duration);
	}
	
	@Override
	protected void onEffectStart() {
		chicken = new ChickenEntity(EntityType.CHICKEN, effected.world);
		chicken.setPosition(effected.posX, effected.posY + 1.5f, effected.posZ);
		effected.world.addEntity(chicken);
		effected.setInvulnerable(true);
		
		if(effected instanceof PlayerEntity) {
			((PlayerEntity) effected).addPotionEffect(new EffectInstance(ForgeRegistries.POTIONS.getValue(new ResourceLocation("slowness")), 1000, 1000));
			((PlayerEntity) effected).addPotionEffect(new EffectInstance(ForgeRegistries.POTIONS.getValue(new ResourceLocation("invisibility")), 1000, 1000));
		
//			copyTaskSetTo(((LivingEntity)chicken).tasks.taskEntries, tasksCopy);
//			copyTaskSetTo(((LivingEntity)chicken).targetTasks.taskEntries, targetTasksCopy);
		}else {
			effected.setInvisible(true);
			
//			copyTaskSetTo(((LivingEntity)effected).tasks.taskEntries, tasksCopy);
//			copyTaskSetTo(((LivingEntity)effected).targetTasks.taskEntries, targetTasksCopy);
			
//			((LivingEntity)effected).tasks.taskEntries.clear();
//			((LivingEntity)effected).targetTasks.taskEntries.clear();
			
		
			effected.noClip = true;
			effected.setNoGravity(true);
		}
		
	}

	@Override
	protected void onEffectEnd(EffectEndType type) {
		effected.setInvisible(false);
		effected.setInvulnerable(false);
		effected.setPositionAndUpdate(chicken.posX, chicken.posY, chicken.posZ);
		effected.noClip = false;
		effected.setNoGravity(false);
		
		if(effected instanceof PlayerEntity) {
			((PlayerEntity) effected).removePotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("slowness")));
			((PlayerEntity) effected).removePotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("invisibility")));
		}else {
//			copyTaskSetTo(tasksCopy, ((LivingEntity)effected).tasks.taskEntries);
//			copyTaskSetTo(targetTasksCopy, ((LivingEntity)effected).targetTasks.taskEntries);
		}
	}

	Entity chicken;
	protected void onEffectTick() {
			effected.setPositionAndRotation(chicken.posX, chicken.posY + 1, chicken.posZ, chicken.rotationYaw, chicken.rotationPitch);
	}

//	private void copyTaskSetTo(Set<EntityAITaskEntry> tasks, Set<EntityAITaskEntry> copyTo) {
//		copyTo.clear();
//		for(EntityAITaskEntry task : tasks) {
//			copyTo.add(task);
//		}
//	}
	
}
