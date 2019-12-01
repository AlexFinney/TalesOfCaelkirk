package skeeter144.toc.entity.tile;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.blocks.TOCBlocks;

public class TileEntityMobSpawner extends TileEntity implements ITickableTileEntity {

	long ticksAlive = 0;

	public TileEntityMobSpawner() {
		super(TOCBlocks.te_mob_spawner);
	}

	public TileEntityMobSpawner(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	EntityType<?> summon_type;
	public String mob_name = "";
	public int spawn_radius = 10;
	public int avg_spawns_per_min = 5;
	public int mob_spawn_limit = 5;
	public int mob_spawn_search_radius = 20;
	public int mobs_per_spawn_min = 1;
	public int mobs_per_spawn_max = 3;

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putString("mob_name", mob_name);
		compound.putInt("spawn_radius", spawn_radius);
		compound.putInt("avg_spawns_per_min", avg_spawns_per_min);
		compound.putInt("mob_spawn_limit", mob_spawn_limit);
		compound.putInt("mob_spawn_search_radius", mob_spawn_search_radius);
		compound.putInt("mobs_per_spawn_min", mobs_per_spawn_min);
		compound.putInt("mobs_per_spawn_max", mobs_per_spawn_max);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		if(!world.isRemote) return;
		
		mob_name = compound.getString("mob_name");
		spawn_radius = compound.getInt("spawn_radius");
		avg_spawns_per_min = compound.getInt("avg_spawns_per_min");
		mob_spawn_limit = compound.getInt("mob_spawn_limit");
		mob_spawn_search_radius = compound.getInt("mob_spawn_search_radius");
		mobs_per_spawn_min = compound.getInt("mobs_per_spawn_min");
		mobs_per_spawn_max = compound.getInt("mobs_per_spawn_max");
		updateSpawnedMob();
	}
	
	public void updateSpawnedMob() {
		summon_type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(mob_name));
		System.out.println();
	}

	@Override
	public void tick() {
		if (this.world.isRemote)
			return;

		if (ticksAlive % 20 == 0) { // tick once per second

			float chance = avg_spawns_per_min / 60f;
			float roll = this.world.rand.nextFloat();
			if (roll < chance) {
				AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - mob_spawn_search_radius / 2, pos.getY() - 5,
						pos.getZ() - mob_spawn_search_radius / 2,

						pos.getX() + mob_spawn_search_radius / 2, pos.getY() + 5,
						pos.getZ() + mob_spawn_search_radius / 2);
				
				// TODO:
				if (true) {// this.world.getEntitiesWithinAABB(entry.getClass(), bb).size() <
							// mob_spawn_limit) {
					int numMobs = world.rand.nextInt(mobs_per_spawn_max - mobs_per_spawn_min + 1) + mobs_per_spawn_min;
					try {

						for (int i = 0; i < numMobs; ++i) {
							int spawnXOffset = world.rand.nextInt(spawn_radius) - spawn_radius / 2;
							int spawnZOffset = world.rand.nextInt(spawn_radius) - spawn_radius / 2;

							BlockPos spawnPos = new BlockPos(pos.getX() + spawnXOffset, pos.getY(),
									pos.getZ() + spawnZOffset);

							if (!world.isAirBlock(spawnPos)) {
								--i;
								continue;
							}

							while (world.isAirBlock(spawnPos) && pos.getY() - spawnPos.getY() < 10) {
								spawnPos = spawnPos.add(0, -1, 0);
							}

							spawnPos = spawnPos.add(0, 1, 0);
							if (!world.isAirBlock(spawnPos) || !world.isAirBlock(spawnPos.add(0, 1, 0))) {
								--i;
								continue;
							} else {
								if(summon_type != null) {
									Entity e = summon_type.create(world);
									e.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
									world.addEntity(e);
								}
							}
						}

					} catch (IllegalArgumentException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}

		++ticksAlive;
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return serializeNBT();
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	public void sendUpdates() {
		// world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		markDirty();
	}

	private BlockState getState() {
		return world.getBlockState(pos);
	}

}
