package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SetMobSpawnerSettingsPKT{

	public static void encode(SetMobSpawnerSettingsPKT pkt, PacketBuffer buf) {}
	public static SetMobSpawnerSettingsPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final SetMobSpawnerSettingsPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
//	public SetMobSpawnerSettingsMessage() {}
//	
//	String mobName;
//	int spawnRadius;
//	int spawnsPerMin;
//	int mobSpawnLimit;
//	int spawnSearchRadius;
//	int minMobsPerSpawn;
//	int maxMobsPersSpawn;
//	int x, y, z;
//	public SetMobSpawnerSettingsMessage(String mobName, int spawnRadius, int spawnsPerMin,
//										int mobSpawnLimit, int spawnSearchRadius,
//										int minMobsPerSpawn,int maxMobsPersSpawn,
//										int x, int y, int z) 
//	{
//		this.mobName = mobName;
//		this.spawnRadius = spawnRadius;
//		this.spawnsPerMin = spawnsPerMin;
//		this.mobSpawnLimit = mobSpawnLimit;
//		this.spawnSearchRadius = spawnSearchRadius;
//		this.minMobsPerSpawn = minMobsPerSpawn;
//		this.maxMobsPersSpawn = maxMobsPersSpawn;
//		this.x = x;
//		this.y = y;
//		this.z = z;
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		ByteBufUtils.writeUTF8String(buf, mobName);
//		buf.writeInt(spawnRadius);
//		buf.writeInt(spawnsPerMin);
//		buf.writeInt(mobSpawnLimit);
//		buf.writeInt(spawnSearchRadius);
//		buf.writeInt(minMobsPerSpawn);
//		buf.writeInt(maxMobsPersSpawn);
//		buf.writeInt(x);
//		buf.writeInt(y);
//		buf.writeInt(z);
//	}
//	
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		mobName= ByteBufUtils.readUTF8String(buf);
//		spawnRadius = buf.readInt();
//		spawnsPerMin = buf.readInt();
//		mobSpawnLimit = buf.readInt();
//		spawnSearchRadius = buf.readInt();
//		minMobsPerSpawn = buf.readInt();
//		maxMobsPersSpawn = buf.readInt();
//		x = buf.readInt();
//		y = buf.readInt();
//		z = buf.readInt();
//	}
//
//	public static class SetMobSpawnerSettingsMessageHandlerHandler<SetMobSpawnerSettingsMessage, IMessage> {
//	
//		public SetMobSpawnerSettingsMessageHandler() {}
//		
//		@Override
//		public IMessage onMessage(SetMobSpawnerSettingsMessage msg, MessageContext ctx) {
//			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
//				public void run() {
//					if(!ctx.getServerHandler().player.capabilities.isCreativeMode)
//						return;
//					
//					EntityPlayerMP player = ctx.getServerHandler().player;
//					if(player.world.isBlockLoaded(new BlockPos(msg.x, msg.y, msg.z))) {
//						TileEntity te = player.world.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
//						if(te != null && te instanceof TileEntityMobSpawner) {
//							TileEntityMobSpawner tems = (TileEntityMobSpawner)te;
//							tems.mob_name = msg.mobName;
//							tems.spawn_radius = msg.spawnRadius;
//							tems.avg_spawns_per_min = msg.spawnsPerMin;
//							tems.mob_spawn_limit = msg.mobSpawnLimit;
//							tems.mob_spawn_search_radius = msg.spawnSearchRadius;
//							tems.mobs_per_spawn_min = msg.minMobsPerSpawn;
//							tems.mobs_per_spawn_max = msg.maxMobsPersSpawn;
//							player.sendMessage(new TextComponentString("Spawner settings saved."));
//							tems.sendUpdates();
//						}
//					}
//				}
//			});
//			return null;
//		}
//	}
}
