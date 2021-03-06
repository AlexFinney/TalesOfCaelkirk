package skeeter144.toc.entity.mob;

import net.minecraft.entity.EntityFlying;
import net.minecraft.world.World;
import skeeter144.toc.client.entity.animation.Animation;
import skeeter144.toc.client.entity.animation.KeyFrame;

public class CustomFlyingMob extends EntityFlying{

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
	
	public CustomFlyingMob(World worldIn) {
		super(worldIn);
	}
	
	public KeyFrame lastFrame = null;
	public KeyFrame currentFrame = null;
	public void playAnimation(Animation anim) {
		this.currentAnim = anim;
		animationStartTime = this.ticksExisted;
	}

}
