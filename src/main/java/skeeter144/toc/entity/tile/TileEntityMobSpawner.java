package skeeter144.toc.entity.tile;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public class TileEntityMobSpawner extends TileEntity implements ITickable{

	int mob_id = 0;
	int spawn_radius = 10;
	int avg_spawns_per_min = 5;
	int mob_spawn_limit = 5;
	int mob_spawn_search_radius = 20;
	int mobs_per_spawn_min = 1;
	int mobs_per_spawn_max = 3;
	
	
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
	}
	
	@Override
	public void update() {
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
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}

}
