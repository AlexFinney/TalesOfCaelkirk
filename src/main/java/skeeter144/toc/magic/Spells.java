package skeeter144.toc.magic;

import java.util.ArrayList;
import java.util.List;

import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.util.Strings;

public class Spells 
{
	public static List<Spell> spells;
	
	//elemental spells
	public static final Spell WIND_GUST = new WindGustSpell(Strings.WIND_GUST, "wind_gust.png", 5, 40, ParticleSystem.BASIC_SPELL_TRAIL_ID);
	public static final Spell WATER_RIPPLE = new WaterRippleSpell(Strings.WATER_RIPPLE, "water_ripple.png", 8, 40, ParticleSystem.WATER_RIPPLE_SPELL_TRAIL_ID);
	public static final Spell EARTH_CRUMBLE = new EarthCrumbleSpell(Strings.EARTH_CRUMBLE, "earth_crumble.png", 13, 40, ParticleSystem.EARTH_CRUMBLE_SPELL_TRAIL_ID);
	public static final Spell FLAME_SMOLDER = new FlameSmolderSpell(Strings.FLAME_SMOLDER, "flame_smolder.png", 20, 40, ParticleSystem.FLAME_SMOLDER_SPELL_TRAIL_ID);
	
	//special spells
	public static final Spell HEAL_WOUNDS = new HealWoundsSpell(Strings.HEAL_WOUNDS, "heal_wounds.png", 200);
	public static final Spell TORNADO = new TornadoSpell(Strings.TORNADO, "wind_cyclone.png", ParticleSystem.TORNADO_SPELL_TRAIL_ID);
	public static final Spell LIGHTNING_BOLT = new LightningBoltSpell(Strings.LIGHTNING_BOLT, "lightning_bolt.png", 100);
	public static final Spell PUNISH_UNDEAD = new PunishUndeadSpell(Strings.PUNISH_UNDEAD, "punish_undead.png", 40, ParticleSystem.PUNISH_UNDEAD_TRAIL_ID);
	public static final Spell FLAMING_ARROWS = new FlamingArrowsSpell(Strings.FLAMING_ARROWS, "flaming_arrows.png", 40);
	public static final Spell ZONE_ENTANGLE = new ZoneEntangleSpell(Strings.ZONE_ENTANGLE, "zone_entangle.png", 40, ParticleSystem.BASIC_SPELL_TRAIL_ID);
	public static final Spell PHASE_TELEPORT = new PhaseTeleportSpell(Strings.PHASE_TELEPORT, "phase_teleport.png", 40, ParticleSystem.BASIC_SPELL_TRAIL_ID);
	public static final Spell TURN_CHICKEN = new TurnChickenSpell(Strings.TURN_CHICKEN, "turn_chicken.png", 40, ParticleSystem.BASIC_SPELL_TRAIL_ID);
	public static final Spell SUMMON_WALL = new SummonWallSpell(Strings.SUMMON_WALL, "summon_wall.png", 40, ParticleSystem.BASIC_SPELL_TRAIL_ID);
	
	//the order they are registered in determines their order in the spell book GUI
	public static void init() {
		registerSpell(WIND_GUST);
		registerSpell(WATER_RIPPLE);
		registerSpell(EARTH_CRUMBLE);
		registerSpell(FLAME_SMOLDER);
		
		registerSpell(TORNADO);
		registerSpell(HEAL_WOUNDS);
		registerSpell(LIGHTNING_BOLT);
		registerSpell(PUNISH_UNDEAD);
		registerSpell(FLAMING_ARROWS);
		registerSpell(ZONE_ENTANGLE);
		registerSpell(PHASE_TELEPORT);
		registerSpell(TURN_CHICKEN);
		registerSpell(SUMMON_WALL);
	}
	
	
	private static void registerSpell(Spell spell) {
		if(spells == null) {
			spells = new ArrayList<Spell>();
		}
		spells.add(spell);
		spell.id = spells.size() - 1;
	}
	
	public static Spell getSpell(int id) {
		if(id < spells.size())
			return spells.get(id);
		else
			return null;
	}
	
	public static Spell getSpellByName(String name) {
		for(Spell s : spells) {
			if(s.getName().equals(name))
				return s;
		}
		return null;
	}
	
	static int id = 0;
	static int getNextSpellId() {
		return id++;
	}
	
	
	
}
