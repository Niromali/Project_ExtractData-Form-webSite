package p04_ExtractData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFile {
	public CSVFile() {
		// TODO Auto-generated constructor stub
		
	}

	public void WriteCSV(String value) {
		// TODO Auto-generated method stub
		File f = new File("Ressources\\ville.csv");
		try {
			FileWriter fw =  new FileWriter(f);
			fw.write(value);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
