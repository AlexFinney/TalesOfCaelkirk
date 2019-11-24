package skeeter144.toc.regions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.LivingEntity;
import skeeter144.toc.TOCMain;
import skeeter144.toc.util.CustomRunnable;

public class Region implements Serializable{
	public static final long serialVersionUID = -687991492884005034L;
	
	public final Map<Integer, Boolean> rules;
	public final List<RegionBound> bounds;
	public final float r, g, b;
	public final String name;
	CustomRunnable onRegionEntered, onRegionExited;
	public Region(String name) {
		rules = new HashMap<Integer, Boolean>();
		bounds = new ArrayList<RegionBound>();
		this.name = name;
		r = TOCMain.rand.nextFloat();
		g = TOCMain.rand.nextFloat();
		b = TOCMain.rand.nextFloat();
	}
	
	public Boolean getRuleValueFor(int id) {
		if(rules.get(id) == null) {
			return TOCMain.rm.getDefaultRule(id);
		}
		return rules.get(id);
	}
	
	public void onRegionEntered(LivingEntity e) {
		Class c = RegionCallbacks.class;
		try {
			c.getMethod((name + "Entered"), LivingEntity.class).invoke(null, e);
		} catch (Exception e1) {
			System.out.println("Warning: No Method for region enter for " + name);
		}
	}
	
	public void onRegionExited(LivingEntity e) {
		Class c = RegionCallbacks.class;
		try {
			c.getMethod((name + "Exited"), LivingEntity.class).invoke(null, e);
		} catch (Exception e1) {
			System.out.println("Warning: No Method for region exit for " + name);
		}
	}
	
	public void onRegionTick(LivingEntity e) {
		Class c = RegionCallbacks.class;
		try {
			c.getMethod((name + "Tick"), LivingEntity.class).invoke(null, e);
		} catch (Exception e1) {
			System.out.println("Warning: No Method for region tick for " + name);
		}
	}
	
	public void setOnRegionEntered(CustomRunnable r) {
		this.onRegionEntered = r;
	}
	
	public void setOnRegionExited(CustomRunnable r) {
		this.onRegionExited = r;
	}
}
