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
import skeeter144.toc.util.Reference;

public class TOCEntityType {
	public static final EntityType<EntityGoblin> GOBLIN;
	
	private static final List<EntityType<?>> ENTITY_TYPES = new LinkedList<>();
	
	static {
		GOBLIN = createEntityType("goblin", EntityGoblin.class, EntityGoblin::new, 64, 1, false);
	}
	
	private static <T extends Entity> EntityType<T> createEntityType(String id, Class<? extends T> entityClass, Function<? super World, ? extends T> factory, int range, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityType<T> type = EntityType.Builder.create(entityClass, factory).tracker(range, updateFrequency, sendsVelocityUpdates).build(Reference.MODID + ":" + id);
		type.setRegistryName(new ResourceLocation(Reference.MODID, id));
		return type;
	}

	public static void register()
	{
		ENTITY_TYPES.add(GOBLIN);
	}
	
	@SubscribeEvent
	public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event)
	{
		ENTITY_TYPES.forEach(entityType -> event.getRegistry().register(entityType));
	}
}