package scheduler;


import java.util.ArrayList;


public class SJF extends Scheduler{
	public SJF() {
		super();
	}
	
	public SJF(ArrayList<Process> processList) {
		super(processList);
		this.algorithmName = "SJF";
	}
	
	public void execute() {
		int t;
		Process currentProcess = new Process();
		
		// This is made just to guarantee that the processes will be fresh when
		// the execution of the schedulers begin		
		for (Process p : processList)
			p.reset();
		
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
			
			// SJF Criteria to choose a process: least burst time
			
			// If there is no process running and the ready queue is not empty we
			// have to start the execution of the process in the queue with least
			// burst time. If it is the first time the current process is executing,
			// we have to set its response time to the current time
			if (!running && !readyQueue.isEmpty()) {
				running = true;
				for (Process p : readyQueue)
					if (currentProcess.burstTime > p.burstTime)
						currentProcess = p;
				currentProcess.increaseContextChanges();
				
				if (currentProcess.getExecutionTime() == 0 ) {
					currentProcess.setResponseTime(this.timer - currentProcess.arrivalTime);
				}
				
			}
			
			// For every process in the ready queue that is not running, we have
			// tho increase their wait time by one
			for (Process p : readyQueue) {
				if (p.pid != currentProcess.pid)
					p.increaseWaitTime();
				else
					p.increaseExecutionTime();
			}
			
//			if (currentProcess.pid != -1) {
//				System.out.println("Tempo " + currentTime() + ": Processo " + currentProcess.pid + " executando");
//			} else {
//				System.out.println("Tempo " + currentTime() + ": Nenhum Processo executado");
//			}
			
			if (running)
				runningTime++;
			
			if (currentProcess.pid != -1 && currentProcess.getExecutionTime() <= currentProcess.getBurstTime()) {
				finishedQueue.add(currentProcess);
				readyQueue.remove(currentProcess);
				currentProcess.increaseContextChanges();
//				System.out.println("Tempo " + currentTime() + ": Processo " + currentProcess.pid + " Finalizado");
				currentProcess = new Process();
				running = false;
			}
			
		}
		printStatistics();
		
	}

}
