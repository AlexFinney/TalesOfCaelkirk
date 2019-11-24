package skeeter144.toc.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import skeeter144.toc.client.entity.animation.Animation;
import skeeter144.toc.client.entity.animation.KeyFrame;

public class CustomMob extends MobEntity{

	public int hpLevel;
	public int attackLevel;
	public int strengthLevel;
	public int defenseLevel;
	public int magicLevel;
	public int xpGiven;
	public int blockedXp;
	public Animation currentAnim;
	public float animationTicks;
	public float animationStartTime;
	
	public CustomMob(EntityType<? extends MobEntity> type, World worldIn) {
		super(type, worldIn);
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	}
	
	public KeyFrame lastFrame = null;
	public KeyFrame currentFrame = null;
	public void playAnimation(Animation anim) {
		this.currentAnim = anim;
		animationStartTime = this.ticksExisted;
	}

}
