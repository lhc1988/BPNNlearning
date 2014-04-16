package lab.cgcl.aliBigdata.BPNNlearning.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lab.cgcl.aliBigdata.BPNNlearning.OutPut;

public class FormatOutput {
	
	public static void fromCSV (String filepath , String outpath) throws IOException {
		InputStream is = new FileInputStream(filepath);
		BufferedReader input = new BufferedReader(new  InputStreamReader(is)); 
		
		OutPut out = new OutPut();
		
		//不要第一行的标题
		String line = null;
		line = input.readLine();
		
		//输出的行
		int outputsize = 0;
		//忽略的行
		int ignoresize = 0;
		
		int linenum = 1;
		FileWriter writer=new FileWriter( "e:\\000.csv" );
		writer.write(line + "\n");
		while((line = input.readLine()) != null) {  
			linenum ++;
		    String[] vs = line.split(",");
		    try {
		    	int sum = 0 ;
		    	for (int j = 2 ; j < vs.length -1 ; j ++) {
		    		sum += Integer.parseInt(vs[j]);
		    	}
//		    	if (sum == 0 ) {
//		    		writer.write(line + "\n");
//		    		continue;
//		    	}
		    	
		    	if ("1".equals(vs[vs.length-1]) ) {
//		    		if (sum == 0 ) {
//			    		writer.write(line + "\n");
//			    		ignoresize++;
//			    		continue;
//			    	}
		    		out.process(vs[0], vs[1]);
		    		outputsize++;
		    	}
		    } catch (Exception e){
		    	System.out.println("phrase error at line " + linenum);
		    }
		}  
		
		out.write(outpath);
		writer.close();
		input.close();
		System.out.println("ignore size : " + ignoresize);
		System.out.println("total line : " + linenum);
		System.out.println("output size :" + outputsize);
	}
	
	public static void main(String[] args) {
		try {
			fromCSV("e:\\csv\\submit\\15+16.csv" , "e:\\csv\\submit\\15+16.txt");
//			fromCSV(args[1] , args[2]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
