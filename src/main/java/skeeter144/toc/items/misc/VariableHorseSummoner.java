package skeeter144.toc.items.misc;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityAbstractHorseMount;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityVariableHorseMount;
import skeeter144.toc.items.CustomItem;

public class VariableHorseSummoner extends CustomItem {

	int type_summoned;
	public VariableHorseSummoner(String name, int type_summoned) {
		super(name, 1, CreativeTabs.MISC);
		this.type_summoned = type_summoned;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(world.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
		
		if(player.getHeldItem(hand).getTagCompound() == null) {
			player.getHeldItem(hand).setTagCompound(new NBTTagCompound());
		}
		
		int type = player.getHeldItem(hand).getTagCompound().getInteger("mount_type");
		UUID horseId = player.getHeldItem(hand).getTagCompound().getUniqueId("mount_uuid");
		
		
		
		//no horse exists
		if(horseId == null || horseId.equals(new UUID(0,0))) {
			
			EntityAbstractHorseMount horse = null;
			try {
				horse = new EntityVariableHorseMount(world, player.getUniqueID(), type_summoned);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			horse.world = player.world;
			horse.horseOwner = player.getUniqueID();
			
			horse.setPosition(player.posX, player.posY + 2, player.posZ);
			world.spawnEntity(horse);
			player.getHeldItem(hand).getTagCompound().setUniqueId("mount_uuid", horse.getUniqueID());
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
					horse = new EntityVariableHorseMount(world, player.getUniqueID(), type_summoned);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				horse.world = player.world;
				horse.horseOwner = player.getUniqueID();
				
				player.getHeldItem(hand).getTagCompound().setUniqueId("mount_uuid", horse.getUniqueID());
				moveHorseNearPlayerAndWalkTo(horse, player);
				world.spawnEntity(horse);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	
	private void moveHorseNearPlayerAndWalkTo(EntityAbstractHorseMount horse, EntityPlayer player) {
		int diameter = 32;
		int attempts = 15;
		System.out.println(Math.sqrt(player.getPosition().distanceSq(horse.getPosition())));
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
	
}
