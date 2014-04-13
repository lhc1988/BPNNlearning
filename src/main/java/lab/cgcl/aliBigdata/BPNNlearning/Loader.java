package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Loader {
	
	private static final int VECTOR_SIZE = 17;
	
	private static ParameterPool pool;
	
	private static ApplicationContext appContext ;
	
	public static void main(String[] args) {
		appContext = new FileSystemXmlApplicationContext("properties/spring.xml");
		pool = (ParameterPool)appContext.getBean("parameterpool");
		pool.init();
		
		System.out.println("num\t: " + pool.getPool().size());
		
		//build BP
		BP bp = ((BPFactory)appContext.getBean("BPfactory")).build();
		System.out.println("bp init done.");
		
		UserBrandPair sb =  pool.getAPair();
		int s = 0;
		long begintime = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(20); 
		
		while (sb!= null) {
//			genVector( VECTOR_SIZE , sb);
			exec.execute(new GetVectorRunnable(sb ,VECTOR_SIZE , appContext , bp , ++s));
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
		System.out.println("train complete.");
		
		//valid 
		pool.restart();
		OutPut out = new OutPut();
		s=0;
		sb =  pool.getAPair();
		ExecutorService exec1 = Executors.newFixedThreadPool(20); 
		while (sb!= null) {
			
			exec1.execute(new ValidVectorRunnable(sb ,VECTOR_SIZE , appContext , bp , ++s , out));
			sb =  pool.getAPair();
			
//			double max = -Integer.MIN_VALUE;  
//            int idx = -1;  
//            double[] result = bp.test(validVector( VECTOR_SIZE , sb));
//            for (int i = 0; i != result.length; i++) {  
//                if (result[i] > max) {  
//                    max = result[i];  
//                    idx = i;  
//                }  
//            }
//			if (idx == 0 ) {
//				out.process(sb);
//			}
//			sb =  pool.getAPair();
//			
//			System.out.println("v" + (s));
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
		 System.out.println("time elapsed : " + (endtinme - begintime));
		
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
//		ubpair.setVector(vector);
		
//		IGetInfo getinfo = (IGetInfo)appContext.getBean("getLabel");
//		double label = getinfo.getData(ubpair);
//		
//		ubpair.setLabel(label);
		
		return vector;
	}
	
}
