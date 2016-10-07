package scheduler;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		CSVReader reader = new CSVReader();
		ArrayList<Process> processList = reader.readFile("/home/ph/workspace/ProcessScheduler/processos.csv");
		
		SJF scheduler = new SJF(processList);
		scheduler.execute();
	}
}
