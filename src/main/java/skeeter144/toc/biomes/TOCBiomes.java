package skeeter144.toc.biomes;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import skeeter144.toc.entity.TOCEntityType;

public class TOCBiomes {

	public static Biome GOBLIN_VILLAGE = new GoblinVillageBiome(new Biome.BiomeBuilder());
	
	
	public void registerBiomes(RegistryEvent.Register<Biome> e) {
		e.getRegistry().registerAll(
				GOBLIN_VILLAGE);
		
	}
	
	public static class GoblinVillageBiome extends Biome{
		public GoblinVillageBiome(Biome.BiomeBuilder builder) {
			super(builder);
			this.addSpawn(EnumCreatureType.MONSTER, new SpawnListEntry(TOCEntityType.GOBLIN, 30, 4, 6));
		}
	}
	
	
}

