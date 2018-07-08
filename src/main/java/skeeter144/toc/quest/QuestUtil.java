package skeeter144.toc.quest;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.util.Reference;

public class QuestUtil {
//public static final ResourceLocation quest_objective_icon = new ResourceLocation (Reference.MODID, "textures/icons");
	public static boolean wasEntityOfTypeAndKilledByPlayer(LivingDeathEvent e, Class<? extends EntityLivingBase> entityClass) {
		
		if(e.getEntityLiving().getClass().equals(entityClass)) {
			if(e.getSource().getTrueSource() instanceof EntityPlayerMP) {
				return true;
			}
			
			if(e.getSource() instanceof TOCDamageSource) {
				TOCDamageSource ds = (TOCDamageSource)e.getSource();
				if(ds.source instanceof EntityPlayer) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static EntityPlayer getPlayerFromEvent(LivingDeathEvent e) {
		if(e.getSource().getTrueSource() instanceof EntityPlayer) {
			return (EntityPlayer) e.getSource().getTrueSource();
		}
		
		if(e.getSource() instanceof TOCDamageSource) {
			TOCDamageSource ds = (TOCDamageSource)e.getSource();
			if(ds.source instanceof EntityPlayer) {
				return (EntityPlayer) ds.source;
			}
		}
		return null;
	}
}
