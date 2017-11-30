package skeeter144.toc.regions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

public class RegionManager implements Serializable{
	
	public static final long serialVersionUID = -687991492884005033L;
	
	Map<String, Region> regions;
	Map<Integer, Boolean> defaultRules;
	Map<UUID, Pair<Point, Point>> playersSelectedPoints;
	public transient Map<UUID, Set<Region>> playerRegions = new HashMap<UUID, Set<Region>>();
	public RegionManager() {
		regions = new HashMap<String, Region>();
		defaultRules = new HashMap<Integer, Boolean>();
		playersSelectedPoints = new HashMap<UUID, Pair<Point, Point>>();
		playerRegions = new HashMap<UUID, Set<Region>>();
		
		defaultRules.put(RegionRules.PVP, false);
		defaultRules.put(RegionRules.SAFE_ZONE, false);
		defaultRules.put(RegionRules.MAP_ALLOWED, true);
	}
	
	public Boolean getDefaultRule(int id) {
		return defaultRules.get(id);
	}
	
	public Region getRegion(String name) {
		return regions.get(name);
	}
	
	public void addBoundsToRegion(String name, Point p1, Point p2) {
		Region r = regions.get(name);
		if(r == null) {
			r = new Region(name);
		}
		r.bounds.add(new RegionBound(p1, p2));
		regions.put(name, r);
	}
	
	public Pair<Point, Point> getPlayersPoints(UUID uuid){
		return playersSelectedPoints.get(uuid);
	}
	
	public void setPlayersPoints(UUID uuid, Pair<Point, Point> points) {
		playersSelectedPoints.put(uuid, points);
	}
	
	public Map<String, Region> getRegions(){
		return regions;
	}
	
	public void setRegions(Map<String, Region> regions){
		this.regions = regions;
	}
}
