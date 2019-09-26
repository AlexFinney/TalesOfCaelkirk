package skeeter144.toc.entityeffect.effects;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.registries.ForgeRegistries;

public class ChickenEffect extends EntityEffect{
	
	Set<EntityAITaskEntry> tasksCopy = new HashSet<EntityAITaskEntry>();
	Set<EntityAITaskEntry> targetTasksCopy = new HashSet<EntityAITaskEntry>();
	AxisAlignedBB bb;
	public ChickenEffect(Entity effected, Entity effecter, int duration) {
		super(effected, "turn_chicken", duration);
	}
	
	@Override
	protected void onEffectStart() {
		chicken = new EntityChicken(effected.world);
		chicken.setPosition(effected.posX, effected.posY + 1.5f, effected.posZ);
		effected.world.spawnEntity(chicken);
		effected.setInvulnerable(true);
		
		if(effected instanceof EntityPlayer) {
			((EntityPlayer) effected).addPotionEffect(new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("slowness")), 1000, 1000));
			((EntityPlayer) effected).addPotionEffect(new PotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("invisibility")), 1000, 1000));
		
			copyTaskSetTo(((EntityLiving)chicken).tasks.taskEntries, tasksCopy);
			copyTaskSetTo(((EntityLiving)chicken).targetTasks.taskEntries, targetTasksCopy);
		}else {
			effected.setInvisible(true);
			
			copyTaskSetTo(((EntityLiving)effected).tasks.taskEntries, tasksCopy);
			copyTaskSetTo(((EntityLiving)effected).targetTasks.taskEntries, targetTasksCopy);
			
			((EntityLiving)effected).tasks.taskEntries.clear();
			((EntityLiving)effected).targetTasks.taskEntries.clear();
			
		
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
		
		if(effected instanceof EntityPlayer) {
			((EntityPlayer) effected).removePotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("slowness")));
			((EntityPlayer) effected).removePotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation("invisibility")));
		}else {
			copyTaskSetTo(tasksCopy, ((EntityLiving)effected).tasks.taskEntries);
			copyTaskSetTo(targetTasksCopy, ((EntityLiving)effected).targetTasks.taskEntries);
		}
	}

	Entity chicken;
	protected void onEffectTick() {
			effected.setPositionAndRotation(chicken.posX, chicken.posY + 1, chicken.posZ, chicken.rotationYaw, chicken.rotationPitch);
	}

	private void copyTaskSetTo(Set<EntityAITaskEntry> tasks, Set<EntityAITaskEntry> copyTo) {
		copyTo.clear();
		for(EntityAITaskEntry task : tasks) {
			copyTo.add(task);
		}
	}
	
}
