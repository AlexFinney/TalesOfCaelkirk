package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class TornadoEffect extends EntityEffect {
	
	public static final int SECONDS_PER_LEVEL = 3;
	static final int INITIAL_DURATION_SECONDS = 5;
	
	public static final int INITIAL_DAMAGE_PER_INSTANCE= 2;
	public static final int DAMAGE_PER_LEVEL = 1;
	
	public static final float MAX_INITIAL_HEIGHT = 13;
	public static final float HEIGHT_PER_LEVEL = 2;
	
	int damage;
	float maxHeight;
	
	public TornadoEffect(Entity effected, int tornadoLevel) {
		super(effected, "Tornado", getDurationForLevel(tornadoLevel));
		effectName = "Tornado: Level " + tornadoLevel;
		iconName = "";
		purgeable = true;
		damage = getDamagePerInstanceForLevel(tornadoLevel);
		maxHeight = getHeightForLevel(tornadoLevel);
	}

	double initialX, initialY, initialZ;
	
	@Override
	protected void onEffectStart() {
		//spawn tornado particle effect
		effected.setNoGravity(true);
		initialX = effected.posX;
		initialY = effected.posY;
		initialZ = effected.posZ;
		effected.addVelocity(0, .65, 0);
		if(effected instanceof EntityPlayerMP) {
			((EntityPlayerMP)effected).capabilities.allowFlying = true;
		}
	}

	@Override
	protected void onEffectTick() {
		if(effected.posY > initialY + maxHeight)
		{
			effected.setPosition(effected.posX, initialY + maxHeight, effected.posZ);
		}
		
		effected.setPosition(initialX, effected.posY, initialZ);
		
		if(ticksRemaining % 20 == 0)
		{
			((EntityLivingBase)effected).attackEntityFrom(DamageSource.GENERIC, 1);
		}
	}
	
	@Override
	protected void onEffectEnd(EffectEndType type) {
		if(effected instanceof EntityPlayerMP) {
			((EntityPlayerMP)effected).capabilities.allowFlying = false;
		}
		effected.setNoGravity(false);
		
	}
	
	public static float getHeightForLevel(int level)
	{
		return MAX_INITIAL_HEIGHT + HEIGHT_PER_LEVEL * (level - 1);
	}
	
	public static int getDamagePerInstanceForLevel(int level)
	{
		return INITIAL_DAMAGE_PER_INSTANCE + DAMAGE_PER_LEVEL * (level- 1);
	}
	
	public static int getDurationForLevel(int level)
	{
		return INITIAL_DURATION_SECONDS * 20 + SECONDS_PER_LEVEL * 20 * (level - 1);
	}

}
