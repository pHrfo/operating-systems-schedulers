package scheduler;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		CSVReader reader = new CSVReader();
		ArrayList<Process> processList = reader.readFile("/home/ph/workspace/ProcessScheduler/processos.csv");
		
		ArrayList<Scheduler> schedulers = new ArrayList<>();
		
		//FCFS scheduler = new FCFS(processList);
		//SJF scheduler = new SJF(processList);
		//SJFP scheduler = new SJFP(processList);
		//Priority scheduler = new Priority(processList);
		//PriorityP scheduler = new PriorityP(processList);
		//RoundRobin scheduler = new RoundRobin(processList, 2);
		
		//scheduler.execute();

		schedulers.add(new FCFS(processList));
		schedulers.add(new SJF(processList));
		schedulers.add(new SJFP(processList));
		schedulers.add(new Priority(processList));
		schedulers.add(new PriorityP(processList));
		schedulers.add(new RoundRobin(processList, 2));
		
		for (Scheduler scheduler : schedulers)
			scheduler.execute();
	}
}
