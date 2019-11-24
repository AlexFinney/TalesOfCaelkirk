package skeeter144.toc.quest;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import skeeter144.toc.combat.TOCDamageSource;

public class QuestUtil {
//public static final ResourceLocation quest_objective_icon = new ResourceLocation (Reference.MODID, "textures/icons");
	public static boolean wasEntityOfTypeAndKilledByPlayer(LivingDeathEvent e, Class<? extends LivingEntity> entityClass) {
		
		if(e.getEntityLiving().getClass().equals(entityClass)) {
			if(e.getSource().getTrueSource() instanceof ServerPlayerEntity) {
				return true;
			}
			
			if(e.getSource() instanceof TOCDamageSource) {
				TOCDamageSource ds = (TOCDamageSource)e.getSource();
				if(ds.source instanceof PlayerEntity) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static PlayerEntity getPlayerFromEvent(LivingDeathEvent e) {
		if(e.getSource().getTrueSource() instanceof PlayerEntity) {
			return (PlayerEntity) e.getSource().getTrueSource();
		}
		
		if(e.getSource() instanceof TOCDamageSource) {
			TOCDamageSource ds = (TOCDamageSource)e.getSource();
			if(ds.source instanceof PlayerEntity) {
				return (PlayerEntity) ds.source;
			}
		}
		return null;
	}
}
