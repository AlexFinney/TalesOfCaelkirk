package skeeter144.toc.magic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import skeeter144.toc.TOCMain;
import skeeter144.toc.util.Util;

public class LightningBoltSpell extends Spell {

	public static final float ARC_CHANCE = .5f;
	public static final float ARC_RADIUS = 10;
	
	public LightningBoltSpell(String name, String iconName, int cooldown) {
		super(name, iconName, cooldown);
	}

	@Override
	public int getManaCost() {
		return 20;
	}

	@Override
	public void performSpellAction(Entity caster){
		if(!caster.world.isRemote) {
			Entity e = Util.getPointedEntity(caster, 1, 100);
			Vec3d pos = null;
			if(e != null) {
				pos = new Vec3d(e.getPosition());
			}else {
				RayTraceResult rtr = Util.rayTrace(caster, 200, 1);
				if(rtr != null) {
					pos = rtr.getHitVec();
				}
			}
			
			if(pos != null) 
				spawnBolt(caster.world, pos.getX(), pos.getY(), pos.getZ(), new ArrayList<Entity>());
		}
	}
	
	
	
	
	private void spawnBolt(World w, double x, double y, double z, ArrayList<Entity> struckEntities) {
		LightningBoltEntity bolt = new LightningBoltEntity(w, x, y, z, false);
		((ServerWorld)w).addLightningBolt(bolt);
		onBoltSpawn(bolt, struckEntities);
	}

	private void onBoltSpawn(LightningBoltEntity bolt, ArrayList<Entity> struckEntities) {
		int arcR = (int)LightningBoltSpell.ARC_RADIUS;
		
		AxisAlignedBB bb = new AxisAlignedBB(bolt.getPosX() - arcR, bolt.getPosY() - 3, bolt.getPosZ() - arcR, bolt.getPosX() + arcR, bolt.getPosY() + 20, bolt.getPosZ() + arcR);
		List<Entity> l = bolt.world.getEntitiesInAABBexcluding(bolt, bb, null);
		for(Entity e : l) {
			if(e instanceof LivingEntity || e instanceof LivingEntity && !struckEntities.contains(e)) {
				Thread t = new Thread(new Runnable() {
					public void run() {
						try {Thread.sleep(500);}
						catch (InterruptedException e) {e.printStackTrace();}
						
						bolt.getServer().deferTask(new Runnable() {
							public void run() {
								spawnBolt(e.world, e.getPosX(), e.getPosY(), e.getPosZ(), struckEntities);
							}
						});
					}
				});
				
				if(TOCMain.rand.nextDouble() < LightningBoltSpell.ARC_CHANCE) {
					struckEntities.add(e);
					t.start();
					break;
				}
			}
		}
	}
}
