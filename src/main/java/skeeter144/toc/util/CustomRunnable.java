package skeeter144.toc.util;

import skeeter144.toc.entity.mob.CustomMob;

public abstract interface CustomRunnable extends Runnable{
	
	public default void run() {
		run(null);
	}

	public abstract void run(Object mob);
	
}
