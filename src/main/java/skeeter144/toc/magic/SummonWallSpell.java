package skeeter144.toc.magic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.TOCEntityType;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.tasks.TickableTask;

public class SummonWallSpell extends ShootableSpell{

	public SummonWallSpell(String name, String iconName, int cooldown, int trailId) {
		super(TOCEntityType.SUMMON_WALL, name, iconName, cooldown, trailId);
	}

	@Override
	public void onProjectileImpact(RayTraceResult res, EntityWandProjectile proj) {
		if(proj.world.isRemote)
			return;
		proj.getThrower().noClip = true;

		if(res instanceof EntityRayTraceResult)
			return;
		
		BlockRayTraceResult blockRay = (BlockRayTraceResult)res;
		
		if(proj.world.getBlockState(new BlockPos(res.getHitVec())) == Blocks.AIR.getDefaultState())
			return;
		
		final Block[] blocks = new Block[] {Blocks.STONE_BRICKS, Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE};
		TOCMain.serverTaskManager.addTask(new TickableTask(500) {
			int wallWidth = 18;
			int wallHeight = 6;
			float blockChance = .9f;
			List<BlockPos> block = new ArrayList<BlockPos>();
			
			public void tick(int worldTick) {
				if(worldTick % 10 == 0) {
					Direction wallDirection = blockRay.getFace();
					BlockPos genPos = new BlockPos(blockRay.getPos());
					
					if(wallDirection == Direction.UP) {
						wallDirection = proj.getHorizontalFacing();
						wallDirection = wallDirection.rotateY();
						genPos = genPos.add(0, 3, 0);
					}else if(wallDirection == Direction.DOWN) {
						wallDirection = proj.getHorizontalFacing();
						genPos = genPos.add(0, -3, 0);
						wallDirection = wallDirection.rotateY();
					}
					
					for(int i = 0; i < 8; ++i) {
						int xOff = TOCMain.rand.nextInt(wallWidth);
						int yOff = TOCMain.rand.nextInt(wallHeight);
						int zOff = TOCMain.rand.nextInt(wallWidth);
								
						BlockPos newPos = genPos.add(xOff * wallDirection.getDirectionVec().getX(), yOff - wallHeight / 2, zOff * wallDirection.getDirectionVec().getZ());
						if(proj.world.getBlockState(newPos).equals(Blocks.AIR.getDefaultState())) {
							proj.world.setBlockState(newPos, blocks[TOCMain.rand.nextInt(blocks.length)].getDefaultState());
							block.add(newPos);
						}
					}
				}
			}
			
			public void onEnd() {
				TOCMain.serverTaskManager.addTask(new TickableTask(500) {
					public void tick(int worldTick) {
						if(worldTick % 10 == 0) {
							for(int x = 0; x < 3; ++x) {
								if(block.size() == 0)
									break;
								int i = TOCMain.rand.nextInt(block.size());
								BlockPos p = block.get(i);
								proj.getEntityWorld().setBlockState(p, Blocks.AIR.getDefaultState());
								block.remove(i);
							}
						}
					}
				});
			}
		});
	}
	

	@Override
	public int getManaCost() {
		return 5;
	}

	@Override
	public void performSpellAction(Entity caster) {}

}
