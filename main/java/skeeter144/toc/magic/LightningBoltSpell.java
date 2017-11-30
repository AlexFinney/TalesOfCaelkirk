package skeeter144.toc.magic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
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
			BlockPos pos = null;
			if(e != null) {
				pos = e.getPosition();
			}else {
				RayTraceResult rtr = Util.rayTrace(caster, 200, 1);
				if(rtr != null) {
					pos = rtr.getBlockPos();
				}
			}
			
			if(pos != null) 
				spawnBolt(caster.world, pos.getX(), pos.getY(), pos.getZ(), new ArrayList<Entity>());
		}
	}
	
	
	
	
	private void spawnBolt(World w, double x, double y, double z, ArrayList<Entity> struckEntities) {
		EntityLightningBolt bolt = new EntityLightningBolt(w, x, y, z, false);
		w.addWeatherEffect(bolt);
		onBoltSpawn(bolt, struckEntities);
	}

	private void onBoltSpawn(EntityLightningBolt bolt, ArrayList<Entity> struckEntities) {
		int arcR = (int)LightningBoltSpell.ARC_RADIUS;
		
		AxisAlignedBB bb = new AxisAlignedBB(bolt.posX - arcR, bolt.posY - 3, bolt.posZ - arcR, bolt.posX + arcR, bolt.posY + 20, bolt.posZ + arcR);
		List<Entity> l = bolt.world.getEntitiesInAABBexcluding(bolt, bb, null);
		for(Entity e : l) {
			if(e instanceof EntityLivingBase || e instanceof EntityLiving && !struckEntities.contains(e)) {
				Thread t = new Thread(new Runnable() {
					public void run() {
						try {Thread.sleep(500);}
						catch (InterruptedException e) {e.printStackTrace();}
						
						bolt.getServer().addScheduledTask(new Runnable() {
							public void run() {
								spawnBolt(e.world, e.posX, e.posY, e.posZ, struckEntities);
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
