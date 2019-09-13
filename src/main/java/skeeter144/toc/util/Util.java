package skeeter144.toc.util;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import skeeter144.toc.combat.TOCDamageSource;

public class Util {

	public static EntityPlayer getPlayerFromDamageSource(DamageSource source) {
		if(source.getTrueSource() instanceof EntityPlayer) {
			return (EntityPlayer)source.getTrueSource();
		}
		else if (source instanceof TOCDamageSource && ((TOCDamageSource)source).source instanceof EntityPlayer) {
			return (EntityPlayer)((TOCDamageSource)source).source;
		}
		return null;
	}
	
	public static Entity getPointedEntity(Entity observer, float partialTicks, float reachDistance) {
		RayTraceResult objectMouseOver = null;
		Entity pointedEntity = null;;
		objectMouseOver = rayTrace(observer, reachDistance, partialTicks);

		Vec3d observerPositionEyes = observer.getPositionEyes(partialTicks);

		double distance = reachDistance;

		if (objectMouseOver != null) {
			distance = objectMouseOver.hitVec.distanceTo(observerPositionEyes);
		}

		Vec3d lookVector = observer.getLook(partialTicks);
		Vec3d lookVectorFromEyePosition = observerPositionEyes.addVector(lookVector.x * reachDistance, lookVector.y * reachDistance,
				lookVector.z * reachDistance);
		//this.pointedEntity = null;
		Vec3d hitVector = null;
		List<Entity> list = observer.world.getEntitiesInAABBexcluding(observer,
				observer.getEntityBoundingBox()
						.expand(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance)
						.expand(1.0D, 1.0D, 1.0D),
				EntitySelectors.NOT_SPECTATING);
		double d2 = distance;

		for (int j = 0; j < list.size(); ++j) {
			Entity entity1 = (Entity) list.get(j);
			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double) entity1.getCollisionBorderSize());
			RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(observerPositionEyes, lookVectorFromEyePosition);

			if (axisalignedbb.contains(observerPositionEyes)) {
				if (d2 >= 0.0D) {
					pointedEntity = entity1;
					hitVector = raytraceresult == null ? observerPositionEyes : raytraceresult.hitVec;
					d2 = 0.0D;
				}
			} else if (raytraceresult != null) {
				double d3 = observerPositionEyes.distanceTo(raytraceresult.hitVec);

				if (d3 < d2 || d2 == 0.0D) {
					if (entity1.getLowestRidingEntity() == observer.getLowestRidingEntity() && !observer.canRiderInteract()) {
						if (d2 == 0.0D) {
							pointedEntity = entity1;
							hitVector = raytraceresult.hitVec;
						}
					} else {
						pointedEntity = entity1;
						hitVector = raytraceresult.hitVec;
						d2 = d3;
					}
				}
			}
		}

		objectMouseOver = new RayTraceResult(pointedEntity, hitVector);

		if (pointedEntity instanceof EntityLivingBase) {
			return pointedEntity;
		}

		return null;
	}
	
	
	@Nullable
	public static RayTraceResult rayTrace(Entity e, double blockReachDistance, float partialTicks) {
		Vec3d vec3d = e.getPositionEyes(partialTicks);
		Vec3d vec3d1 = e.getLook(partialTicks);
		Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
		return e.world.rayTraceBlocks(vec3d, vec3d2, false, true, true);
	}
	
	
	public static boolean isEntityWithinViewCone(Entity tested, Entity looker, float angleLeft, float angleRight) {
		float lookerRotation = looker.rotationYaw;
		if(lookerRotation < 0)
			lookerRotation += 360;
		
		double dx =  tested.posX - looker.posX;
		double dz =  tested.posZ - looker.posZ;
		
		double distance = Math.sqrt(dx * dx + dz * dz);
		double angle = Math.toDegrees(Math.acos(dz / distance));
		
		if(dx > 0) {
			angle = 360 - angle;
		}
		
		double lower = lookerRotation + angleLeft;
		double upper = lookerRotation + angleRight;
		
		if(lower < 0)
			lower += 360;
		
		if(upper > 360)
			upper -= 360;
		
		boolean hit = false;
		if(lower > upper) { //on the 360-0 crossover
			if(angle > lower || angle < upper)
				hit = true;
		}else {
			if(angle > lower && angle < upper)
				hit = true;
		}
		return hit;
	}
	
	public static float toRad(float degrees) {
		return (float)Math.toRadians(degrees);
	}
	
	public static boolean inRange(int a, int min, int max)	{
		return a >= min && a <= max;
	}
	
	public static boolean inRange(float a, float min, float max)	{
		return a >= min && a <= max;
	}
	
	public static boolean inRange(double a, double min, double max)	{
		return a >= min && a <= max;
	}
	
}
