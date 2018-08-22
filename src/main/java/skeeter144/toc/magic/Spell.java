package skeeter144.toc.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.util.Reference;

public abstract class Spell {
	int levelRequirement;
	
	protected int id;
	protected String name;
	public final ResourceLocation icon;
	int cooldown = 0;
	public SoundEvent sound = null;
	
	public Spell(String name, String iconName, int cooldown) {
		this.name = name;
		icon = new ResourceLocation(Reference.MODID, "textures/spells/" + iconName);
		this.cooldown = cooldown;
	}
	
	public void onCast(Entity caster) {
		if(!caster.world.isRemote) {
			TOCPlayer tocCaster = TOCMain.pm.getPlayer((EntityPlayer) caster);
			tocCaster.adjustVitals(0, -getManaCost());
			performSpellAction(caster);
			if(this.sound != null && caster instanceof EntityPlayer) {
				caster.world.playSound(null, caster.getPosition(), Sounds.spell_wind_gust, SoundCategory.MASTER, 1, 1);
			}
		}
	}
	
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	
	public int getCooldown() {
		return cooldown;
	}
	
	public abstract int getManaCost();
	public abstract void performSpellAction(Entity caster);
}
