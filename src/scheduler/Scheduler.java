package scheduler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class Scheduler {
	
	protected String algorithmName;	
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
	
	public abstract void execute();
	
	public void tick() {
		timer++;
	}
	
	public int currentTime() {
		return timer;
	}
	
	public void printStatistics() {
		DecimalFormat df = new DecimalFormat("#.####");
		
		double avgWait;
		double avgResponse;
		double throughput;
		
		int auxSumWait = 0;
		int auxSumResponse = 0;
		
		for (Process p : finishedQueue) {
			auxSumWait += p.waitTime;
			auxSumResponse += p.responseTime;
		}
		
		avgWait = auxSumWait/finishedQueue.size();
		avgResponse = auxSumResponse/finishedQueue.size();
		
		throughput = (double)finishedQueue.size()/(double)currentTime();
		
		//System.out.println(finishedQueue.size());
		//System.out.println(currentTime());
		
		
		System.out.println("\n====================== STATISTICS: " + this.algorithmName + " ======================\n");
		System.out.println("Number of processes: " + finishedQueue.size());
		System.out.println("Average throughput: " + df.format(throughput));
		System.out.println("Average wait time: " + avgWait);
		System.out.println("Average response time: " + avgResponse);
		
		
	}
	
}
