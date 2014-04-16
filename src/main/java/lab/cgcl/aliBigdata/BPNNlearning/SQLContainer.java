package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SQLContainer {
	
	private static String filepath1 = "properties/train.sql";
	private static String filepath2 = "properties/valid.sql";
	private static List TrainSqls = new ArrayList();
	private static List ValidSqls = new ArrayList() ;
	
	public static String getFilepath1() {
		return filepath1;
	}

	public static void setFilepath1(String filepath1) {
		SQLContainer.filepath1 = filepath1;
	}

	public static String getFilepath2() {
		return filepath2;
	}

	public static void setFilepath2(String filepath2) {
		SQLContainer.filepath2 = filepath2;
	}

	public static void init () throws IOException {
		InputStream is = new FileInputStream(filepath1);
		BufferedReader input = new BufferedReader(new  InputStreamReader(is)); 
		
		TrainSqls = new ArrayList();
		String line = null;
		while((line = input.readLine()) != null) {  
			TrainSqls.add(line);
		}
		
		input.close();
		
		InputStream is2 = new FileInputStream(filepath2);
		BufferedReader input2 = new BufferedReader(new  InputStreamReader(is2)); 

		ValidSqls = new ArrayList();
		String line2 = null;
		int num2 = 0;
		while((line2 = input2.readLine()) != null) {  
			ValidSqls.add(line2);
		}
		
		input2.close();
	}

	public static List getTrainSqls() {
		return TrainSqls;
	}

	public static void setTrainSqls(List trainSqls) {
		TrainSqls = trainSqls;
	}

	public static List getValidSqls() {
		return ValidSqls;
	}

	public static void setValidSqls(List validSqls) {
		ValidSqls = validSqls;
	}




}
