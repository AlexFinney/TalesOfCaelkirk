package skeeter144.toc.regions;

import java.io.Serializable;

public class RegionBound implements Serializable {
	public static final long serialVersionUID = -687991492884005035L;
	public final int x1,z1,x2,z2;
	public RegionBound(Point p1, Point p2) {
		this.x1 = p1.x;
		this.z1 = p1.z;
		this.x2 = p2.x;
		this.z2 = p2.z;
	}
	
	public boolean containsPoint(int x, int z) {
		return x >= x1 && x <= x2 && z >= z1 && z <= z2;
	}
	
	public boolean containsPoint(Point p) {
		return p.x >= x1 && p.x <= x2 && p.z >= z1 && p.z <= z2;
	}
}
