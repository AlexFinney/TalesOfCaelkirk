package skeeter144.toc.magic;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.tasks.TickableTask;
import skeeter144.toc.util.Util;

public class FlamingArrowsSpell extends Spell {

	public FlamingArrowsSpell(String name, String iconName, int cooldown) {
		super(name, iconName, cooldown);
	}

	@Override
	public int getManaCost() {
		return 5;
	}

	@Override
	public void performSpellAction(Entity caster) {
		if (!caster.world.isRemote) {
			Entity e = Util.getPointedEntity(caster, 1, 100);
			Vec3d pos = null;
			if(e == null) {
				RayTraceResult rtr = Util.rayTrace(caster, 100, 1);
				if(rtr instanceof EntityRayTraceResult) {
					e = ((EntityRayTraceResult)rtr).getEntity();
					pos = new Vec3d(e.getPosition());
				}else {
					pos = rtr.getHitVec();
				}
			}

			if (pos != null) {
				spawnArrows(caster, caster.world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
	}

	private void spawnArrows(Entity caster, World w, double x, double y, double z) {
		ArrowItem itemarrow = (ArrowItem) (Items.ARROW);
		ItemStack itemstack = new ItemStack(Items.ARROW);

		TOCMain.serverTaskManager.addTask(new TickableTask(200) {
			public void tick(int worldTick) {
				if (worldTick % 5 == 0) {
					AbstractArrowEntity entityarrow = itemarrow.createArrow(w, itemstack, (LivingEntity) caster);

					Random r = TOCMain.rand;
					BlockPos target = new BlockPos(x + r.nextFloat() - .5f, y + 1, z + r.nextFloat() - .5f);
					BlockPos arrowPos = new BlockPos(x + (r.nextFloat() * 15) * (r.nextBoolean() ? 1 : -1), y + TOCMain.rand.nextInt(15) + 5,
							z + (r.nextFloat() * 15) * (r.nextBoolean() ? 1 : -1));

					float xP = target.getX() - arrowPos.getX();
					float zP = target.getZ() - arrowPos.getZ();

					entityarrow.shoot(target.getX() - arrowPos.getX(),
													target.getY() + 1 - arrowPos.getY(), 
													target.getZ() - arrowPos.getZ(), 
													2, 0);

					entityarrow.setPosition(arrowPos.getX(), arrowPos.getY(), arrowPos.getZ());
					entityarrow.setFire(3);
					entityarrow.setDamage(30);
					entityarrow.pickupStatus = ArrowEntity.PickupStatus.DISALLOWED;
					w.addEntity(entityarrow);

					try {
						Field f = ArrowEntity.class.getDeclaredField("ticksInGround");
						f.setAccessible(true);
						f.set(entityarrow, 1100);
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
							| IllegalAccessException e) {
						e.printStackTrace();
					}

				}
			}
		});
	}
}
