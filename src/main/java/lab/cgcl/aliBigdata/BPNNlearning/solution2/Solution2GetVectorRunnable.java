package lab.cgcl.aliBigdata.BPNNlearning.solution2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import lab.cgcl.aliBigdata.BPNNlearning.SQLContainer;
import lab.cgcl.aliBigdata.BPNNlearning.Write;
import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;

public class Solution2GetVectorRunnable implements Runnable  {
	private UserBrandPair ubpair ;
	private ApplicationContext appContext ;
	private int size;
	private Calendar can;
	private int id ;

	public UserBrandPair getUbpair() {
		return ubpair;
	}

	public void setUbpair(UserBrandPair ubpair) {
		this.ubpair = ubpair;
	}
	
	public Solution2GetVectorRunnable (UserBrandPair p ,int s , ApplicationContext app  ,Calendar c , int i) {
		ubpair = p;
		size =s ;
		appContext = app;
		this.can = c;
		this.id = i;
	}

	public void run() {
		double[] vector = new double[size];
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
		 
		for (int i = 0 ; i < size ; i ++) {
			IGetInfo getinfo = (IGetInfo)appContext.getBean("solution2Get");
			Map Pmap = new HashMap();
			Pmap.put("sql", SQLContainer.getTrainSqls().get(i));
			Pmap.put("ubp" , ubpair);
			Pmap.put("start_time" , sdf.format(can.getTime()));
			Pmap.put("end_time" , sdf.format(can.getTime()));
			vector[i] = getinfo.getData(Pmap);
			
		}
		ubpair.setVector(vector);
		
		Map Pmap = new HashMap();
		Pmap.put("ubp" , ubpair);
		Pmap.put("start_time" , sdf.format(can.getTime()));
		Pmap.put("end_time" , sdf.format(can.getTime()));
		
		String label_sql = "select count(*) as click_times from ali.user_data where user_id= ?  and brand_id = ?  and type = '0' and "
				+ "visit_datetime <= date_add(? , interval '1' month) and visit_datetime >= ?;";
		
		Pmap.put("sql", label_sql);
		
		IGetInfo getinfo = (IGetInfo)appContext.getBean("getLabel");
		double label = getinfo.getData(Pmap);
		
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
