package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.FileWriter;
import java.io.IOException;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;

public class GetVectorRunnable implements Runnable  {
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
	
	public GetVectorRunnable (UserBrandPair p ,int s , ApplicationContext app  , BP b , int i) {
		ubpair = p;
		size =s ;
		appContext = app;
		this.bp = b;
		this.id = i;
	}

	public void run() {
		double[] vector = new double[size];
		 
		for (int i = 0 ; i < size ; i ++) {
			IGetInfo getinfo = (IGetInfo)appContext.getBean("get" + i);
			vector[i] = getinfo.getData(ubpair);
			
		}
		ubpair.setVector(vector);
		
		IGetInfo getinfo = (IGetInfo)appContext.getBean("getLabel");
		double label = getinfo.getData(ubpair);
		
		ubpair.setLabel(label);
		if (label == 0) {
			bp.train(ubpair.getVector(), new double[]{0.0 , 1.0});
		} else {
			bp.train(ubpair.getVector(), new double[]{1.0 , 0.0});
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(vector[0]);
		for (int i = 1 ; i < vector.length  ;  i++) {
			sb.append(",");
			sb.append(vector[i]);
		}
		
		
		sb.append("," + label);
		
		sb.append("\n\r");
	
		try {
			FileWriter writer=new FileWriter("e:\\111.txt" ,true);
			writer.write(sb.toString());
			
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(id);
		
//			return vector;
		
	}
		
		

}
