package skeeter144.toc.combat;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import skeeter144.toc.TOCMain;
import skeeter144.toc.combat.CombatManager.DamageType;
import skeeter144.toc.entity.TOCEntities;
import skeeter144.toc.entity.mob.CustomMob;
import skeeter144.toc.items.armor.CustomArmor;
import skeeter144.toc.items.magic.BasicWand;
import skeeter144.toc.items.weapons.TOCBow;
import skeeter144.toc.items.weapons.TOCGreatAxe;
import skeeter144.toc.items.weapons.TOCGreatSword;
import skeeter144.toc.items.weapons.TOCSword;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SpawnBlockedMessage;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.EntityLevels.Levels;
import skeeter144.toc.player.Level;
import skeeter144.toc.player.TOCPlayer;

public class CombatManager {
	
	public static final float LOWER_DAMAGE_PCT = .5f;
	
	static CombatManager instance;
	private CombatManager() {}
	public static CombatManager instance() {
		if(instance == null)
			instance = new CombatManager();
		
		return instance;
	}
	
	public float calcEntityDamage(EntityLivingBase e, float amount, DamageSource source) {
		if(e.world.isRemote)
			return 100;
		
		EntityLevels attackerLevels = null;
		EntityLevels defenderLevels = null;
		
		if(e instanceof EntityPlayer) {
			defenderLevels = TOCMain.pm.getPlayer((EntityPlayer) e).getPlayerLevels();
		}else if(e instanceof CustomMob) {
			defenderLevels = TOCMain.mm.getMob(e.getPersistentID()).levels;
		}
		
		if(source.getTrueSource() != null) {
			if(source.getTrueSource() instanceof EntityPlayer) {
				attackerLevels = TOCMain.pm.getPlayer((EntityPlayer) source.getTrueSource()).getPlayerLevels();
			}else if(source.getTrueSource() instanceof CustomMob) {
				attackerLevels = TOCMain.mm.getMob(source.getTrueSource().getPersistentID()).levels;
			}
		}else {
			return amount;
		}
		

		DamageType type = DamageType.PHYSICAL;
		if(source instanceof TOCDamageSource) {
			TOCDamageSource tocSource = (TOCDamageSource)source;
			type = tocSource.type;
		}
		boolean doTOCCombatRolls = false;
		if(type == DamageType.PHYSICAL && attackerLevels != null && defenderLevels != null) {
			doTOCCombatRolls = true;
		}
		
		if(doTOCCombatRolls) {
			int defenseRoll = TOCMain.rand.nextInt(defenderLevels.getLevelFor(Levels.DEFENSE) + 1);
			int attackRoll = TOCMain.rand.nextInt(attackerLevels.getLevelFor(Levels.ATTACK) + 1);
			attackRoll = 60;
			
			if(attackRoll < defenseRoll) {
				if(TOCMain.rand.nextFloat() < .65f) {
					Network.INSTANCE.sendToAllAround(new SpawnBlockedMessage(e), 
							new TargetPoint(e.getEntityWorld().provider.getDimension(), e.posX, e.posY, e.posZ, 50));
					return -1;
				}
			}
			
			int strengthRoll = TOCMain.rand.nextInt(attackerLevels.getLevelFor(Levels.STRENGTH) + 1);
			float ratio = ((float)strengthRoll) / attackerLevels.getLevelFor(Levels.STRENGTH);
			float strengthMult = ratio * EntityLevels.STRENGTH_MULT_PER_LEVEL * attackerLevels.getLevelFor(Levels.STRENGTH);
			float bonusDamage = amount * strengthMult;
			amount += bonusDamage;
		}
		
		
		float physResistance = 0;
		float magicResistance = 0;
		float rangedResistance = 0;
		for(ItemStack is : e.getArmorInventoryList()) {
			if(is.getItem() instanceof CustomArmor) {
				CustomArmor armor = (CustomArmor)is.getItem();
				physResistance += armor.physicalResistance;
				magicResistance += armor.magicalResistance;
				rangedResistance += armor.rangedResistance;
			}
		}
		
		float reduction = 0;
		if(type == DamageType.PHYSICAL) {
			reduction = amount * physResistance;
		}else if(type == DamageType.MAGICAL) {
			reduction = amount * magicResistance;
		}
		else if(type == DamageType.RANGED) {
			reduction = amount * rangedResistance;
		}
		else if(type == DamageType.RANGED_MAGIC) {
			reduction = amount * ((rangedResistance + magicResistance) / 2);
		}
		else if(type == DamageType.PHYSICAL_MAGIC) {
			reduction = amount * ((physResistance + magicResistance) / 2);
		}else if(type == DamageType.PURE) {
			reduction = 0;
		}

		amount -= reduction;

		if(amount <= 1)
			amount = 2;
		
		int upperBound = (int)(amount - amount * .5f);
		if(upperBound <= 0)
			return 0;
		
		int actualDamage = TOCMain.rand.nextInt(upperBound) + (int)(amount*.5f);
		return actualDamage;
	}
	
