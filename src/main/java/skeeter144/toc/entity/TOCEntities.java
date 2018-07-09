package skeeter144.toc.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.mob.monster.EntityGhost;
import skeeter144.toc.entity.mob.monster.EntityGiantScorpian;
import skeeter144.toc.entity.mob.monster.EntityGiantSpider;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.entity.mob.monster.EntityRat;
import skeeter144.toc.entity.mob.monster.EntityViking;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityDonkeyMount;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityMuleMount;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityVariableHorseMount;
import skeeter144.toc.entity.mob.mount.flying.EntityGriffin;
import skeeter144.toc.entity.mob.mount.flying.EntityPegasus;
import skeeter144.toc.entity.mob.passive.banker.EntityBanker;
import skeeter144.toc.entity.mob.passive.questgiver.EntityEvaTeffan;
import skeeter144.toc.entity.mob.passive.questgiver.EntityKelvinWhitestone;
import skeeter144.toc.entity.mob.passive.questgiver.EntityRobertCromwell;
import skeeter144.toc.entity.mob.passive.questgiver.EntityUlricWeston;
import skeeter144.toc.entity.mob.passive.shopkeeper.EntityHumanShopKeeper;
import skeeter144.toc.util.Reference;

public class TOCEntities {

	public static void registerEntities() {
		//monsters
		registerEntity(EntityViking.class, "viking", 80, 3, true, 0xC4C4C4, 0x684242);
		registerEntity(EntityRat.class, "rat", 80, 3, true, 0x9999990, 0x606060);
		registerEntity(EntityGoblin.class, "goblin", 80, 3, true, 0x007F0E, 0x877436);
		registerEntity(EntityGiantScorpian.class, "giant_scorpian", 80, 3, true, 0x00AA0E, 0x87BD36);
		registerEntity(EntityGiantSpider.class, "giant_spider", 80, 3, true, 0xFF0000, 0x000000);
		registerEntity(EntityGhost.class, "ghost", 80, 3, true, 0xFFFFFF, 0x000000);
		
		
		//mounts
			//horse-like
			registerEntity(EntityMuleMount.class, "mule_mount", 80, 3, true, 0x7d3800, 0x361800);
			registerEntity(EntityDonkeyMount.class, "donkey_mount", 80, 3, true, 0x7d3800, 0x361800);
			registerEntity(EntityVariableHorseMount.class, "horse_mount", 80, 3, true, 0x7d3800, 0x361800);
			
			//flying
			registerEntity(EntityPegasus.class, "pegasus", 80, 3, true, 0xffffff, 0xdddddd);
			registerEntity(EntityGriffin.class, "griffin", 80, 3, true, 0xfa9d55, 0x732e00);
		
		//npcs
			
			//bankers
			registerEntity(EntityBanker.class, "banker", 80, 3, false, 0xefff11, 0xededed);
			
			//quests
			registerEntity(EntityRobertCromwell.class, "robert_cromwell", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
			registerEntity(EntityUlricWeston.class, "ulric_weston", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
			registerEntity(EntityEvaTeffan.class, "eva_teffan", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
			registerEntity(EntityKelvinWhitestone.class, "kelvin_whitestone", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
			
			//swords
			registerEntity(EntityHumanShopKeeper.class, "sam_derric", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
			
		registerEntitySpawns();
	}
	
	
	static void registerEntitySpawns() {
		EntityRegistry.addSpawn(EntityRat.class, 10, 1, 1, EnumCreatureType.MONSTER, Biomes.DEFAULT);
		EntityRegistry.addSpawn(EntityGiantScorpian.class, 10, 1, 1, EnumCreatureType.MONSTER, Biomes.DESERT);
	}
	
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency,
			boolean sendsVelocityUpdates, int eggA, int eggB) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID,  entityName), entityClass, entityName, 
				getNextId(), TOCMain.instance, trackingRange, updateFrequency, sendsVelocityUpdates, eggA, eggB);
	}
	
	private static int entityId = 0; 
	private static int getNextId() {
		return entityId++;
	}
	
}
