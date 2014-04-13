package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.FileWriter;
import java.io.IOException;

public class Write {
	
	private static final String filepath1= "e:\\111.txt";
	private static final String filepath2= "e:\\222.txt";
	
	
	public static synchronized void  write1 (String sb) throws IOException {
		FileWriter writer=new FileWriter(filepath1 ,true);
		writer.write(sb);
		writer.close();
	}
	
	public static synchronized void write2 (String sb) throws IOException {
		FileWriter writer=new FileWriter(filepath2 ,true);
		writer.write(sb);
		writer.close();
	}

}
