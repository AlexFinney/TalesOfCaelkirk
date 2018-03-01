package skeeter144.toc.particles.system;

import net.minecraft.world.World;

public abstract class ParticleSystem {
	
	protected double posX, posY, posZ;
	protected World world;
	protected int id;
	public ParticleSystem() {}
	
	double[] optionalParams;
	
	public ParticleSystem(World world, double x, double y, double z) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}

	public abstract void spawnParticles();
	
	/**
	 * Spawn paticles at the specified location without moving the original particle system
	 */
	public void spawnParticlesAt(World world, double x, double y, double z) {
		World prevWorld = this.world;
		double prevX = posX;
		double prevY = posY;
		double prevZ = posZ;
		updatePosition(world, x, y, z);
		spawnParticles();
		updatePosition(world, prevX, prevY, prevZ);
	}
	
	public void updatePosition(World world, double x, double y, double z) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}
	
	public int getId() {
		return id;
	}
	
	//include the inital systemid, posX, posY, etc
	public void setOptionalParams(double[] params) {
		this.optionalParams = params;
	}
	
	
	public static final int BASIC_SPELL_TRAIL_ID = 0; 
	public static final int TORNADO_SPELL_TRAIL_ID = 1; 
	public static final int TORNADO_SYSTEM = 2;
	public static final int WATER_RIPPLE_SPELL_TRAIL_ID = 3;
	public static final int EARTH_CRUMBLE_SPELL_TRAIL_ID = 4;
	public static final int FLAME_SMOLDER_SPELL_TRAIL_ID = 5; 
	public static final int HEAL_WOUNDS_EFFECT_ID = 6;
	public static final int PUNISH_UNDEAD_TRAIL_ID = 7;
	public static final int MANA_REGEN_EFFECT_ID = 8;
	public static final int ORE_MINING_PARTICLE_SYSTEM = 9;
	public static final int ORE_BROKEN_PARTICLE_SYSTEM = 10;
	public static final int ANVIL_STRUCK_PARTICLE_SYSTEM = 11;
	public static final int GHOST_TELEPORT_SYSTEM = 12;
	
	
	public static ParticleSystem getNewParticleSystem(int id) {
		switch (id) {
        	case BASIC_SPELL_TRAIL_ID:  
        		return new BasicSpellTrail(3, 1, .02f, 0xFFFFFF);
        	case TORNADO_SPELL_TRAIL_ID:  
        		return new TornadoSpellTrail();
        	case TORNADO_SYSTEM:
        		return new TornadoSystem();
        	case WATER_RIPPLE_SPELL_TRAIL_ID:
        		return new BasicSpellTrail(3, 1, .05f, 0x1F1FFF);
        	case EARTH_CRUMBLE_SPELL_TRAIL_ID:
        		return new BasicSpellTrail(3, 1, .05f, 0x7F4600);
        	case FLAME_SMOLDER_SPELL_TRAIL_ID:
        		return new FlameSmolderSpellTrail(3, 1.4f, .05f, 0xFF1F1F);
        	case HEAL_WOUNDS_EFFECT_ID:
        		return new HealEffectSystem();
        	case PUNISH_UNDEAD_TRAIL_ID:
        		return new PunishUndeadSystem();
        	case MANA_REGEN_EFFECT_ID:
        		return new ManaEffectSystem();
        	case ORE_MINING_PARTICLE_SYSTEM:
        		return new OreMiningParticleSystem();
        	case ORE_BROKEN_PARTICLE_SYSTEM:
        		return new OreBrokenParticleSystem();
        	case ANVIL_STRUCK_PARTICLE_SYSTEM:
        		return new AnvilStruckParticleSystem();
        	case GHOST_TELEPORT_SYSTEM:
        		return new GhostTeleportSystem();
		}
		return null;
	}
}
