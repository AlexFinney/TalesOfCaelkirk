package skeeter144.toc.items.weapons;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skeeter144.toc.combat.CombatManager;
import skeeter144.toc.entity.projectile.EntityTOCArrow;
import skeeter144.toc.util.Reference;

public class TOCBow extends BowItem {

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

	protected ItemStack findAmmo(PlayerEntity player) {
		if (isArrowStack(player.getHeldItem(Hand.OFF_HAND))) {
			return player.getHeldItem(Hand.OFF_HAND);
		}else {
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (isArrowStack(itemstack)) {
					return itemstack;
				}
			}
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity PlayerEntity = (PlayerEntity) entityLiving;
			boolean dontDecreseArrows = PlayerEntity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack arrowStack = this.findAmmo(PlayerEntity);

			int ticksUsed = this.getUseDuration(stack) - timeLeft;
			ticksUsed = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, PlayerEntity, ticksUsed, !arrowStack.isEmpty() || dontDecreseArrows);
			if (ticksUsed < 0)
				return;

			if (!arrowStack.isEmpty() || dontDecreseArrows) {
				if (arrowStack.isEmpty()) {
					arrowStack = new ItemStack(Items.ARROW);
				}

				float velocity = getArrowVelocity(ticksUsed, drawTicks, arrowMaxVel);
				if ((double) velocity >= 0.1D) {
					boolean flag1 = PlayerEntity.abilities.isCreativeMode
							|| (arrowStack.getItem() instanceof ArrowItem && ((ArrowItem) arrowStack.getItem()).isInfinite(arrowStack, stack, PlayerEntity));

					if (!worldIn.isRemote) {
						TOCArrow itemarrow = (TOCArrow) (arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);
						EntityTOCArrow entityarrow = (EntityTOCArrow) itemarrow.createArrow(worldIn, arrowStack, PlayerEntity);
						entityarrow.shoot(PlayerEntity, PlayerEntity.rotationPitch, PlayerEntity.rotationYaw, 0.0F, velocity, 0);

						System.out.println("Bow total damage: " + (((TOCBow)stack.getItem()).baseDamage + itemarrow.damage));
						
						entityarrow.setDamage(((TOCBow)stack.getItem()).baseDamage + itemarrow.damage);
						System.out.println("Arrow damage: " + entityarrow.getDamage());
						entityarrow.setKnockbackStrength(0);

						worldIn.addEntity(entityarrow);
					}

					worldIn.playSound((PlayerEntity) null, PlayerEntity.getPosX(), PlayerEntity.getPosY(), PlayerEntity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
							1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

					if (!flag1 && !PlayerEntity.abilities.isCreativeMode) {
						arrowStack.shrink(1);

						if (arrowStack.isEmpty()) {
							PlayerEntity.inventory.deleteStack(arrowStack);
						}
					}

					//PlayerEntity.addStat(StatList.getObjectUseStats(this));
				}
			}
		}
	}
	
	private void setBowProps() {
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@OnlyIn(Dist.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
				if (entityIn == null) {
					return 0.0F;
				} else {
					return entityIn.getActiveItemStack().getItem() instanceof TOCBow ? (float) (stack.getUseDuration() - entityIn.getItemInUseCount()) / drawTicks : 0.0F;
				}
			}

			@Override
			public float call(ItemStack p_call_1_, World p_call_2_, LivingEntity p_call_3_) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@OnlyIn(Dist.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}

			@Override
			public float call(ItemStack p_call_1_, World p_call_2_, LivingEntity p_call_3_) {
				return 0;
			}
		});
	}
	
	private boolean isArrowStack(ItemStack is) {
		return is.getItem() instanceof ArrowItem;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Base Damage:  " + (int)(baseDamage * CombatManager.LOWER_DAMAGE_PCT) +" - " + baseDamage));
		tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Arrow Force:  " + (1 + arrowMaxVel)));
		tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "Draw Time:    " + drawTicks / 20f));
	}

}
