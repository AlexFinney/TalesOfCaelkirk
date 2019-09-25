package skeeter144.toc.entity.tile;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.blocks.TOCBlocks;

public class TileEntityMobSpawner extends TileEntity implements ITickable {

	public TileEntityMobSpawner() {
		super(TOCBlocks.te_mob_spawner);
	}
	
	public TileEntityMobSpawner(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public String mob_name = "";
	public int spawn_radius = 10;
	public int avg_spawns_per_min = 5;
	public int mob_spawn_limit = 5;
	public int mob_spawn_search_radius = 20;
	public int mobs_per_spawn_min = 1;
	public int mobs_per_spawn_max = 3;

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString("mob_name", mob_name);
		compound.setInt("spawn_radius", spawn_radius);
		compound.setInt("avg_spawns_per_min", avg_spawns_per_min);
		compound.setInt("mob_spawn_limit", mob_spawn_limit);
		compound.setInt("mob_spawn_search_radius", mob_spawn_search_radius);
		compound.setInt("mobs_per_spawn_min", mobs_per_spawn_min);
		compound.setInt("mobs_per_spawn_max", mobs_per_spawn_max);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		mob_name = compound.getString("mob_name");
		spawn_radius = compound.getInt("spawn_radius");
		avg_spawns_per_min = compound.getInt("avg_spawns_per_min");
		mob_spawn_limit = compound.getInt("mob_spawn_limit");
		mob_spawn_search_radius = compound.getInt("mob_spawn_search_radius");
		mobs_per_spawn_min = compound.getInt("mobs_per_spawn_min");
		mobs_per_spawn_max = compound.getInt("mobs_per_spawn_max");
	}

	@Override
	public void tick() {
		if(this.world.isRemote)
			return;
		
		if(this.world.rand.nextInt(20) == 0) {//"tick" once per second
			
			float chance = avg_spawns_per_min / 60f;
			if(this.world.rand.nextFloat() < chance) {
				for(EntityType<?> entry : ForgeRegistries.ENTITIES) {
					if(entry.getEntityClass().getName().equals(mob_name)) {
						AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - mob_spawn_search_radius / 2,
															 pos.getY() - 5, 
															 pos.getZ() - mob_spawn_search_radius / 2,
															 
															 pos.getX() + mob_spawn_search_radius / 2,
															 pos.getY() + 5, 
															 pos.getZ() + mob_spawn_search_radius / 2);
						
						if(this.world.getEntitiesWithinAABB(entry.getEntityClass(), bb).size() < mob_spawn_limit) {
							int numMobs = world.rand.nextInt(mobs_per_spawn_max - mobs_per_spawn_min + 1) + mobs_per_spawn_min;
							try {
								
								for(int i = 0; i < numMobs; ++i) {
									int spawnXOffset = world.rand.nextInt(spawn_radius) - spawn_radius / 2;
									int spawnZOffset = world.rand.nextInt(spawn_radius) - spawn_radius / 2;
									
									BlockPos spawnPos = new BlockPos(pos.getX() + spawnXOffset, pos.getY(), pos.getZ() + spawnZOffset);
									
									if(!world.isAirBlock(spawnPos)) {
										--i;
										continue;
									}
									
									while(world.isAirBlock(spawnPos) && pos.getY() - spawnPos.getY() < 10) {
										spawnPos = spawnPos.add(0, -1, 0);
									}
									
									spawnPos = spawnPos.add(0, 1, 0);
									if(!world.isAirBlock(spawnPos) || !world.isAirBlock(spawnPos.add(0, 1, 0))) {
										--i;
										continue;
									}else {
										Entity e = entry.getEntityClass().getConstructor(World.class).newInstance(world);
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
		return serializeNBT();
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		markDirty();
	}

	private IBlockState getState() {
		return world.getBlockState(pos);
	}

}
