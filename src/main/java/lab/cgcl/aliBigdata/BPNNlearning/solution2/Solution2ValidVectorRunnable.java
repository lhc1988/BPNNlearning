package lab.cgcl.aliBigdata.BPNNlearning.solution2;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import lab.cgcl.aliBigdata.BPNNlearning.OutPut;
import lab.cgcl.aliBigdata.BPNNlearning.SQLContainer;
import lab.cgcl.aliBigdata.BPNNlearning.Write;
import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;

public class Solution2ValidVectorRunnable implements Runnable  {
	private UserBrandPair ubpair ;
	private ApplicationContext appContext ;
	private int size;
	private Calendar can;
	private OutPut output;
	
	private int id ;

	public UserBrandPair getUbpair() {
		return ubpair;
	}

	public void setUbpair(UserBrandPair ubpair) {
		this.ubpair = ubpair;
	}
	
	public Solution2ValidVectorRunnable (UserBrandPair p ,int s , ApplicationContext app  , Calendar c , int i , OutPut o) {
		this.ubpair = p;
		this.size =s ;
		this.appContext = app;
		this.can = c;
		this.id = i;
		this.output = o;
	}

	public void run() {
		
		validVector( size , ubpair);
		
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
