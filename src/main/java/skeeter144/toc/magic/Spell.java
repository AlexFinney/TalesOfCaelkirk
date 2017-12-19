package skeeter144.toc.magic;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.TOCMain;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.util.Reference;

public abstract class Spell {
	int manaCost;
	int levelRequirement;
	
	protected int id;
	protected String name;
	public final ResourceLocation icon;
	int cooldown = 0;
	
	public Spell(String name, String iconName, int cooldown) {
		this.name = name;
		icon = new ResourceLocation(Reference.MODID, "textures/spells/" + iconName);
		this.cooldown = cooldown;
	}
	
	public void onCast(Entity caster) {
		if(!caster.world.isRemote) {
			TOCPlayer tocCaster = TOCMain.pm.getPlayer(caster.getPersistentID());
			tocCaster.adjustVitals(0, -manaCost);
			performSpellAction(caster);
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
