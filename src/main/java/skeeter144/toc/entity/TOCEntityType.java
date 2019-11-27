package skeeter144.toc.entity;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.entity.mob.monster.EntityGhost;
import skeeter144.toc.entity.mob.monster.EntityGiantScorpian;
import skeeter144.toc.entity.mob.monster.EntityGiantSpider;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.entity.mob.monster.EntityRat;
import skeeter144.toc.entity.mob.monster.EntitySiren;
import skeeter144.toc.entity.mob.monster.EntityViking;
import skeeter144.toc.entity.mob.mount.flying.EntityGriffin;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.entity.mob.npc.questgiver.EntityEvaTeffan;
import skeeter144.toc.entity.mob.npc.questgiver.EntityKelvinWhitestone;
import skeeter144.toc.entity.mob.npc.questgiver.EntityMarlinMonroe;
import skeeter144.toc.entity.mob.npc.questgiver.EntityRobertCromwell;
import skeeter144.toc.entity.mob.npc.questgiver.EntitySeloviusKamazz;
import skeeter144.toc.entity.mob.npc.questgiver.EntityUlricWeston;
import skeeter144.toc.util.Reference;

public class TOCEntityType {
	public static EntityType<? extends CustomMob> GOBLIN;
	public static EntityType<? extends CustomMob> VIKING;
	public static EntityType<? extends CustomMob> RAT;
	public static EntityType<? extends CustomMob> GIANT_SCORPIAN;
	public static EntityType<? extends CustomMob> GIANT_SPIDER;
	public static EntityType<? extends CustomMob> GHOST;
	public static EntityType<? extends CustomMob> SIREN;
	public static EntityType<? extends AbstractChestedHorseEntity> MULE;
	public static EntityType<? extends AbstractChestedHorseEntity> DONKEY;
	public static EntityType<? extends AbstractChestedHorseEntity> HORSE;
	public static EntityType<? extends AbstractChestedHorseEntity> PEGASUS;
	public static EntityType<? extends AbstractChestedHorseEntity> GRIFFIN;
	public static EntityType<? extends EntityNpc> ROBERT_CROMWELL;
	public static EntityType<? extends EntityNpc> ULRIC_WESTON;
	public static EntityType<? extends EntityNpc> EVA_TEFFAN;
	public static EntityType<? extends EntityNpc> MARLIN_MONROE;
	public static EntityType<? extends EntityNpc> KELVIN_WHITESTONE;
	public static EntityType<? extends EntityNpc> SELOVIUS_KAMAZZ;

	private static final List<EntityType<?>> ENTITY_TYPES = new LinkedList<>();
	
	static {
		ROBERT_CROMWELL = createEntityType("robert_cromwell", 64, 1, Builder.<EntityRobertCromwell>create(EntityRobertCromwell::new, EntityClassification.AMBIENT));
		ULRIC_WESTON = createEntityType("ulric_weston", 64, 1, Builder.<EntityUlricWeston>create(EntityUlricWeston::new, EntityClassification.AMBIENT));
		EVA_TEFFAN = createEntityType("eva_teffan", 64, 1, Builder.<EntityEvaTeffan>create(EntityEvaTeffan::new, EntityClassification.AMBIENT));
		MARLIN_MONROE = createEntityType("marlin_monroe", 64, 1, Builder.<EntityMarlinMonroe>create(EntityMarlinMonroe::new, EntityClassification.AMBIENT));
		KELVIN_WHITESTONE = createEntityType("kelvin_whitestone", 64, 1, Builder.<EntityKelvinWhitestone>create(EntityKelvinWhitestone::new, EntityClassification.AMBIENT));
		SELOVIUS_KAMAZZ = createEntityType("selovius_kamazz", 64, 1, Builder.<EntitySeloviusKamazz>create(EntitySeloviusKamazz::new, EntityClassification.AMBIENT));
		
		GOBLIN = createEntityType("goblin",64, 3, Builder.<EntityGoblin>create(EntityGoblin::new, EntityClassification.MONSTER));
		VIKING = createEntityType("viking", 64, 3, Builder.<EntityViking>create(EntityViking::new, EntityClassification.MONSTER));
		RAT = createEntityType("rat", 64, 3, Builder.<EntityRat>create(EntityRat::new, EntityClassification.AMBIENT));
		GIANT_SCORPIAN = createEntityType("giant_scorpian", 64, 3, Builder.<EntityGiantScorpian>create(EntityGiantScorpian::new, EntityClassification.MONSTER));
		GIANT_SPIDER = createEntityType("giant_spider", 64, 3, Builder.<EntityGiantSpider>create(EntityGiantSpider::new, EntityClassification.MONSTER));
		GHOST = createEntityType("ghost", 64, 3, Builder.<EntitySiren>create(EntitySiren::new, EntityClassification.MONSTER));
		SIREN = createEntityType("siren", 64, 3, Builder.<EntityGhost>create(EntityGhost::new, EntityClassification.MONSTER));
		//MULE = createEntityType("mule", MuleEntity.class, MuleEntity::new, 64, 3, false);
		GRIFFIN = createEntityType("griffen", 64, 3, Builder.<EntityGriffin>create(EntityGriffin::new, EntityClassification.CREATURE));
	}
	
	private static <T extends Entity> EntityType<T> createEntityType(String id, int range, int updateFrequency, Builder<T> builder){
	    EntityType<T> type = builder.setTrackingRange(range).setUpdateInterval(updateFrequency).build(Reference.MODID + ":" + id);
		type.setRegistryName(new ResourceLocation(Reference.MODID, id));
		ENTITY_TYPES.add(type);
		return type;
	}

	@SubscribeEvent
	public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().registerAll(ENTITY_TYPES.toArray(new EntityType<?>[ENTITY_TYPES.size()]));
	}
}