	public float playerHurtPlayer(EntityPlayer attacker, EntityPlayer attacked, float amount, DamageSource source) {
		return calcEntityDamage(attacked, amount, source);
	}
	
	public float playerHurtEntity(EntityPlayer player, EntityLiving mob, float amount, DamageSource source) {
		return calcEntityDamage(mob, amount, source);
	}
	
	public float entityHurtPlayer(EntityPlayer player, EntityLiving mob, float amount, DamageSource source) {
		return calcEntityDamage(player, amount, source);
	}
	
	public float entityHurtAnother(EntityLiving attacker, EntityLiving attacked, float amount, DamageSource source) {
		return calcEntityDamage(attacked, amount, source);
	}
	
	public static Levels levelForHeldItem(EntityPlayer pl) {
		Levels level = Levels.ATTACK;
		if (pl.getHeldItemMainhand().getItem() instanceof TOCGreatSword) {
			level = Levels.ATTACK;
		} else if (pl.getHeldItemMainhand().getItem() instanceof TOCGreatAxe) {
			level = Levels.STRENGTH;
		} else if (pl.getHeldItemMainhand().getItem() instanceof TOCSword) {
			TOCSword sword = (TOCSword) pl.getHeldItemMainhand().getItem();
			level = sword.xpLeveled;
		} else if (pl.getHeldItemMainhand().getItem() instanceof TOCBow) {
			level = Levels.RANGED;
		} else if (pl.getHeldItemMainhand().getItem() instanceof BasicWand) {
			level = Levels.MAGIC;
		}
		return level;
	}
	
	public static Levels levelForDamageSource(TOCDamageSource src) {
		Levels level = Levels.ATTACK;
		if (src.type == DamageType.PHYSICAL) {
			EntityPlayer pl = (EntityPlayer) src.source;
			if (pl.getHeldItemMainhand().getItem() instanceof TOCGreatSword) {
				level = Levels.ATTACK;
			} else if (pl.getHeldItemMainhand().getItem() instanceof TOCGreatAxe) {
				level = Levels.STRENGTH;
			} else if (pl.getHeldItemMainhand().getItem() instanceof TOCSword) {
				TOCSword sword = (TOCSword) pl.getHeldItemMainhand().getItem();
				level = sword.xpLeveled;
			}
		} else if (src.type == DamageType.RANGED) {
			level = Levels.RANGED;
		} else if (src.type == DamageType.MAGICAL) {
			level = Levels.MAGIC;
		} else if (src.type == DamageType.PHYSICAL_MAGIC) {
		} else if (src.type == DamageType.RANGED_MAGIC) {
		}
		
		return level;
	}
	
	public static int getXpForEntity(EntityLivingBase e) {
		if(e instanceof CustomMob) {
			CustomMob m = (CustomMob) e;
			return m.xpGiven;
		}else {
			return TOCEntities.getXpFroVanillaMob(EntityRegistry.getEntry(e.getClass()).getRegistryName().toString());
		}
	}
	
	public static enum DamageType{
		PHYSICAL,
		MAGICAL,
		RANGED,
		PHYSICAL_MAGIC,
		RANGED_MAGIC, 
		PROCESSED, 
		PURE
	}
}
