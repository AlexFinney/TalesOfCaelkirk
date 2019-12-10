package skeeter144.toc.items.magic;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.items.CustomItem;
import skeeter144.toc.magic.ShootableSpell;
import skeeter144.toc.magic.Spell;
import skeeter144.toc.magic.Spells;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.particles.system.PunishUndeadSystem;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.util.Strings;

public class BasicWand extends CustomItem
{
	public BasicWand(Item.Properties properties, String name)
	{
		super(properties, name, 1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		Spell embuedSpell = null;
		
		if(player.getHeldItem(hand).getTag() == null) {
			player.getHeldItem(hand).setTag(new CompoundNBT());
			player.getHeldItem(hand).getTag().putInt("embued_spell", 0);
		}

		embuedSpell = Spells.getSpell(player.getHeldItem(hand).getTag().getInt("embued_spell"));
		
		if(embuedSpell == null) {
			return new ActionResult<ItemStack>(ActionResultType.FAIL, player.getHeldItem(hand));
		}
		
		
		if(!world.isRemote) //on the server
		{
			TOCPlayer tocPlayer = TOCMain.pm.getPlayer(player);
			if(tocPlayer.getMana() >= embuedSpell.getManaCost() || player.isCreative()) {
				embuedSpell.onCast(player);
				player.getCooldownTracker().setCooldown(this, embuedSpell.getCooldown());
			}
			
		}else {
			if(TOCMain.localPlayer.getMana() < embuedSpell.getManaCost() && !Minecraft.getInstance().player.isCreative()) {
				player.sendMessage(new StringTextComponent("You do not have enough mana to cast " + embuedSpell.getName() + " (need " + embuedSpell.getManaCost() + ")"));
				return new ActionResult<ItemStack>(ActionResultType.FAIL, player.getHeldItem(hand));
			}
			
			embuedSpell.onCast(player);;
			if(embuedSpell instanceof ShootableSpell) {
				ShootableSpell es = (ShootableSpell)embuedSpell;
				ParticleSystem system = ParticleSystem.getNewParticleSystem(es.getSpellTrailId());
				system.updatePosition(world, player.posX, player.posY, player.posZ);
					
				EntityWandProjectile spell = new EntityWandProjectile(es.entityType, world, player, embuedSpell.getId(), system);
				spell.shoot(player, player.rotationPitch, player.rotationYaw, 0, 3.0f, 0);
				
				//do special stuff for special spells down here. Kinda ugly but whatevs.
				// Later comment: no not whatevs. this is shit. fix this at some point
				if(embuedSpell.getName().equals(Strings.PUNISH_UNDEAD)) {
					spell.disableRepeatedParticles();
					((PunishUndeadSystem)system).setForwardVector(Minecraft.getInstance().player.getLookVec());
					
				}
				
				player.world.addEntity(spell);
			}
		}
		
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		
	}
}




