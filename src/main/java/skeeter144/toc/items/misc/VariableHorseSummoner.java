package skeeter144.toc.items.misc;

import java.util.UUID;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityAbstractHorseMount;
import skeeter144.toc.items.CustomItem;

public class VariableHorseSummoner extends CustomItem {

	int type_summoned;

	public VariableHorseSummoner(Item.Properties builder, int type_summoned) {
		super(builder, "vhorse_summoner", 1);
		this.type_summoned = type_summoned;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		if (world.isRemote)
			return new ActionResult<ItemStack>(ActionResultType.PASS, player.getHeldItem(hand));

		if (player.getHeldItem(hand).getTag() == null) {
			player.getHeldItem(hand).setTag(new CompoundNBT());
		}

		int type = player.getHeldItem(hand).getTag().getInt("mount_type");
		UUID horseId = player.getHeldItem(hand).getTag().getUniqueId("mount_uuid");

		// no horse exists
		if (horseId == null || horseId.equals(new UUID(0, 0))) {

			EntityAbstractHorseMount horse = null;
			try {
				// TODO
				// horse = new EntityVariableHorseMount(world, player.getUniqueID(),
				// type_summoned);
			} catch (Exception e) {
				e.printStackTrace();
			}

			horse.world = player.world;
			horse.horseOwner = player.getUniqueID();

			horse.setPosition(player.posX, player.posY + 2, player.posZ);
			world.addEntity(horse);
			player.getHeldItem(hand).getTag().putUniqueId("mount_uuid", horse.getUniqueID());
		} else {
			EntityAbstractHorseMount foundHorse = (EntityAbstractHorseMount) ((ServerWorld) world).getEntityByUuid(horseId);
			if (foundHorse != null)
				moveHorseNearPlayerAndWalkTo((EntityAbstractHorseMount) foundHorse, player);
			else {
				EntityAbstractHorseMount horse = null;
				try {
					// TODO
					// horse = new EntityVariableHorseMount(world, player.getUniqueID(),
					// type_summoned);
				} catch (Exception e) {
					e.printStackTrace();
				}

				horse.world = player.world;
				horse.horseOwner = player.getUniqueID();

				player.getHeldItem(hand).getTag().putUniqueId("mount_uuid", horse.getUniqueID());
				moveHorseNearPlayerAndWalkTo(horse, player);
				world.addEntity(horse);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}

	private void moveHorseNearPlayerAndWalkTo(EntityAbstractHorseMount horse, PlayerEntity player) {
		int diameter = 32;
		int attempts = 15;
		if (Math.sqrt(player.getPosition().distanceSq(horse.getPosition())) < 30) {
			if (horse.getNavigator().tryMoveToEntityLiving(player, 1.7)) {
				horse.followTarget = player;
				return;
			}
		}

		for (int i = 0; i < attempts; ++i) {
			int xOff = horse.world.rand.nextInt(diameter) - diameter / 2;
			int zOff = horse.world.rand.nextInt(diameter) - diameter / 2;

			BlockPos pos = new BlockPos(player.posX + xOff, player.posY + 2, player.posZ + zOff);

			if (!horse.world.getBlockState(pos).equals(Blocks.AIR.getDefaultState()))
				continue;

			while (horse.world.getBlockState(pos).equals(Blocks.AIR.getDefaultState()) && pos.getY() > -5)
				pos = pos.down();

			if (horse.world.getBlockState(pos).equals(Blocks.LAVA.getDefaultState())
					|| horse.world.getBlockState(pos).equals(Blocks.WATER.getDefaultState())) {
				continue;
			}

			pos = pos.up();
			horse.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), horse.rotationYaw, horse.rotationPitch);

			if (horse.getNavigator().tryMoveToEntityLiving(player, 1.7)) {
				horse.followTarget = player;
				return;
			}

		}
		horse.setLocationAndAngles(player.posX, player.posY, player.posZ, horse.rotationYaw, horse.rotationPitch);
	}

}
