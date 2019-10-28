package skeeter144.toc.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skeeter144.toc.entity.mob.monster.EntityGoblin;
import skeeter144.toc.entity.mob.monster.EntityViking;
import skeeter144.toc.entity.mob.npc.questgiver.EntityEvaTeffan;
import skeeter144.toc.entity.mob.npc.questgiver.EntityKelvinWhitestone;
import skeeter144.toc.entity.mob.npc.questgiver.EntityMarlinMonroe;
import skeeter144.toc.entity.mob.npc.questgiver.EntityRobertCromwell;
import skeeter144.toc.entity.mob.npc.questgiver.EntitySeloviusKamazz;
import skeeter144.toc.entity.mob.npc.questgiver.EntityUlricWeston;
import skeeter144.toc.util.Reference;

public class TOCEntityType {
	public static EntityType<EntityGoblin> GOBLIN;
	public static EntityType<?> VIKING;
	public static EntityType<?> RAT;
	public static EntityType<?> GIANT_SCORPIAN;
	public static EntityType<?> GIANT_SPIDER;
	public static EntityType<?> GHOST;
	public static EntityType<?> SIREN;
	public static EntityType<?> MULE;
	public static EntityType<?> DONKEY;
	public static EntityType<?> HORSE;
	public static EntityType<?> PEGASUS;
	public static EntityType<?> GRIFFIN;
	
	public static EntityType<?> ROBERT_CROMWELL;
	public static EntityType<?> ULRIC_WESTON;
	public static EntityType<?> EVA_TEFFAN;
	public static EntityType<?> MARLIN_MONROE;
	public static EntityType<?> KELVIN_WHITESTONE;
	public static EntityType<?> SELOVIUS_KAMAZZ;
	
	
	private static final List<EntityType<?>> ENTITY_TYPES = new LinkedList<>();
	
	static {
		VIKING = createEntityType("viking", EntityViking.class, EntityViking::new, 64, 1, false);
		ROBERT_CROMWELL = createEntityType("robert_cromwell", EntityRobertCromwell.class, EntityRobertCromwell::new, 64, 1, false);
		ULRIC_WESTON = createEntityType("ulril_weston", EntityUlricWeston.class, EntityUlricWeston::new, 64, 1, false);
		EVA_TEFFAN = createEntityType("eva_teffan", EntityEvaTeffan.class, EntityEvaTeffan::new, 64, 1, false);
		MARLIN_MONROE = createEntityType("marlin_monroe", EntityMarlinMonroe.class, EntityMarlinMonroe::new, 64, 1, false);
		KELVIN_WHITESTONE = createEntityType("kelvin_whitestone", EntityKelvinWhitestone.class, EntityKelvinWhitestone::new, 64, 1, false);
		SELOVIUS_KAMAZZ = createEntityType("selovius_kamazz", EntitySeloviusKamazz.class, EntitySeloviusKamazz::new, 64, 1, false);
	}
	
	private static <T extends Entity> EntityType<T> createEntityType(String id, Class<? extends T> entityClass, Function<? super World, ? extends T> factory, int range, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityType<T> type = EntityType.Builder.create(entityClass, factory).tracker(range, updateFrequency, sendsVelocityUpdates).build(Reference.MODID + ":" + id);
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