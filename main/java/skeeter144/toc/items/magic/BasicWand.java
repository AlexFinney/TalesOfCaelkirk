package skeeter144.toc.items.magic;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.projectile.EntityWandProjectile;
import skeeter144.toc.magic.ShootableSpell;
import skeeter144.toc.magic.Spell;
import skeeter144.toc.magic.Spells;
import skeeter144.toc.particles.system.ParticleSystem;
import skeeter144.toc.particles.system.PunishUndeadSystem;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.Strings;

public class BasicWand extends Item
{
	public BasicWand(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Reference.MODID, name));
		setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.COMBAT);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		Spell embuedSpell = null;
		
		if(player.getHeldItem(hand).getTagCompound() == null) {
			player.getHeldItem(hand).setTagCompound(new NBTTagCompound());
			player.getHeldItem(hand).getTagCompound().setInteger("embued_spell", 0);
		}

		embuedSpell = Spells.getSpell(player.getHeldItem(hand).getTagCompound().getInteger("embued_spell"));
		
		if(embuedSpell == null) {
			return new ActionResult(EnumActionResult.FAIL, player.getHeldItem(hand));
		}
		
		
		if(!world.isRemote) //on the server
		{
			TOCPlayer tocPlayer = TOCMain.pm.getPlayer(player.getPersistentID());
			if(tocPlayer.getMana() >= embuedSpell.getManaCost() || player.isCreative()) {
				embuedSpell.onCast(player);
				player.getCooldownTracker().setCooldown(this, embuedSpell.getCooldown());
			}
			
		}else {
			if(TOCMain.localPlayer.getMana() < embuedSpell.getManaCost() && !Minecraft.getMinecraft().player.isCreative()) {
				player.sendMessage(new TextComponentString("You do not have enough mana to cast " + embuedSpell.getName() + " (need " + embuedSpell.getManaCost() + ")"));
				return new ActionResult(EnumActionResult.FAIL, player.getHeldItem(hand));
			}
			
			embuedSpell.onCast(player);;
			if(embuedSpell instanceof ShootableSpell) {
				ShootableSpell es = (ShootableSpell)embuedSpell;
				ParticleSystem system = ParticleSystem.getNewParticleSystem(es.getSpellTrailId());
				system.updatePosition(world, player.posX, player.posY, player.posZ);
					
				EntityWandProjectile spell = new EntityWandProjectile(world, player, embuedSpell.getId(), system);
				spell.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0, 2.0f, 0);
				
				
				//do special stuff for special spells down here. Kinda ugly but whatevs
				if(embuedSpell.getName().equals(Strings.PUNISH_UNDEAD)) {
					spell.disableRepeatedParticles();
					((PunishUndeadSystem)system).setForwardVector(Minecraft.getMinecraft().player.getLookVec());
					
				}
				
				player.world.spawnEntity(spell);
			}
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
	}
}




