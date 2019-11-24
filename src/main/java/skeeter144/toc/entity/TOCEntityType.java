package skeeter144.toc.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
	public static EntityType<? extends MobEntity> GOBLIN;
	public static EntityType<? extends MobEntity> VIKING;
	public static EntityType<? extends MobEntity> RAT;
	public static EntityType<? extends MobEntity> GIANT_SCORPIAN;
	public static EntityType<? extends MobEntity> GIANT_SPIDER;
	public static EntityType<? extends MobEntity> GHOST;
	public static EntityType<? extends MobEntity> SIREN;
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
		ROBERT_CROMWELL = createEntityType("robert_cromwell", EntityRobertCromwell.class, EntityRobertCromwell::new, 64, 1, false);
		ULRIC_WESTON = createEntityType("ulric_weston", EntityUlricWeston.class, EntityUlricWeston::new, 64, 1, false);
		EVA_TEFFAN = createEntityType("eva_teffan", EntityEvaTeffan.class, EntityEvaTeffan::new, 64, 1, false);
		MARLIN_MONROE = createEntityType("marlin_monroe", EntityMarlinMonroe.class, EntityMarlinMonroe::new, 64, 1, false);
		KELVIN_WHITESTONE = createEntityType("kelvin_whitestone", EntityKelvinWhitestone.class, EntityKelvinWhitestone::new, 64, 1, false);
		SELOVIUS_KAMAZZ = createEntityType("selovius_kamazz", EntitySeloviusKamazz.class, EntitySeloviusKamazz::new, 64, 1, false);
		
		GOBLIN = createEntityType("goblin", EntityGoblin.class, EntityGoblin::new, 64, 3, false);
		VIKING = createEntityType("viking", EntityViking.class, EntityViking::new, 64, 3, false);
		RAT = createEntityType("rat", EntityRat.class, EntityRat::new, 64, 3, false);
		GIANT_SCORPIAN = createEntityType("giant_scorpian", EntityGiantScorpian.class, EntityGiantScorpian::new, 64, 3, false);
		GIANT_SPIDER = createEntityType("giant_spider", EntityGiantSpider.class, EntityGiantSpider::new, 64, 3, false);
		GHOST = createEntityType("ghost", EntityGhost.class, EntityGhost::new, 64, 3, false);
		SIREN = createEntityType("siren", EntitySiren.class, EntitySiren::new, 64, 3, false);
		//MULE = createEntityType("mule", MuleEntity.class, MuleEntity::new, 64, 3, false);
		GRIFFIN = createEntityType("griffen", EntityGriffin.class, EntityGriffin::new, 64, 3, false);
		
	}
	
	private static <T extends Entity> EntityType<T> createEntityType(String id, Class<? extends T> entityClass, Function<? super World, ? extends T> factory, int range, int updateFrequency, boolean sendsVelocityUpdates)
	{
//		EntityType<T> type = EntityType.Builder.create(entityClass, factory).tracker(range, updateFrequency, sendsVelocityUpdates).build(Reference.MODID + ":" + id);
//		type.setRegistryName(new ResourceLocation(Reference.MODID, id));
//		ENTITY_TYPES.add(type);
//		return type;
		return null;
	}

	@SubscribeEvent
	public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().registerAll(ENTITY_TYPES.toArray(new EntityType<?>[ENTITY_TYPES.size()]));
	}
}