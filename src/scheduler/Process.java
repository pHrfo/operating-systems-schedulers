package scheduler;

public class Process {
	
	protected int pid;
	protected int arrivalTime;
	protected int burstTime;
	protected int priority;
	protected int waitTime;
	protected int responseTime;
	protected int executionTime;
	
	public Process() {
		this(0,-1,5000,0);
	}
	
	public Process(int arrivalTime, int pid, int burstTime, int priority) {
		
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
		this.waitTime = 0;
		this.responseTime = 0;
		this.executionTime = 0;
	
	}
	
	public Process(String[] line) {
		
		this(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]));

	}
	
	public String toString() {
		String s = "pid: " + pid + ", chegada: " + arrivalTime + ", burst: " + burstTime + ", priority: " + priority;
		return s;
	}
	
	public void increaseWaitTime() {
		this.waitTime++;
	}
	
	public int getWaitTime() {
		return waitTime;
	}
	
	public int getBurstTime() {
		return burstTime;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}
	
	public void increaseExecutionTime() {
		this.executionTime++;
	}
	
	public int getExecutionTime() {
		return this.executionTime;
	}
	
	public void reset() {
		this.executionTime = 0;
		this.waitTime = 0;
	}
}
