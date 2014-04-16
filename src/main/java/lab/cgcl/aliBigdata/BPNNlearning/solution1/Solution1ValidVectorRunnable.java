package lab.cgcl.aliBigdata.BPNNlearning.solution1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lab.cgcl.aliBigdata.BPNNlearning.BP;
import lab.cgcl.aliBigdata.BPNNlearning.OutPut;
import lab.cgcl.aliBigdata.BPNNlearning.SQLContainer;
import lab.cgcl.aliBigdata.BPNNlearning.Write;
import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;

public class Solution1ValidVectorRunnable implements Runnable  {
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
	
	public Solution1ValidVectorRunnable (UserBrandPair p ,int s , ApplicationContext app  , BP b , int i , OutPut o) {
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
			IGetInfo getinfo = (IGetInfo)appContext.getBean("solutionGet");
			
			Map Pmap = new HashMap();
			Pmap.put("sql", SQLContainer.getValidSqls().get(i));
			Pmap.put("ubp" , ubpair);
			
			vector[i] = getinfo.getData(Pmap);
			
		}
//		ubpair.setVector(vector);
		
//		IGetInfo getinfo = (IGetInfo)appContext.getBean("getLabel");
//		double label = getinfo.getData(ubpair);
//		
//		ubpair.setLabel(label);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(ubpair.getUser_id() + "," +ubpair.getBrand_id() + ",");
		sb.append(vector[0]);
		for (int i = 1 ; i < vector.length  ;  i++) {
			sb.append(",");
			sb.append(vector[i]);
			
		}
		sb.append("\n");
		
		try {
			Write.write2(sb.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vector;
	}
		
		

}
