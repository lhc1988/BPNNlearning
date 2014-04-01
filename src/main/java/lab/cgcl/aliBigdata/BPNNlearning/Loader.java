package lab.cgcl.aliBigdata.BPNNlearning;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Loader {
	
	private static final int VECTORSIZE = 0x0;
	
	private static ParameterPool pool;
	
	private static ApplicationContext appContext ;
	
	public static void main(String[] args) {
		appContext = new FileSystemXmlApplicationContext("properties/spring.xml");
		pool = (ParameterPool)appContext.getBean("parameterpool");
		pool.init();
		
		System.out.println("num\t: " + pool.getPool().size());
	}
	
	public double[] genVector(int size) {
		double[] vector = new double[size];
		UserBrandPair ubpair = pool.getAPair();
		for (int i = 0 ; i < size ; i ++) {
			IGetInfo getinfo = (IGetInfo)appContext.getBean("get" + i);
			vector[i] = getinfo.getData(ubpair);
		}
		ubpair.setVector(vector);
		return vector;
	}
	

}
