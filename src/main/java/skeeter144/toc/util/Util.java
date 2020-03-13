package skeeter144.toc.util;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.quest.QuestProgress;

public class Util {

	public static PlayerEntity getPlayerFromDamageSource(DamageSource source) {
		if(source.getTrueSource() instanceof PlayerEntity) {
			return (PlayerEntity)source.getTrueSource();
		}
		else if (source instanceof TOCDamageSource && ((TOCDamageSource)source).source instanceof PlayerEntity) {
			return (PlayerEntity)((TOCDamageSource)source).source;
		}
		return null;
	}
	
	public static Entity getPointedEntity(Entity observer, float partialTicks, float reachDistance) {
		RayTraceResult objectMouseOver = null;
		Entity pointedEntity = null;;
		objectMouseOver = rayTrace(observer, reachDistance, partialTicks);

		Vec3d observerPositionEyes = observer.getEyePosition(partialTicks);

		double distance = reachDistance;

		if (objectMouseOver != null) {
			distance = objectMouseOver.getHitVec().distanceTo(observerPositionEyes);
		}

		Vec3d lookVector = observer.getLook(partialTicks);
		Vec3d lookVectorFromEyePosition = observerPositionEyes.add(lookVector.x * reachDistance, lookVector.y * reachDistance,
				lookVector.z * reachDistance);
		//this.pointedEntity = null;
		Vec3d hitVector = null;
		List<Entity> list = observer.world.getEntitiesInAABBexcluding(observer,
				observer.getBoundingBox()
						.expand(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance)
						.expand(1.0D, 1.0D, 1.0D),
						e -> !e.isSpectator());
		double d2 = distance;
//TODO
//		for (int j = 0; j < list.size(); ++j) {
//			Entity entity1 = (Entity) list.get(j);
//			AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double) entity1.getCollisionBorderSize());
//			RayTraceResult raytraceresult = axisalignedbb.
//
//			if (axisalignedbb.contains(observerPositionEyes)) {
//				if (d2 >= 0.0D) {
//					pointedEntity = entity1;
//					hitVector = raytraceresult == null ? observerPositionEyes : raytraceresult.hitVec;
//					d2 = 0.0D;
//				}
//			} else if (raytraceresult != null) {
//				double d3 = observerPositionEyes.distanceTo(raytraceresult.hitVec);
//
//				if (d3 < d2 || d2 == 0.0D) {
//					if (entity1.getLowestRidingEntity() == observer.getLowestRidingEntity() && !observer.canRiderInteract()) {
//						if (d2 == 0.0D) {
//							pointedEntity = entity1;
//							hitVector = raytraceresult.hitVec;
//						}
//					} else {
//						pointedEntity = entity1;
//						hitVector = raytraceresult.hitVec;
//						d2 = d3;
//					}
//				}
//			}
//		}

		//objectMouseOver = new RayTraceResult(pointedEntity, hitVector);

		if (pointedEntity instanceof LivingEntity) {
			return pointedEntity;
		}

		return null;
	}
	
	
	@Nullable
	public static RayTraceResult rayTrace(Entity e, double blockReachDistance, float partialTicks) {
		Vec3d vec3d = e.getEyePosition(partialTicks);
		Vec3d vec3d1 = e.getLook(partialTicks);
		Vec3d vec3d2 = vec3d.add(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
		return e.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, BlockMode.OUTLINE, FluidMode.NONE, e));
	}
	
	
	public static boolean isEntityWithinViewCone(Entity tested, Entity looker, float angleLeft, float angleRight) {
		float lookerRotation = looker.rotationYaw;
		if(lookerRotation < 0)
			lookerRotation += 360;
		
		double dx =  tested.getPosX() - looker.getPosX();
		double dz =  tested.getPosZ() - looker.getPosZ();
		
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
	
	public static BlockPos getRandomBlockPosAround(BlockPos pos, int height, int range) {
		Random r = new Random(System.currentTimeMillis());
		int rx = r.nextInt(range) - (range / 2);
		int ry = r.nextInt(height) - (height / 2);
		int rz = r.nextInt(range) - (range / 2);
		
		return new BlockPos(pos.getX() + rx,
							pos.getY() + ry,
							pos.getZ() + rz);
	}
	
	public static BlockPos getRandomBlockPosAround(BlockPos pos, int height, int range, World w, Block type) {
		Random r = new Random(System.currentTimeMillis());
		int rx = r.nextInt(range) - (range / 2);
		int ry = r.nextInt(height) - (height / 2);
		int rz = r.nextInt(range) - (range / 2);

		BlockPos p = new BlockPos(pos.getX() + rx, pos.getY() + .5D + ry, pos.getZ() + rz);
		
		return w.getBlockState(p) == type.getDefaultState() ? p : pos;
	}
	
	public static boolean inRange(float a, float min, float max)	{
		return a >= min && a <= max;
	}
	
	public static boolean inRange(double a, double min, double max)	{
		return a >= min && a <= max;
	}
	
	public static void LookAt(double px, double py, double pz , LivingEntity e)
	{
	    double dirx = e.getPosition().getX() - px;
	    double diry = e.getPosition().getY() - py;
	    double dirz = e.getPosition().getZ() - pz;

	    double len = Math.sqrt(e.getPosition().distanceSq(px, py, pz, true));

	    dirx /= len;
	    diry /= len;
	    dirz /= len;

	    double pitch = Math.asin(diry);
	    double yaw = Math.atan2(dirz, dirx);

	    //to degree
	    pitch = pitch * 180.0 / Math.PI;
	    yaw = yaw * 180.0 / Math.PI;
	    
	    yaw += 90f;
	    e.rotationPitch = (float)pitch;
	    e.rotationYaw = (float)yaw;
	}
	
	public static Vec3d getMovementVectorTo(BlockPos from, BlockPos to) {
		BlockPos fromTo = from.subtract(to);
		return new Vec3d(fromTo).normalize();
	}

	public static ServerPlayerEntity getPlayerByUUID(UUID uuid) {
		return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(uuid);
	}
	
	public static void sendNewTaskMessage(PlayerEntity player, Quest quest, String text) {
		player.sendMessage(new StringTextComponent(TextFormatting.BLUE  + "[" +  quest.name + "] " + TextFormatting.GOLD + "[New Task] " + TextFormatting.WHITE + text));
	}
	
	public static void sendTaskUpdateMessage(PlayerEntity player, Quest quest, String text) {
		player.sendMessage(new StringTextComponent(TextFormatting.BLUE  + "[" +  quest.name + "] " + TextFormatting.GREEN + "[Task Updated] " + TextFormatting.WHITE + text));
	}
	
	public static void saveQuestProgress(UUID player, Class<? extends QuestProgress> questClass) {
		QuestManager.getQuestProgressForPlayer(player, questClass).save();
	}

	public static void writeItemStack(ItemStack itemStack, PacketBuffer buf) {
		buf.writeInt(itemStack.getCount());
		String name = itemStack.getItem().getRegistryName().toString();
		buf.writeInt(name.length());
		buf.writeCharSequence(name, Charset.defaultCharset());
	}
	
	public static ItemStack readItemStack(PacketBuffer buf) {
		int cnt = buf.readInt();
		int charLength = buf.readInt();
		String itemName = buf.readCharSequence(charLength , Charset.defaultCharset()).toString();
		return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), cnt);
	}
	
}
