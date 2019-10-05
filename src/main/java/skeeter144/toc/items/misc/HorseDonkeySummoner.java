package skeeter144.toc.items.misc;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityAbstractHorseMount;
import skeeter144.toc.items.CustomItem;

public class HorseDonkeySummoner extends CustomItem {

	Class<? extends EntityAbstractHorseMount> type_summoned;
	public HorseDonkeySummoner(Item.Properties builder, Class<? extends EntityAbstractHorseMount> type_summoned) {
		super(builder, 1);
		this.type_summoned = type_summoned;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(world.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
		
		if(player.getHeldItem(hand).getTag() == null) {
			player.getHeldItem(hand).setTag(new NBTTagCompound());
		}
		
		int type = player.getHeldItem(hand).getTag().getInt("mount_type");
		UUID horseId = player.getHeldItem(hand).getTag().getUniqueId("mount_uuid");
		
		
		
		//no horse exists
		if(horseId == null || horseId.equals(new UUID(0,0))) {
			
			EntityAbstractHorseMount horse = null;
			try {
				horse = type_summoned.getDeclaredConstructor(World.class, UUID.class).newInstance(player.world, player.getUniqueID());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			horse.world = player.world;
			horse.horseOwner = player.getUniqueID();
			
			horse.setPosition(player.posX, player.posY + 2, player.posZ);
			world.spawnEntity(horse);
			player.getHeldItem(hand).getTag().setUniqueId("mount_uuid", horse.getUniqueID());
		}else {
			Entity foundHorse = null;
			for(Entity e : world.loadedEntityList) {
				if(e instanceof EntityAbstractHorseMount) {
					if(e.getUniqueID().equals(horseId)) {
						moveHorseNearPlayerAndWalkTo((EntityAbstractHorseMount) e, player);
						foundHorse = e;
						break;
					}
				}
			}
			
			if(foundHorse == null) {
				EntityAbstractHorseMount horse = null;
				try {
					horse = type_summoned.getDeclaredConstructor(World.class, UUID.class).newInstance(player.world, player.getUniqueID());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				horse.world = player.world;
				horse.horseOwner = player.getUniqueID();
				
				player.getHeldItem(hand).getTag().setUniqueId("mount_uuid", horse.getUniqueID());
				moveHorseNearPlayerAndWalkTo(horse, player);
				world.spawnEntity(horse);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	
	private void moveHorseNearPlayerAndWalkTo(EntityAbstractHorseMount horse, EntityPlayer player) {
		int diameter = 32;
		int attempts = 15;
		if(Math.sqrt(player.getPosition().distanceSq(horse.getPosition())) < 30) {
			if(horse.getNavigator().tryMoveToEntityLiving(player, 1.7)) {
				horse.followTarget = player;
				return;
			}
		}
		
		for(int i = 0; i < attempts; ++i) {
			int xOff = horse.world.rand.nextInt(diameter) - diameter / 2;
			int zOff = horse.world.rand.nextInt(diameter) - diameter / 2;
			
			BlockPos pos = new BlockPos(player.posX + xOff, player.posY + 2, player.posZ +zOff);
			
			if(!horse.world.getBlockState(pos).equals(Blocks.AIR.getDefaultState())) 
				continue;
			
			
			while(horse.world.getBlockState(pos).equals(Blocks.AIR.getDefaultState()) && pos.getY() > -5) 
				pos = pos.down();
			
			
			if(horse.world.getBlockState(pos).equals(Blocks.LAVA.getDefaultState()) || 
			   horse.world.getBlockState(pos).equals(Blocks.WATER.getDefaultState())) {
				continue;
			}
			
			pos = pos.up();
			horse.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), horse.rotationYaw, horse.rotationPitch);
			
			if(horse.getNavigator().tryMoveToEntityLiving(player, 1.7)) {
				horse.followTarget = player;
				return;
			}
			
		}
		horse.setLocationAndAngles(player.posX, player.posY, player.posZ, horse.rotationYaw, horse.rotationPitch);
	}
	
		
	public static enum HORSE_TYPES{
		MULE,
		DONKEY,
		LAME_BLACK,
		LAME_DAPPLE_GRAY,
		CHESTNUT,
		BUCKSKIN,
		WHITE_STALLION,
		ZOMBIE,
		SKELETON
	}
	
}
