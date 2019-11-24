package skeeter144.toc.entityeffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import skeeter144.toc.entityeffect.effects.EntityEffect;
import skeeter144.toc.entityeffect.effects.EntityEffect.EffectEndType;

@Mod.EventBusSubscriber
public class ServerEffectHandler {

	
	private static HashMap<UUID, List<EntityEffect>> effectsMap = new HashMap<UUID, List<EntityEffect>>();
	
	@SubscribeEvent
	public static void livingUpdate(LivingUpdateEvent event)
	{
		if(!event.getEntity().world.isRemote && (event.getEntity() instanceof LivingEntity || event.getEntity() instanceof ServerPlayerEntity)) {
			int id = event.getEntity().getEntityId();
			List<EntityEffect> effects = effectsMap.get(event.getEntity().getUniqueID());
			if(effects != null)
			{
				for(int i = effects.size() - 1; i >= 0; --i)
				{
					if(!effects.get(i).isActive())
					{
						effects.remove(i);
					}
				}
				
				for(EntityEffect e : effects)
				{
					e.tick();
				}
			}
		}
	}
	
	public static void removeEffectFromEntity(UUID effectedId, Class effectClass)
	{
		if(effectsMap.containsKey(effectedId)) {
			List<EntityEffect> list = effectsMap.get(effectedId);
			if(list == null) {
				list = new ArrayList<EntityEffect>();
			}	
			for(int i = list.size() - 1; i >= 0; --i) {
				if(list.get(i).getClass().equals(effectClass)) {
					list.get(i).end(EffectEndType.NORMAL);
					list.remove(i);
				}
			}
		}
	}
	
	public static EffectAddResult attemptAddNewEffect(UUID effectedId, EntityEffect effect)
	{
		if(entityHasEffectAlready(effectedId, effect)){
			return EffectAddResult.ALREADY_EFFECTED;
		}
		
		if(effectsMap.containsKey(effectedId))
		{
			List<EntityEffect> list = effectsMap.get(effectedId);
			if(list == null) {
				list = new ArrayList<EntityEffect>();
			}	
			list.add(effect);
			effectsMap.put(effectedId, list);
		}
		else
		{
			List<EntityEffect> list = new ArrayList<EntityEffect>();
			list.add(effect);
			effectsMap.put(effectedId, list);
		}
		effect.start();
		return EffectAddResult.SUCCESS;
	}
	
	private static boolean entityHasEffectAlready(UUID entityId, EntityEffect effect)
	{
		List<EntityEffect> list = effectsMap.get(entityId);
		if(list != null)
		{
			for(EntityEffect e : list)
			{
				if(e.getEffectName().equals(effect.getEffectName()))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public enum EffectAddResult
	{
		IMMUNE,
		ALREADY_EFFECTED,
		SUCCESS
	}
}
