package scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Scheduler {
	
	protected ArrayList<Process> processList;
	protected ArrayList<Process> readyQueue;
	protected ArrayList<Process> finishedQueue;
	protected int timer;
	protected boolean running;
	
	public Scheduler() {
		
		processList = new ArrayList<>();
		readyQueue = new ArrayList<>();
		finishedQueue = new ArrayList<>();
		timer = 0;
		running = false;
		
	}
	
	public Scheduler(ArrayList<Process> processList) {
		
		this();
		for (Process p: processList)
			this.processList.add(p);
		
		// For the all the algorithms, we have to sort our list of processes by the arrivalTime
		Collections.sort(this.processList, new Comparator<Process>() {
			public int compare(Process a, Process b) {
				if (a.arrivalTime >= b.arrivalTime)
					return 1;
				else
					return -1;
			}
		});
		
	}
	
	public void tick() {
		timer++;
	}
	
	public int currentTime() {
		return timer;
	}
	
}
