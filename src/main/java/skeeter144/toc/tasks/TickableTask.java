package skeeter144.toc.tasks;

public abstract class TickableTask {
	
	public int duration;
	boolean cancelled = false;
	protected int start = 0;
	public TickableTask(int duration) {
		this.duration = duration;
	}
	
	public void setStartTick(int start) {
		this.start = start;
	}
	
	public abstract void tick(int worldTick);

	public void onStart() {}
	public void onEnd() {}
	
	public void cancel() {
		cancelled = true;
		duration = 0;
	}
}
