package skeeter144.toc.util;

public abstract interface CustomRunnable extends Runnable{
	
	public default void run() {
		run(null);
	}

	public abstract void run(Object mob);
	
}
