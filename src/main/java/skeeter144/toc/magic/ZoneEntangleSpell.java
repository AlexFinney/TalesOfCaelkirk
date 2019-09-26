package skeeter144.toc.magic;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.tasks.TickableTask;

public class ZoneEntangleSpell extends ShootableSpell{
	public ZoneEntangleSpell(String name, String iconName, int cooldown, int trailId) {
		super(name, iconName, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		if(proj.world.isRemote)
			return;
		
		ArrayList<BlockPos> webs = new ArrayList<BlockPos>();
		World w = proj.world;
		for(int i = -2; i < 2; ++i) {
			for(int j = -2; j < 2; ++j) {
				for(int k = 0; k < 3; ++k) {
					if(TOCMain.rand.nextFloat() < .3f) {
						BlockPos pos = new BlockPos(res.hitVec.x + i,  res.hitVec.y + k, res.hitVec.z + j);
						if(proj.world.getBlockState(pos).equals(Blocks.AIR.getDefaultState())) {
							webs.add(pos);
							proj.world.setBlockState(pos, Blocks.COBWEB.getDefaultState());
						}
					}
				}
			}
		}
		TOCMain.serverTaskManager.addTask(new TickableTask(200) {
			public void tick(int worldTick) {}
			public void onEnd() {
				for(BlockPos p : webs) {
					w.setBlockState(p, Blocks.AIR.getDefaultState());
				}
			}
		});
	}

	@Override
	public int getSpellTrailId() {
		return this.trailId;
	}

	@Override
	public int getManaCost() {
		return 5;
	}

	@Override
	public void performSpellAction(Entity caster) {}
}
