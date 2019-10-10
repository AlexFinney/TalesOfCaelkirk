package skeeter144.toc.items.weapons;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.entity.projectile.EntityTOCArrow;
import skeeter144.toc.util.Reference;

public class TOCBow extends ItemBow {

	int drawTicks;
	int baseDamage;
	float arrowMaxVel;
	public TOCBow(Item.Properties builder, String name, int baseDamage, int pullTime, float arrowMaxVel) {
		super(builder);
		setRegistryName(Reference.MODID, name);
		setBowProps();
	}

	public static float getArrowVelocity(int charge, int drawDuration, float bonusVelocity) {
		float ticksForMaxCharge = drawDuration;
		
		float x = (float) charge / ticksForMaxCharge;
		x = MathHelper.clamp(x, 0, 1);
		
		bonusVelocity =  x * bonusVelocity;
		
		return x + bonusVelocity;
	}

	protected ItemStack findAmmo(EntityPlayer player) {
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
			boolean dontDecreseArrows = entityplayer.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack arrowStack = this.findAmmo(entityplayer);

			int ticksUsed = this.getUseDuration(stack) - timeLeft;
			ticksUsed = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, ticksUsed, !arrowStack.isEmpty() || dontDecreseArrows);
			if (ticksUsed < 0)
				return;

			if (!arrowStack.isEmpty() || dontDecreseArrows) {
				if (arrowStack.isEmpty()) {
					arrowStack = new ItemStack(Items.ARROW);
				}

				float velocity = getArrowVelocity(ticksUsed, drawTicks, arrowMaxVel);
				if ((double) velocity >= 0.1D) {
					boolean flag1 = entityplayer.abilities.isCreativeMode
							|| (arrowStack.getItem() instanceof ItemArrow && ((ItemArrow) arrowStack.getItem()).isInfinite(arrowStack, stack, entityplayer));

					if (!worldIn.isRemote) {
						TOCArrow itemarrow = (TOCArrow) (arrowStack.getItem() instanceof ItemArrow ? arrowStack.getItem() : Items.ARROW);
						EntityTOCArrow entityarrow = (EntityTOCArrow) itemarrow.createArrow(worldIn, arrowStack, entityplayer);
						entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, velocity, 0);

						System.out.println("Bow total damage: " + (((TOCBow)stack.getItem()).baseDamage + itemarrow.damage));
						
						entityarrow.setDamage(((TOCBow)stack.getItem()).baseDamage + itemarrow.damage);
						System.out.println("Arrow damage: " + entityarrow.getDamage());
						entityarrow.setKnockbackStrength(0);

						worldIn.spawnEntity(entityarrow);
					}

					worldIn.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
							1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

					if (!flag1 && !entityplayer.abilities.isCreativeMode) {
						arrowStack.shrink(1);

						if (arrowStack.isEmpty()) {
							entityplayer.inventory.deleteStack(arrowStack);
						}
					}

					//entityplayer.addStat(StatList.getObjectUseStats(this));
				}
			}
		}
	}
	
	private void setBowProps() {
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@OnlyIn(Dist.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if (entityIn == null) {
					return 0.0F;
				} else {
					return entityIn.getActiveItemStack().getItem() instanceof TOCBow ? (float) (stack.getUseDuration() - entityIn.getItemInUseCount()) / drawTicks : 0.0F;
				}
			}

			@Override
			public float call(ItemStack p_call_1_, World p_call_2_, EntityLivingBase p_call_3_) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@OnlyIn(Dist.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}

			@Override
			public float call(ItemStack p_call_1_, World p_call_2_, EntityLivingBase p_call_3_) {
				return 0;
			}
		});
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Base Damage:  " + (int)(baseDamage * CombatManager.LOWER_DAMAGE_PCT) +" - " + baseDamage));
		tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Arrow Force:  " + (1 + arrowMaxVel)));
		tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Draw Time:    " + drawTicks / 20f));
	}

}
