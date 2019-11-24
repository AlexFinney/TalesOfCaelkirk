package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class SetMobSpawnerSettingsPKT{
	public SetMobSpawnerSettingsPKT() {}

	String mobName;
	int spawnRadius;
	int spawnsPerMin;
	int mobSpawnLimit;
	int spawnSearchRadius;
	int minMobsPerSpawn;
	int maxMobsPersSpawn;
	int x, y, z;
	public SetMobSpawnerSettingsPKT(String mobName, int spawnRadius, int spawnsPerMin,
										int mobSpawnLimit, int spawnSearchRadius,
										int minMobsPerSpawn,int maxMobsPersSpawn,
										int x, int y, int z) 
	{
		this.mobName = mobName;
		this.spawnRadius = spawnRadius;
		this.spawnsPerMin = spawnsPerMin;
		this.mobSpawnLimit = mobSpawnLimit;
		this.spawnSearchRadius = spawnSearchRadius;
		this.minMobsPerSpawn = minMobsPerSpawn;
		this.maxMobsPersSpawn = maxMobsPersSpawn;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void encode(SetMobSpawnerSettingsPKT pkt, PacketBuffer buf) {
		buf.writeString(pkt.mobName);
		buf.writeInt(pkt.spawnRadius);
		buf.writeInt(pkt.spawnsPerMin);
		buf.writeInt(pkt.mobSpawnLimit);
		buf.writeInt(pkt.spawnSearchRadius);
		buf.writeInt(pkt.minMobsPerSpawn);
		buf.writeInt(pkt.maxMobsPersSpawn);
		buf.writeInt(pkt.x);
		buf.writeInt(pkt.y);
		buf.writeInt(pkt.z);
	}
	public static SetMobSpawnerSettingsPKT decode(PacketBuffer buf) {
		SetMobSpawnerSettingsPKT pkt = new SetMobSpawnerSettingsPKT();
		pkt.mobName= buf.readString(1024);
		pkt.spawnRadius = buf.readInt();
		pkt.spawnsPerMin = buf.readInt();
		pkt.mobSpawnLimit = buf.readInt();
		pkt.spawnSearchRadius = buf.readInt();
		pkt.minMobsPerSpawn = buf.readInt();
		pkt.maxMobsPersSpawn = buf.readInt();
		pkt.x = buf.readInt();
		pkt.y = buf.readInt();
		pkt.z = buf.readInt();
		
		return pkt;
	}
	
	public static class Handler
	{
		public static void handle(final SetMobSpawnerSettingsPKT msg, Supplier<NetworkEvent.Context> ctx){
			ctx.get().enqueueWork(new Runnable() {
				public void run() {
					if(!ctx.get().getSender().isCreative())
						return;
					
					ServerPlayerEntity player = ctx.get().getSender();
					if(player.world.isBlockLoaded(new BlockPos(msg.x, msg.y, msg.z))) {
						TileEntity te = player.world.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
						if(te != null && te instanceof TileEntityMobSpawner) {
							TileEntityMobSpawner tems = (TileEntityMobSpawner)te;
							tems.mob_name = msg.mobName;
							tems.spawn_radius = msg.spawnRadius;
							tems.avg_spawns_per_min = msg.spawnsPerMin;
							tems.mob_spawn_limit = msg.mobSpawnLimit;
							tems.mob_spawn_search_radius = msg.spawnSearchRadius;
							tems.mobs_per_spawn_min = msg.minMobsPerSpawn;
							tems.mobs_per_spawn_max = msg.maxMobsPersSpawn;
							player.sendMessage(new StringTextComponent("Spawner settings saved."));
							tems.sendUpdates();
						}
					}
				}
			});
		}
	}
}
