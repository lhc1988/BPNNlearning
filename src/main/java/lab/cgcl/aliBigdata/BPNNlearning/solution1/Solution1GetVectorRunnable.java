package lab.cgcl.aliBigdata.BPNNlearning.solution1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lab.cgcl.aliBigdata.BPNNlearning.BP;
import lab.cgcl.aliBigdata.BPNNlearning.SQLContainer;
import lab.cgcl.aliBigdata.BPNNlearning.Write;
import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;

public class Solution1GetVectorRunnable implements Runnable  {
	private UserBrandPair ubpair ;
	private ApplicationContext appContext ;
	private int size;
	private BP bp ;
	
	private int id ;

	public UserBrandPair getUbpair() {
		return ubpair;
	}

	public void setUbpair(UserBrandPair ubpair) {
		this.ubpair = ubpair;
	}
	
	public Solution1GetVectorRunnable (UserBrandPair p ,int s , ApplicationContext app  , BP b , int i) {
		ubpair = p;
		size =s ;
		appContext = app;
		this.bp = b;
		this.id = i;
	}

	public void run() {
		double[] vector = new double[size];
		 
		for (int i = 0 ; i < size ; i ++) {
			IGetInfo getinfo = (IGetInfo)appContext.getBean("solutionGet");
			Map Pmap = new HashMap();
			Pmap.put("sql", SQLContainer.getTrainSqls().get(i));
			Pmap.put("ubp" , ubpair);
			
			vector[i] = getinfo.getData(Pmap);
			
		}
		ubpair.setVector(vector);
		
		IGetInfo getinfo = (IGetInfo)appContext.getBean("getLabel");
		double label = getinfo.getData(ubpair);
		
		ubpair.setLabel(label);
//		if (label == 0) {
//			bp.train(ubpair.getVector(), new double[]{0.0 , 1.0});
//		} else {
//			bp.train(ubpair.getVector(), new double[]{1.0 , 0.0});
//		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(ubpair.getUser_id() + "," +ubpair.getBrand_id() + ",");
		sb.append(vector[0]);
		for (int i = 1 ; i < vector.length  ;  i++) {
			sb.append(",");
			sb.append(vector[i]);
		}
		
		
		sb.append("," + label);
		
		sb.append("\n");
	
		try {
			Write.write1(sb.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(id);
		
//			return vector;
		
	}
		
		

}
