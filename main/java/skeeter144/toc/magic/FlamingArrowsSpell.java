package skeeter144.toc.magic;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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
			BlockPos pos = null;
			if (e != null) {
				pos = e.getPosition();
			} else {
				RayTraceResult rtr = Util.rayTrace(caster, 100, 1);
				if (rtr != null) {
					pos = rtr.getBlockPos();
				}
			}

			if (pos != null) {
				spawnArrows(caster, caster.world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
	}

	private void spawnArrows(Entity caster, World w, double x, double y, double z) {
		ItemArrow itemarrow = (ItemArrow) (Items.ARROW);
		ItemStack itemstack = new ItemStack(Items.ARROW);

		TOCMain.serverTaskManager.addTask(new TickableTask(200) {
			public void tick(int worldTick) {
				if (worldTick % 5 == 0) {
					EntityArrow entityarrow = itemarrow.createArrow(w, itemstack, (EntityLivingBase) caster);

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
					entityarrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
					w.spawnEntity(entityarrow);

					try {
						Field f = EntityArrow.class.getDeclaredField("ticksInGround");
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
