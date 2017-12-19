package skeeter144.toc.regions;

import java.io.Serializable;

public class Point implements Serializable {
	
	public final int x;
	public final int z;
	public Point(int x, int z) {
		this.x = x;
		this.z = z;
	}
}
