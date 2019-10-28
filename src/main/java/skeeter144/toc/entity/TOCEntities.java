package skeeter144.toc.entity;

import java.util.HashMap;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.entity.mob.monster.EntityRat;
import skeeter144.toc.entity.mob.monster.EntityViking;
import skeeter144.toc.util.Reference;

public class TOCEntities {
	


	
	static HashMap<String, Integer> vanillaMobXp = new HashMap<>();
	public static void initVanillaMobXp() {
		vanillaMobXp.put(ForgeRegistries.ENTITIES.getValue(new ResourceLocation("chicken")).func_212546_e().toString(), 5);
		vanillaMobXp.put(ForgeRegistries.ENTITIES.getValue(new ResourceLocation("sheep")).func_212546_e().toString(), 5);
	}
	
	public static int getXpFroVanillaMob(String className) {
		Integer xp = vanillaMobXp.get(className);
		return  xp != null ? xp : 0;
	}
	
	public static void registerEntities() {
		//monsters
//		viking = EntityType.Builder.create(EntityViking.class, EntityViking::new).build(Reference.MODID + ":viking").setRegistryName(Reference.MODID + ":viking");
//		rat = EntityType.Builder.create(EntityRat.class, EntityRat::new).build(Reference.MODID + ":rat").setRegistryName(Reference.MODID + ":rat");
//		goblin = EntityType.Builder.create(EntityViking.class, EntityViking::new).build(Reference.MODID + ":goblin").setRegistryName(Reference.MODID + ":goblin");
//		giant_scorpian = EntityType.Builder.create(EntityViking.class, EntityViking::new).build(Reference.MODID + ":giant_scorpian").setRegistryName(Reference.MODID + ":giant_scorpian");
//		giant_spider = EntityType.Builder.create(EntityViking.class, EntityViking::new).build(Reference.MODID + ":giant_spider").setRegistryName(Reference.MODID + ":giant_spider");
//		ghost = EntityType.Builder.create(EntityViking.class, EntityViking::new).build(Reference.MODID + ":ghost").setRegistryName(Reference.MODID + ":ghost");
//		siren = EntityType.Builder.create(EntityViking.class, EntityViking::new).build(Reference.MODID + ":siren").setRegistryName(Reference.MODID + ":siren");
////		registerEntity(EntityRat.class, "rat", 80, 3, true, 0x9999990, 0x606060);
//		registerEntity(EntityGoblin.class, "goblin", 80, 3, true, 0x007F0E, 0x877436);
//		registerEntity(EntityGiantScorpian.class, "giant_scorpian", 80, 3, true, 0x00AA0E, 0x87BD36);
//		registerEntity(EntityGiantSpider.class, "giant_spider", 80, 3, true, 0xFF0000, 0x000000);
//		registerEntity(EntityGhost.class, "ghost", 80, 3, true, 0xFFFFFF, 0x000000);
//		registerEntity(EntitySiren.class, "siren", 80, 3, true, 0xFFFFFF, 0x000000);
		
		//mounts
			//horse-like
//			registerEntity(EntityMuleMount.class, "mule_mount", 80, 3, true, 0x7d3800, 0x361800);
//			registerEntity(EntityDonkeyMount.class, "donkey_mount", 80, 3, true, 0x7d3800, 0x361800);
//			registerEntity(EntityVariableHorseMount.class, "horse_mount", 80, 3, true, 0x7d3800, 0x361800);
//			
//			//flying
//			registerEntity(EntityPegasus.class, "pegasus", 80, 3, true, 0xffffff, 0xdddddd);
//			registerEntity(EntityGriffin.class, "griffin", 80, 3, true, 0xfa9d55, 0x732e00);
//		
//		//npcs
//			
//			//bankers
//			registerEntity(EntityBanker.class, "banker", 80, 3, false, 0xefff11, 0xededed);
//			
//			//quests
//			registerEntity(EntityRobertCromwell.class, "robert_cromwell", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
//			registerEntity(EntityUlricWeston.class, "ulric_weston", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
//			registerEntity(EntityEvaTeffan.class, "eva_teffan", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
//			registerEntity(EntityKelvinWhitestone.class, "kelvin_whitestone", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
//			registerEntity(EntityMarlinMonroe.class, "marlin_monroe", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
//			registerEntity(EntitySeloviusKamazz.class, "selovius_kamazz", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
//			
//			
//			//shop keepers
//			// swords
//			registerEntity(EntityHumanShopKeeper.class, "sam_derric", 80, 3, true, 0xFFFFFFFF, 0xFFFFFFFF);
			
	}
	
	
	
	
	private static int entityId = 0; 
	private static int getNextId() {
		return entityId++;
	}
	
}
