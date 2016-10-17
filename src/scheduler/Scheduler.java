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
	protected int runningTime;
	protected boolean running;
	
	public Scheduler() {
		
		processList = new ArrayList<>();
		readyQueue = new ArrayList<>();
		finishedQueue = new ArrayList<>();
		timer = 0;
		runningTime = 0;
		running = false;
		
	}
	
	public Scheduler(ArrayList<Process> processList) {
		
		this();
		for (Process p: processList)
			this.processList.add(p);
		
		// For the all the algorithms, we have to sort our list of processes by the arrivalTime
		Collections.sort(this.processList, new Comparator<Process>() {
			public int compare(Process a, Process b) {
				if (a.arrivalTime == b.arrivalTime)
					return a.burstTime - b.burstTime;
				else
					return a.arrivalTime - b.arrivalTime;
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
		double avgTurnaround;
		double avgContextChange;
		double throughput;
		
		double auxSumWait = 0;
		double auxSumResponse = 0;
		double auxSumTurnaround = 0;
		double auxSumContextChange = 0;
		
		for (Process p : finishedQueue) {
			auxSumWait += p.waitTime;
			auxSumResponse += p.responseTime;
			auxSumTurnaround += (p.getWaitTime() + p.getBurstTime());
			auxSumContextChange += p.getContextChanges();
			//System.out.println("Process " + p.pid + " waited for " + p.getResponseTime() + " Seconds");
		}
		
		avgWait = auxSumWait/finishedQueue.size();
		avgResponse = auxSumResponse/finishedQueue.size();
		avgTurnaround = auxSumTurnaround/finishedQueue.size();
		avgContextChange = auxSumContextChange/finishedQueue.size();
		
		throughput = (double)finishedQueue.size()/(double)currentTime();
		
		//System.out.println(finishedQueue.size());
		//System.out.println(currentTime());
		
		
		System.out.println("\n====================== STATISTICS: " + this.algorithmName + " ======================\n");
		System.out.println("Total processing time: " + timer + "s");
		System.out.println("Percentage of CPU usage: " + df.format((double)runningTime/timer));
		System.out.println("Average throughput: " + df.format(throughput));
		System.out.println("Average turnaround time " + avgTurnaround);
		System.out.println("Average wait time: " + avgWait);
		System.out.println("Average response time: " + avgResponse);
		System.out.println("Total number of context changes: " + auxSumContextChange);
		System.out.println("Average number of context changes: " + avgContextChange);
		System.out.println("Number of processes: " + finishedQueue.size());
		
	}
	
}
