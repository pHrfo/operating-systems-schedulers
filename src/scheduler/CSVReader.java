package scheduler;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
	
	protected String csvFile;
	protected BufferedReader br;
	protected String line;
	protected String csvSplitBy;
	
	public CSVReader() {
		this.csvFile = "";
		this.br = null;
		this.line = "";
		this.csvSplitBy = ",";
	}

	public String getCsvFile() {
		return csvFile;
	}

	public void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}

	public String getCsvSplitBy() {
		return csvSplitBy;
	}

	public void setCsvSplitBy(String csvSplitBy) {
		this.csvSplitBy = csvSplitBy;
	}

	public ArrayList<Process> readFile(String path) {
		setCsvFile(path);
		ArrayList<Process> output = new ArrayList<>();
		
		try {
			br = new BufferedReader(new FileReader(getCsvFile()));
			
			while ((line = br.readLine()) != null) {
				
				String[] stringValues = line.split(csvSplitBy);			
				
				Process p = new Process(stringValues);
				output.add(p);
			}
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		
		} catch (IOException e) {
		
			e.printStackTrace();
		
		} finally {
		
			if (br != null) {
			
				try { br.close(); }
				catch (IOException e) { e.printStackTrace(); }
				
			}
		}
		
		return output;
	}
	
}
