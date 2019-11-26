package skeeter144.toc.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

	List<TickableTask> tasks;
	public TaskManager() {
		tasks = new ArrayList<TickableTask>();
	}
	
	int currentTick = 0;
	public void addTask(TickableTask t){
		tasks.add(t);
		t.setStartTick(currentTick);
		t.onStart();
	}
	
	public void tickTasks() {
		currentTick++;
		for(int i = tasks.size() - 1; i >= 0; --i) {
			TickableTask task = tasks.get(i);
			task.tick(currentTick);
			if(task.cancelled || task.duration <= 0) {
				tasks.get(i).onEnd();
				tasks.remove(i);
			}
		}
	}
}
