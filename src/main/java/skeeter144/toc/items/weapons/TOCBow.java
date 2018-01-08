package skeeter144.toc.items.weapons;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skeeter144.toc.Reference;
import skeeter144.toc.combat.CombatManager;

public class TOCBow extends ItemBow {

	int drawTicks;
	int baseDamage;
	float arrowMaxVel;
	public TOCBow(String name, int baseDamage, int pullTime, float arrowMaxVel) {
		super();
		this.setMaxDamage(-1);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		
		this.baseDamage = baseDamage;
		
		this.arrowMaxVel = arrowMaxVel;
		this.drawTicks = pullTime;
		setBowProps();
	}

	public static float getArrowVelocity(int charge, int drawDuration, float bonusVelocity) {
		float ticksForMaxCharge = drawDuration;
		
		float x = (float) charge / ticksForMaxCharge;
		x = MathHelper.clamp(x, 0, 1);
		
		bonusVelocity =  x * bonusVelocity;
		
		return x + bonusVelocity;
	}

	private ItemStack findAmmo(EntityPlayer player) {
		if (isArrow(player.getHeldItem(EnumHand.OFF_HAND))) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		} else if (isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (isArrow(itemstack)) {
					return itemstack;
				}
			}
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			boolean dontDecreseArrows = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack arrowStack = this.findAmmo(entityplayer);

			int ticksUsed = this.getMaxItemUseDuration(stack) - timeLeft;
			ticksUsed = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, ticksUsed, !arrowStack.isEmpty() || dontDecreseArrows);
			if (ticksUsed < 0)
				return;

			if (!arrowStack.isEmpty() || dontDecreseArrows) {
				if (arrowStack.isEmpty()) {
					arrowStack = new ItemStack(Items.ARROW);
				}

				float velocity = getArrowVelocity(ticksUsed, drawTicks, arrowMaxVel);
				if ((double) velocity >= 0.1D) {
					boolean flag1 = entityplayer.capabilities.isCreativeMode
							|| (arrowStack.getItem() instanceof ItemArrow && ((ItemArrow) arrowStack.getItem()).isInfinite(arrowStack, stack, entityplayer));

					if (!worldIn.isRemote) {
						ItemArrow itemarrow = (ItemArrow) (arrowStack.getItem() instanceof ItemArrow ? arrowStack.getItem() : Items.ARROW);
						EntityArrow entityarrow = itemarrow.createArrow(worldIn, arrowStack, entityplayer);
						
						entityarrow.setIsCritical(true);
						entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, velocity, 0);

						entityarrow.setDamage(((TOCBow)stack.getItem()).baseDamage + entityarrow.getDamage() + baseDamage + .5);
						entityarrow.setKnockbackStrength(0);

						worldIn.spawnEntity(entityarrow);
					}

					worldIn.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
							1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

					if (!flag1 && !entityplayer.capabilities.isCreativeMode) {
						arrowStack.shrink(1);

						if (arrowStack.isEmpty()) {
							entityplayer.inventory.deleteStack(arrowStack);
						}
					}

					entityplayer.addStat(StatList.getObjectUseStats(this));
				}
			}
		}
	}
	
	private void setBowProps() {
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if (entityIn == null) {
					return 0.0F;
				} else {
					return entityIn.getActiveItemStack().getItem() instanceof TOCBow ? (float) (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / drawTicks : 0.0F;
				}
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.YELLOW + "Base Damage:  " + (int)(baseDamage * CombatManager.LOWER_DAMAGE_PCT) +" - " + baseDamage);
		tooltip.add(TextFormatting.YELLOW + "Arrow Force:  " + (1 + arrowMaxVel));
		tooltip.add(TextFormatting.YELLOW + "Draw Time:    " + drawTicks / 20f);
	}

}
