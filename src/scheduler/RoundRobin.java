package scheduler;

import java.util.ArrayList;

public class RoundRobin extends Scheduler {
	
	protected int quantum;
	protected int quantumTimer;
	protected int currentPosition;
	
	public RoundRobin() {
		super();
		this.quantum = 0;
	}
	
	public RoundRobin(ArrayList<Process> processList, int quantum) {
		super(processList);
		this.quantum = quantum;
		this.algorithmName = "Round Robin";
		this.currentPosition = 0;
		this.quantumTimer = 0;
	}
	
	public void increaseQuantumTimer() {
		this.quantumTimer++;
	}
	
	public void resetQuantumTimer() {
		this.quantumTimer = 0;
	}
	
	public int getCurrentQuantumTimer() {
		return this.quantumTimer;
	}
	
	public void increaseCurrentPosition() {
		currentPosition++;
		if (currentPosition >= readyQueue.size()) {
			currentPosition = 0;
		}
	}
	
	public int getCurrentPosition() {
		return currentPosition;
	}
	
	public void execute() {
		int t;
		Process aux = new Process();
		Process currentProcess = new Process();
		
		// This is made just to guarantee that the processes will be fresh when
		// the execution of the schedulers begin		
		for (Process p : processList)
			p.reset();
		
		// At this point, the process list is sorted by the arrival time.
		// Now, we will implement the algorithm. It is going to repeat
		// until every process is in the finished queue
		
		while((!processList.isEmpty()) || (!readyQueue.isEmpty())) {
			// First, we tick the clocks
			tick();
			increaseQuantumTimer();
			
			// Here, we have to check whether there are processes that are able
			// to enter the ready queue
			t = 0;
			while(t <= currentTime() && !processList.isEmpty())  {
				t = processList.get(0).arrivalTime;
				if (t <= currentTime()) {
					aux = processList.remove(0);
					readyQueue.add(aux);
					//System.out.println("Process " + aux.pid + " Entered the ready queue in time = " + currentTime());
				}
			}
			
			// Round Robin Criteria to choose a process: The queue order
			
			// If there is no process running and the ready queue is not empty
			// we have to start the execution of the first process in the queue.
			// If it is the first time the current process is executing, we have
			// to set its response time to the current time
			if (!running && !readyQueue.isEmpty()) {
				resetQuantumTimer();
				running = true;
				
				if (getCurrentPosition() >= readyQueue.size())
					increaseCurrentPosition();
				currentProcess = readyQueue.get(getCurrentPosition());
				currentProcess.increaseContextChanges();
				if (currentProcess.getExecutionTime() == 0 ) {
					currentProcess.setResponseTime(this.timer - currentProcess.arrivalTime);
				}
				increaseQuantumTimer();
			}
			
			if (this.quantumTimer > this.quantum) {
				increaseCurrentPosition();
				this.resetQuantumTimer();
				this.increaseQuantumTimer();
				currentProcess.increaseContextChanges();
				currentProcess = readyQueue.get(getCurrentPosition());
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
			
			if (currentProcess.pid != -1 && currentProcess.getExecutionTime() == currentProcess.getBurstTime()) {
				finishedQueue.add(readyQueue.remove(currentPosition));
//				System.out.println("Tempo " + currentTime() + ": Processo " + currentProcess.pid + " Finalizado");
				currentProcess.increaseContextChanges();
				currentProcess = new Process();
				running = false;
				resetQuantumTimer();
			}
			
		}
		printStatistics();
		
	}

}
