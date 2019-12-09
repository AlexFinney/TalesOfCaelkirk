package skeeter144.toc.handlers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import skeeter144.toc.TOCMain;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.combat.TOCDamageSource;
import skeeter144.toc.entity.TOCEntity;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.entity.projectile.EntityTOCArrow;
import skeeter144.toc.network.AddLevelXpPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.regions.Point;
import skeeter144.toc.regions.Region;
import skeeter144.toc.regions.RegionBound;
import skeeter144.toc.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class EntityHandler {

	@SubscribeEvent
	public static void livingUpdate(LivingUpdateEvent event) {
		Entity e = event.getEntity();
		if (e.world.isRemote || !(e instanceof PlayerEntity))
			return;

		TOCPlayer pl = TOCMain.pm.getPlayer((PlayerEntity) e);
		pl.tick();

		if (e.ticksExisted % 100 == 0) {
			((PlayerEntity) e).getFoodStats().setFoodLevel(20);
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
						r.onRegionExited((LivingEntity) e);
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
					r.onRegionTick((LivingEntity) e);
				}
			}

			// region entries
			for (Region r : foundRegions) {
				if (playerRegions == null || !playerRegions.contains(r)) {
					r.onRegionEntered((LivingEntity) e);
					if (playerRegions == null)
						playerRegions = new HashSet<Region>();
					playerRegions.add(r);
					TOCMain.rm.playerRegions.put(e.getUniqueID(), playerRegions);
				}
			}
		}
	}

	@SubscribeEvent
	public static void entityJoin(EntityJoinWorldEvent e) {
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

		// fuck slimes
		if (e.getEntity() instanceof SlimeEntity || e.getEntity() instanceof CreeperEntity ||
				 e.getEntity() instanceof ZombieEntity || e.getEntity() instanceof SkeletonEntity
				 || e.getEntity() instanceof SpiderEntity || e.getEntity() instanceof WitchEntity 
				 || e.getEntity() instanceof EndermanEntity) 
		{
			e.setCanceled(true);
			e.getEntity().remove();
		}
	}

	@SubscribeEvent
	public static void entityDeath(LivingDeathEvent e) {
		if (e.getEntity().world.isRemote) {
			return;
		}

		if (e.getSource().getTrueSource() instanceof PlayerEntity) {
			Levels level = Levels.ATTACK;
			int xp = CombatManager.getXpForEntity(e.getEntityLiving());
			if (e.getSource() instanceof TOCDamageSource) {
				TOCDamageSource src = (TOCDamageSource) e.getSource();
				level = CombatManager.levelForDamageSource(src);
			}
			level = CombatManager.levelForHeldItem(((PlayerEntity) e.getSource().getTrueSource()));
			TOCPlayer player = TOCMain.pm.getPlayer(((PlayerEntity) e.getSource().getTrueSource()));

			boolean leveledUp = player.getPlayerLevels().addExp(level, xp);
			boolean hpLeveled = player.getPlayerLevels().addExp(Levels.HITPOINTS, xp / 4);

			ServerPlayerEntity truSource = (ServerPlayerEntity) e.getSource().getTrueSource();

			Network.INSTANCE.sendTo(new AddLevelXpPKT(level.name(), xp), truSource);
			Network.INSTANCE.sendTo(new AddLevelXpPKT(Levels.HITPOINTS.name(), xp / 4), truSource);
			if (hpLeveled || leveledUp)
				player.combatLeveledUp();
		}
	}

	@SubscribeEvent
	public static void entityHurt(LivingHurtEvent event) {
		if (event.getEntity() instanceof PlayerEntity)
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
			if (attacker instanceof PlayerEntity) {
				if (event.getEntityLiving() instanceof PlayerEntity) {
					amount = TOCMain.cm.playerHurtPlayer((PlayerEntity) attacker, (PlayerEntity) event.getEntity(),
							event.getAmount(), source);
				} else {
					if (event.getEntity() instanceof LivingEntity)
						amount = TOCMain.cm.playerHurtEntity((PlayerEntity) attacker, (LivingEntity) event.getEntity(),
								event.getAmount(), source);
				}
			} else {
				if (event.getEntityLiving() instanceof PlayerEntity) {
					try {
						amount = TOCMain.cm.entityHurtPlayer((PlayerEntity) event.getEntity(), (LivingEntity) attacker,
								event.getAmount(), source);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					if (attacker instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
						try {
							amount = TOCMain.cm.entityHurtAnother((LivingEntity) attacker,
									(LivingEntity) event.getEntity(), event.getAmount(), source);
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
			if (event.getEntityLiving() instanceof PlayerEntity) {
				TOCMain.pm.getPlayer((PlayerEntity) event.getEntity()).adjustVitals((int) -event.getAmount(), 0);
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
