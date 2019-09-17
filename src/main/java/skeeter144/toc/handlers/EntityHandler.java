package skeeter144.toc.handlers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import skeeter144.toc.TOCMain;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.entity.TOCEntities;
import skeeter144.toc.entity.TOCEntity;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.entity.projectile.EntityTOCArrow;
import skeeter144.toc.items.weapons.TOCGreatAxe;
import skeeter144.toc.items.weapons.TOCGreatSword;
import skeeter144.toc.items.weapons.TOCSword;
import skeeter144.toc.network.AddLevelXpMessage;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.regions.Point;
import skeeter144.toc.regions.Region;
import skeeter144.toc.regions.RegionBound;

public class EntityHandler {

	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent event) {
		Entity e = event.getEntity();

		if (!e.world.isRemote && e instanceof EntityPlayer) {
			TOCPlayer pl = TOCMain.pm.getPlayer((EntityPlayer) e);
			pl.tick();

			if (e.ticksExisted % 100 == 0) {
				((EntityPlayer) e).getFoodStats().setFoodLevel(20);
			}

			// do player region ticks
			if (e.ticksExisted % 20 == 0) {
				if (TOCMain.rm == null || TOCMain.rm.playerRegions == null) {
					if (TOCMain.rm == null)
						System.out.println("Fatal error: TOCMain.rm == null");
					if (TOCMain.rm.playerRegions == null)
						System.out.println("Fatal error: TOCMain.rm.playerRegions == null");
					return;
				}

				Set<Region> foundRegions = new HashSet<Region>();
				for (Map.Entry<String, Region> entry : TOCMain.rm.getRegions().entrySet()) {
					for (RegionBound rb : entry.getValue().bounds) {
						if (rb.containsPoint(new Point((int) e.posX, (int) e.posZ))) {
							foundRegions.add(entry.getValue());
							break;
						}
					}
				}

				Set<Region> playerRegions = TOCMain.rm.playerRegions.get(e.getUniqueID());
				// region exits
				if (playerRegions != null) {
					List<Region> exitedRegions = new ArrayList<Region>();
					for (Region r : playerRegions) {
						if (!foundRegions.contains(r)) {
							r.onRegionExited((EntityLivingBase) e);
							exitedRegions.add(r);
						}
					}

					for (Region r : exitedRegions) {
						playerRegions.remove(r);
					}
					TOCMain.rm.playerRegions.put(e.getUniqueID(), playerRegions);
				}

				// region ticks
				if (playerRegions != null) {
					for (Region r : playerRegions) {
						r.onRegionTick((EntityLivingBase) e);
					}
				}

				// region entries
				for (Region r : foundRegions) {
					if (playerRegions == null || !playerRegions.contains(r)) {
						r.onRegionEntered((EntityLivingBase) e);
						if (playerRegions == null)
							playerRegions = new HashSet<Region>();
						playerRegions.add(r);
						TOCMain.rm.playerRegions.put(e.getUniqueID(), playerRegions);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void entityJoin(EntityJoinWorldEvent e) {
		if (e.getEntity().world.isRemote) {
			return;
		}

		if (e.getEntity() instanceof CustomMob) {
			EntityLevels levels = new EntityLevels();
			CustomMob cm = (CustomMob) e.getEntity();
			levels.setLevelFor(Levels.ATTACK, cm.attackLevel, false);
			levels.setLevelFor(Levels.STRENGTH, cm.strengthLevel, false);
			levels.setLevelFor(Levels.DEFENSE, cm.defenseLevel, false);
			levels.setLevelFor(Levels.MAGIC, cm.magicLevel, false);
			levels.setLevelFor(Levels.HITPOINTS, cm.hpLevel, false);
			TOCMain.mm.addMob(e.getEntity().getUniqueID(), new TOCEntity(e.getEntity(), levels));
		}
	}

	@SubscribeEvent
	public void entityDeath(LivingDeathEvent e) {
		if (e.getEntity().world.isRemote) {
			return;
		}

		if (e.getSource().getTrueSource() instanceof EntityPlayer) {
			Levels level = Levels.ATTACK;
			int xp = CombatManager.getXpForEntity(e.getEntityLiving());
			if (e.getSource() instanceof TOCDamageSource) {
				TOCDamageSource src = (TOCDamageSource) e.getSource();
				level = CombatManager.levelForDamageSource(src);
			}
			level = CombatManager.levelForHeldItem(((EntityPlayer)e.getSource().getTrueSource()));
			TOCPlayer player = TOCMain.pm.getPlayer(((EntityPlayer) e.getSource().getTrueSource()));

			boolean leveledUp = player.getPlayerLevels().addExp(level, xp);
			boolean hpLeveled = player.getPlayerLevels().addExp(Levels.HITPOINTS, xp / 4);

			EntityPlayerMP truSource = (EntityPlayerMP) e.getSource().getTrueSource();

			Network.INSTANCE.sendTo(new AddLevelXpMessage(level.name(), xp), truSource);
			Network.INSTANCE.sendTo(new AddLevelXpMessage(Levels.HITPOINTS.name(), xp / 4), truSource);
			if (hpLeveled || leveledUp)
				player.combatLeveledUp();
		}
	}

	@SubscribeEvent
	public void entityHurt(LivingHurtEvent event) {
		if (event.getEntity() instanceof EntityPlayer)
			event.setCanceled(true);

		if (event.getEntity().world.isRemote)
			return;

		DamageSource source = event.getSource();

		if (source.getImmediateSource() instanceof EntityTOCArrow) {
			Class c = event.getClass();
			try {
				Field f = c.getDeclaredField("source");
				f.setAccessible(true);
				TOCDamageSource tds = new TOCDamageSource(DamageType.RANGED, source.getTrueSource());
				source = tds;
				f.set(event, tds);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
			}

		}
		event.setCanceled(true);
		float amount = 0;
		Entity attacker = event.getSource().getTrueSource();
		if (attacker != null) {
			if (attacker instanceof EntityPlayer) {
				if (event.getEntityLiving() instanceof EntityPlayer) {
					amount = TOCMain.cm.playerHurtPlayer((EntityPlayer) attacker, (EntityPlayer) event.getEntity(),
							event.getAmount(), source);
				} else {
					if (event.getEntity() instanceof EntityLiving)
						amount = TOCMain.cm.playerHurtEntity((EntityPlayer) attacker, (EntityLiving) event.getEntity(),
								event.getAmount(), source);
				}
			} else {
				if (event.getEntityLiving() instanceof EntityPlayer) {
					try {
						amount = TOCMain.cm.entityHurtPlayer((EntityPlayer) event.getEntity(), (EntityLiving) attacker,
								event.getAmount(), source);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					if (attacker instanceof EntityLiving && event.getEntity() instanceof EntityLiving) {
						try {
							amount = TOCMain.cm.entityHurtAnother((EntityLiving) attacker,
									(EntityLiving) event.getEntity(), event.getAmount(), source);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			}
		} else {
			amount = TOCMain.cm.calcEntityDamage(event.getEntityLiving(), (int) event.getAmount(), source);
		}

		if (amount != 0) {
			if (event.getEntityLiving() instanceof EntityPlayer) {
				TOCMain.pm.getPlayer((EntityPlayer) event.getEntity()).adjustVitals((int) -event.getAmount(), 0);
			} else {
				event.setCanceled(false);
				event.setAmount(amount);
			}
		}

		Entity e = event.getEntity();
		if (source == DamageSource.LIGHTNING_BOLT) {
			event.getEntity().attackEntityFrom(new TOCDamageSource(DamageType.PROCESSED), 20);
			// TODO possibly keep track of what lightning bolts are owned by who to allow
			// for
			// "skulling" with lightning spells
		}
	}

}
