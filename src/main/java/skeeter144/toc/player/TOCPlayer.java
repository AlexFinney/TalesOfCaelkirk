package skeeter144.toc.player;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import skeeter144.toc.entity.TOCEntity;
import skeeter144.toc.network.HealthManaRegenUpdatePKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.PlayerVitalsUpdatePKT;
import skeeter144.toc.network.PlayerVitalsUpdatePKT;
import skeeter144.toc.network.SpecialAttackCooldownPKT;
import skeeter144.toc.player.EntityLevels.Levels;

public class TOCPlayer extends TOCEntity{
	public static final int BASE_HP = 10;
	public static final int BASE_MANA = 5;
	
	int curHealth, maxHealth, curMana, maxMana;
	//stored as points per second
	float maxHealthRegen, maxManaRegen, healthRegen, manaRegen;
	
	
	public HashMap<String, Pair<Integer, Integer>> specialAttackCooldowns = new HashMap<String, Pair<Integer, Integer>>();
	
	
	public TOCPlayer(PlayerEntity mcPlayer)
	{
		super(mcPlayer, new EntityLevels(mcPlayer.getUniqueID()));
		curHealth = maxHealth = getMaxHealth();
		curMana = maxMana = getMaxMana();
		
		healthRegen = maxHealthRegen = getMaxHealthRegen();
		
		manaRegen = maxManaRegen = getMaxManaRegen();
		
	}
	
	public TOCPlayer(PlayerEntity mcPlayer, EntityLevels levels, int curHealth, int curMana) {
		super(mcPlayer, levels);
		this.maxHealth = getMaxHealth();
		this.maxMana = getMaxMana();
		healthRegen = getMaxHealthRegen();
		manaRegen = getMaxManaRegen();
		this.curHealth = curHealth;
		this.curMana = curMana;
	}
	
	/**
	 * Called client side when a client receives a vitals update message
	 * @param health
	 * @param mana
	 */
	public void setHealthAndMana(int health, int mana) {
		curHealth = health;
		curMana = mana;
		
		maxHealth = getMaxHealth();
		maxMana = getMaxMana();
		
		if(curHealth > getMaxHealth())
			curHealth = getMaxHealth();
		if(curHealth < 0)
			curHealth = 0;
		if(curMana > getMaxMana())
			curMana = getMaxMana();
		if(curMana < 0)
			curMana = 0;
	}
	
	
	/**
	 * Called Server Side. Sends a packet update to the player client
	 */
	public void adjustVitals(int healthDelta, int manaDelta) {
		int newHealth = curHealth + healthDelta;
		int newMana = curMana + manaDelta;
		
		curHealth = newHealth;
		curMana = newMana;
		
		if(curHealth > maxHealth)
			curHealth = maxHealth;
		if(curHealth < 0)
			curHealth = 0;
		if(curMana > maxMana)
			curMana = maxMana;
		if(curMana < 0)
			curMana = 0;
		
		
		skeeter144.toc.network.Network.INSTANCE.sendTo(new PlayerVitalsUpdatePKT(curHealth, curMana), (ServerPlayerEntity) mcEntity);
	}

	//only called on server
	int ticksExisted = 0;
	float healthRegenInProgress = 0;
	float manaRegenInProgress = 0;
	public void tick() {
		++ticksExisted;
		
		//run health and mana regen 5 times per second
		if(ticksExisted % 4 == 0) {
			//only call regen if there's something to regen, naturally
			if(curHealth < maxHealth || curMana < maxMana)
				healthAndManaRegen();
		}
		
		tickInventory(ticksExisted);
	}
	
	private void tickInventory(int ticksExisted) {
		if(ticksExisted % 20 != 0)
			return;
		
		ArrayList<String> stacksToRemove = new ArrayList<String>();
		
		for(String stack : specialAttackCooldowns.keySet()) {
			Integer cooldown = specialAttackCooldowns.get(stack).getLeft() - 1;
			Network.INSTANCE.sendTo(new SpecialAttackCooldownPKT(stack, (byte)cooldown.intValue(), (byte)20),  (ServerPlayerEntity) mcEntity);
			if(cooldown <= 0)
				stacksToRemove.add(stack);
			
			specialAttackCooldowns.put(stack, new MutablePair<Integer, Integer>(cooldown, specialAttackCooldowns.get(stack).getRight()));
		}
		
		for(String stack : stacksToRemove) {
			specialAttackCooldowns.remove(stack);
		}
	}
	
	private void healthAndManaRegen() {
		int healthDelta = 0;
		int manaDelta = 0;
		
		healthRegenInProgress += healthRegen / 5;
		int healthHealed = (int)healthRegenInProgress;
		healthRegenInProgress -= healthHealed;
		healthDelta = healthHealed;
		
		manaRegenInProgress += manaRegen / 5;
		int manaHealed = (int)manaRegenInProgress;
		manaRegenInProgress -= manaHealed;
		manaDelta = manaHealed;
		
		//don't send a packet unless there's actually something to update
		if(healthDelta == 0 && manaDelta == 0)
			return;
		
		adjustVitals(healthDelta, manaDelta);
	}
	
	public void setHealthAndManaRegen(float health, float mana) {
		healthRegen = health;
		manaRegen = mana;
		
		if(!mcEntity.world.isRemote)
			Network.INSTANCE.sendTo(new HealthManaRegenUpdatePKT(healthRegen, manaRegen), (ServerPlayerEntity)mcEntity);
	}
	
	public int getMaxHealth() {
		return BASE_HP + levels.getLevelFor(Levels.HITPOINTS) * EntityLevels.HP_PER_LEVEL;
	}
	
	public int getMaxMana() {
		return BASE_MANA + levels.getLevelFor(Levels.MAGIC) * EntityLevels.MANA_PER_LEVEL;
	}
	
	public float getMaxHealthRegen() {
		return  this.levels.getLevelFor(Levels.HITPOINTS) * EntityLevels.HP_REGEN_PER_HP_LEVEL
				+ this.levels.getLevelFor(Levels.STRENGTH) * EntityLevels.HP_REGEN_PER_STRENGTH_LEVEL;
	}
	
	public float getMaxManaRegen() {
		return this.levels.getLevelFor(Levels.MAGIC) * EntityLevels.MANA_REGEN_PER_MAGIC_LEVEL;
	}
	
	public int getHealth() {
		return curHealth;
	}
	
	public int getMana() {
		return curMana;
	}
	
	public void combatLeveledUp() {
		int maxHealthDelta = getMaxHealth() - maxHealth;
		int maxManaDelta = getMaxMana() - maxMana;
		float healthRegenDelta = getMaxHealthRegen() - maxHealthRegen;
		float manaRegenDelta = getMaxManaRegen() - maxManaRegen;
		this.maxHealth = getMaxHealth();
		this.maxMana = getMaxMana();
		this.maxHealthRegen = getMaxHealthRegen();
		this.maxManaRegen = getMaxManaRegen();
		
		
		adjustVitals(maxHealthDelta, maxManaDelta);
		setHealthAndManaRegen(maxHealthRegen, maxManaRegen);
	}
	
	public EntityLevels getPlayerLevels() {
		return levels;
	}
	
	public float getHealthRegen() {
		return healthRegen;
	}
	
	public float getManaRegen() {
		return manaRegen;
	}
}
