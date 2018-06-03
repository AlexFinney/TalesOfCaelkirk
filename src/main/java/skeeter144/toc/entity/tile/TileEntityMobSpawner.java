package skeeter144.toc.entity.tile;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TileEntityMobSpawner extends TileEntity implements ITickable {

	public int mob_id = 0;
	public int spawn_radius = 10;
	public int avg_spawns_per_min = 5;
	public int mob_spawn_limit = 5;
	public int mob_spawn_search_radius = 20;
	public int mobs_per_spawn_min = 1;
	public int mobs_per_spawn_max = 3;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("mob_id", mob_id);
		compound.setInteger("spawn_radius", spawn_radius);
		compound.setInteger("avg_spawns_per_min", avg_spawns_per_min);
		compound.setInteger("mob_spawn_limit", mob_spawn_limit);
		compound.setInteger("mob_spawn_search_radius", mob_spawn_search_radius);
		compound.setInteger("mobs_per_spawn_min", mobs_per_spawn_min);
		compound.setInteger("mobs_per_spawn_max", mobs_per_spawn_max);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		mob_id = compound.getInteger("mob_id");
		spawn_radius = compound.getInteger("spawn_radius");
		avg_spawns_per_min = compound.getInteger("avg_spawns_per_min");
		mob_spawn_limit = compound.getInteger("mob_spawn_limit");
		mob_spawn_search_radius = compound.getInteger("mob_spawn_search_radius");
		mobs_per_spawn_min = compound.getInteger("mobs_per_spawn_min");
		mobs_per_spawn_max = compound.getInteger("mobs_per_spawn_max");
		super.readFromNBT(compound);
	}

	@Override
	public void update() {
		if(this.world.isRemote)
			return;
		
		if(this.world.rand.nextInt(20) == 0) {//"tick" once per second
			
			float chance = avg_spawns_per_min / 60f;
			if(this.world.rand.nextFloat() < chance) {
				for(EntityEntry entry : ForgeRegistries.ENTITIES) {
					if(entry.hashCode() == mob_id) {
						AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - mob_spawn_search_radius / 2,
															 pos.getY() - 20, 
															 pos.getZ() - mob_spawn_search_radius / 2,
															 
															 pos.getX() + mob_spawn_search_radius / 2,
															 pos.getY() + 20, 
															 pos.getZ() + mob_spawn_search_radius / 2);
						
						if(this.world.getEntitiesWithinAABB(entry.getEntityClass(), bb).size() < mob_spawn_limit) {
							int numMobs = world.rand.nextInt(mobs_per_spawn_max - mobs_per_spawn_min + 1) + mobs_per_spawn_min;
							try {
								Entity e = entry.getEntityClass().getConstructor(World.class).newInstance(world);
								for(int i = 0; i < numMobs; ++i) {
									int spawnXOffset = world.rand.nextInt(spawn_radius) - spawn_radius / 2;
									int spawnZOffset = world.rand.nextInt(spawn_radius) - spawn_radius / 2;
									
									BlockPos spawnPos = new BlockPos(pos.getX() + spawnXOffset, pos.getY() + 10, pos.getZ() + spawnZOffset);
									
									if(!world.isAirBlock(spawnPos)) {
										--i;
										continue;
									}
									
									while(world.isAirBlock(spawnPos) && pos.getY() - spawnPos.getY() < 10) {
										spawnPos = spawnPos.add(0, -1, 0);
									}
									
									spawnPos = spawnPos.add(0, 1, 0);
									if(!world.isAirBlock(spawnPos)) {
										--i;
										continue;
									}else {
										e.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
										world.spawnEntity(e);
									}
								}
									
							  } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
									e.printStackTrace();
							  }
						}
					}
				}
			}
			
		}
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
	}

	private IBlockState getState() {
		return world.getBlockState(pos);
	}

}
