package skeeter144.toc.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.event.RegistryEvent;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.entity.mob.monster.EntityRat;
import skeeter144.toc.entity.mob.monster.EntityViking;

public class TOCBiomes {

	public static Biome GOBLIN_VILLAGE = new GoblinVillageBiome(new BiomeProperties("goblin_village").setBaseBiome("default"));
	public static Biome VIKING_VILLAGE = new VikingVillageBiome(new BiomeProperties("viking_village").setBaseBiome("default"));
	
	
	public void registerBiomes(RegistryEvent.Register<Biome> e) {
		e.getRegistry().registerAll(
				GOBLIN_VILLAGE,
				VIKING_VILLAGE);
		
	}
	
	
	
	
	public static class GoblinVillageBiome extends Biome{
		public GoblinVillageBiome(BiomeProperties properties) {
			super(properties);
			
			this.spawnableMonsterList.add(new SpawnListEntry(EntityGoblin.class, 30, 4, 6));
			this.spawnableMonsterList.add(new SpawnListEntry(EntityRat.class, 4, 1, 1));
		}
	}
	
	public static class VikingVillageBiome extends Biome{
		public VikingVillageBiome(BiomeProperties properties) {
			super(properties);
			
			this.spawnableMonsterList.add(new SpawnListEntry(EntityViking.class, 30, 2, 4));
			this.spawnableMonsterList.add(new SpawnListEntry(EntityRat.class, 4, 1, 1));
		}
	}
}

