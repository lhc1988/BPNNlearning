package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.FileWriter;
import java.io.IOException;

public class Write {
	
	private static String filepath1= "e:\\111.txt";
	private static String filepath2= "e:\\222.txt";
	private static int i = 0;
	
	private static StringBuilder sb_buffer1 = new StringBuilder();
	private static StringBuilder sb_buffer2 = new StringBuilder();
	
	
	public static void setFilepath1(String filepath1) {
		Write.filepath1 = filepath1;
	}


	public static void setFilepath2(String filepath2) {
		Write.filepath2 = filepath2;
	}


	public static String getFilepath1() {
		return filepath1;
	}


	public static String getFilepath2() {
		return filepath2;
	}


	public static synchronized void  write1 (String str) throws IOException {
		sb_buffer1.append(str);
		if (++i >100 ) {
			FileWriter writer=new FileWriter(filepath1 ,true);
			writer.write(sb_buffer1.toString());
			writer.close();
			sb_buffer1 = new StringBuilder();
			i=0;
		}
	}
	
	
	public static synchronized void flush1() throws IOException {
		FileWriter writer=new FileWriter(filepath1 ,true);
		writer.write(sb_buffer1.toString());
		writer.close();
		sb_buffer1 = new StringBuilder();
	}
	
	public static synchronized void write2 (String str) throws IOException {
		sb_buffer2.append(str);
		if (++i >100 ) {
			FileWriter writer=new FileWriter(filepath2 ,true);
			writer.write(sb_buffer2.toString());
			writer.close();
			i=0;
			sb_buffer2 = new StringBuilder();
		}
	}
	
	public static synchronized void flush2() throws IOException {
		FileWriter writer=new FileWriter(filepath2 ,true);
		writer.write(sb_buffer2.toString());
		writer.close();
		sb_buffer2 = new StringBuilder();
	}

}
