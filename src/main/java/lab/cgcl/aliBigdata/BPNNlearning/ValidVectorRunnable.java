package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.FileWriter;
import java.io.IOException;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;

public class ValidVectorRunnable implements Runnable  {
	private UserBrandPair ubpair ;
	private ApplicationContext appContext ;
	private int size;
	private BP bp ;
	private OutPut output;
	
	private int id ;

	public UserBrandPair getUbpair() {
		return ubpair;
	}

	public void setUbpair(UserBrandPair ubpair) {
		this.ubpair = ubpair;
	}
	
	public ValidVectorRunnable (UserBrandPair p ,int s , ApplicationContext app  , BP b , int i , OutPut o) {
		this.ubpair = p;
		this.size =s ;
		this.appContext = app;
		this.bp = b;
		this.id = i;
		this.output = o;
	}

	public void run() {
		double max = -Integer.MIN_VALUE;  
        int idx = -1;  
        double[] result = bp.test(validVector( size , ubpair));
        for (int i = 0; i != result.length; i++) {  
            if (result[i] > max) {  
                max = result[i];  
                idx = i;  
            }  
        }
		if (idx == 0 ) {
			output.process(ubpair);
		}
		
		
		
		
		System.out.println("v" + (id));
		
	}
	
	public double[] validVector(int size , UserBrandPair ubpair) {
		double[] vector = new double[size];
		 
		for (int i = 0 ; i < size ; i ++) {
			IGetInfo getinfo = (IGetInfo)appContext.getBean("valid" + i);
			vector[i] = getinfo.getData(ubpair);
			
		}
//		ubpair.setVector(vector);
		
//		IGetInfo getinfo = (IGetInfo)appContext.getBean("getLabel");
//		double label = getinfo.getData(ubpair);
//		
//		ubpair.setLabel(label);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(vector[0]);
		for (int i = 1 ; i < vector.length  ;  i++) {
			sb.append(",");
			sb.append(vector[i]);
			
		}
		sb.append("\n\r");
		
		try {
			FileWriter writer=new FileWriter("e:\\222.txt" ,true);
			writer.write(sb.toString());
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vector;
	}
		
		

}
