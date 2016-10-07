package scheduler;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class FCFS extends Scheduler{

	public FCFS() {
		super();
	}
	
	public FCFS(ArrayList<Process> processList) {
		super(processList);
	}
	
	public void execute() {
		int t;
		Process currentProcess = new Process();		
		
		// At this point, the process list is sorted by the arrival time.
		// Now, we will implement the algorithm. It is going to repeat
		// until every process is in the finished queue
		
		while((!processList.isEmpty()) || (!readyQueue.isEmpty())) {
			// First, we tick the clock
			tick();
			
			// Here, we have to check whether there are processes that are able
			// to enter the ready queue
			t = 0;
			while(t <= currentTime() && !processList.isEmpty())  {
				t = processList.get(0).arrivalTime;
				if (t <= currentTime())
					readyQueue.add(processList.remove(0));
			}
			
			// If there is no process running and the ready queue is not empty
			// we have to start the execution of the first process in the queue.
			if (!running && !readyQueue.isEmpty()) {
				running = true;
				currentProcess = readyQueue.get(0);
			}
			
			// For every process in the ready queue that is not running, we have
			// tho increase their wait time by one
			for (Process p : readyQueue)
				if (p.pid != currentProcess.pid)
					p.increaseWaitTime();
			
			if (currentProcess.pid != -1) {
				System.out.println("Tempo " + currentTime() + ": Processo " + currentProcess.pid + " executando");
			} else {
				System.out.println("Tempo " + currentTime() + ": Nenhum Processo executado");
			}
			
			if (currentProcess.pid != -1 && currentProcess.getWaitTime() + currentProcess.getArrivalTime() + currentProcess.getBurstTime() <= currentTime()) {
				finishedQueue.add(readyQueue.remove(0));
				System.out.println("Tempo " + currentTime() + ": Processo " + currentProcess.pid + " Finalizado");
				currentProcess = new Process();
				running = false;
			}
			
		}
		printStatistics();
		
	}

	public void printStatistics() {
		DecimalFormat df = new DecimalFormat("#.####");
		
		double avgWait;
		//double avgResponse;
		double throughput;
		
		int auxSum = 0;
		
		for (Process p : finishedQueue) {
			auxSum += p.waitTime;
		}
		
		avgWait = auxSum/finishedQueue.size();
		
		throughput = (double)finishedQueue.size()/(double)currentTime();
		
		System.out.println(finishedQueue.size());
		System.out.println(currentTime());
		
		
		System.out.println("\n===================STATISTICS: FCFS===================\n");
		System.out.println("Number of processes: " + finishedQueue.size());
		System.out.println("Average throughput: " + df.format(throughput));
		System.out.println("Average wait time: " + avgWait);
		
		
	}

}
