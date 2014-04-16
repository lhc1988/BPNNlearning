package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;
import lab.cgcl.aliBigdata.BPNNlearning.solution1.Solution1GetVectorRunnable;
import lab.cgcl.aliBigdata.BPNNlearning.solution1.Solution1ValidVectorRunnable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Loader {
	
//	private static final int VECTOR_SIZE = 17;
	
	private static ParameterPool pool;
	
	private static ApplicationContext appContext ;
	
	public static void main(String[] args) {
		appContext = new FileSystemXmlApplicationContext("properties/spring.xml");
		pool = (ParameterPool)appContext.getBean("parameterpool");
		pool.init();
		try {
			SQLContainer.init();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		System.out.println("num\t: " + pool.getPool().size());
		
		//build BP
//		BP bp = ((BPFactory)appContext.getBean("BPfactory")).build();
		
		BP bp = new BP(SQLContainer.getTrainSqls().size(), 15 , 2);
		System.out.println("bp init done.");
		
		UserBrandPair sb =  pool.getAPair();
		int s = 0;
		long begintime = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(20); 
		
		while (sb!= null) {
//			genVector( VECTOR_SIZE , sb);
//			exec.execute(new GetVectorRunnable(sb ,VECTOR_SIZE , appContext , bp , ++s));
			
			exec.execute(new Solution1GetVectorRunnable(sb , SQLContainer.getTrainSqls().size() ,
					appContext , bp , ++s));
			sb =  pool.getAPair();
//			bp.train(sb.getVector(), new double[]{sb.getLabel()});
		}
		exec.shutdown();
		
		while (!exec.isTerminated()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//flush 1
		try {
			Write.flush1();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("train complete.");
		
		//valid 
		pool.restart();
		OutPut out = new OutPut();
		s=0;
		sb =  pool.getAPair();
		ExecutorService exec1 = Executors.newFixedThreadPool(20); 
		while (sb!= null) {
			
//			exec1.execute(new ValidVectorRunnable(sb ,VECTOR_SIZE , appContext , bp , ++s , out));
			exec1.execute(new Solution1ValidVectorRunnable(sb ,SQLContainer.getValidSqls().size() ,
					appContext , bp , ++s , out));
			sb =  pool.getAPair();
			
		}
		exec1.shutdown();
		
		while (!exec1.isTerminated()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//flush 2
		try {
			Write.flush2();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("train complete.");
		
		try {
			out.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(sb.getLabel());
		System.out.println("program finished.");
		long endtinme=System.currentTimeMillis();
		 System.out.println("time elapsed : " + (endtinme - begintime)/1000);
		
	}
	
	
	
	public static double[] genVector(int size , UserBrandPair ubpair) {
		double[] vector = new double[size];
		 
		for (int i = 0 ; i < size ; i ++) {
			IGetInfo getinfo = (IGetInfo)appContext.getBean("get" + i);
			vector[i] = getinfo.getData(ubpair);
			
		}
		ubpair.setVector(vector);
		
		IGetInfo getinfo = (IGetInfo)appContext.getBean("getLabel");
		double label = getinfo.getData(ubpair);
		
		ubpair.setLabel(label);
		
		return vector;
	}
	
	public static double[] validVector(int size , UserBrandPair ubpair) {
		double[] vector = new double[size];
		 
		for (int i = 0 ; i < size ; i ++) {
			IGetInfo getinfo = (IGetInfo)appContext.getBean("valid" + i);
			vector[i] = getinfo.getData(ubpair);
			
		}
		
		return vector;
	}
	
}
