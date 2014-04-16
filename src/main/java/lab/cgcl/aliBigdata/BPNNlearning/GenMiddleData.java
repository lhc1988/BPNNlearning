package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.solution2.Solution2GetVectorRunnable;
import lab.cgcl.aliBigdata.BPNNlearning.solution2.Solution2ValidVectorRunnable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class GenMiddleData {
	
	private static ApplicationContext appContext = new FileSystemXmlApplicationContext("properties/spring.xml");;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public void generate(Calendar  can) {
		ParameterPool pool =  (ParameterPool)appContext.getBean("parameterpool");
		
		String start_time = sdf.format(can.getTime());
		
		can.add( Calendar.MONTH, 1);
		String end_time = sdf.format(can.getTime());
		
		pool.init("select distinct user_id , brand_id from ali.user_data where visit_datetime >="
				+ " '" + start_time + "' and visit_datetime <= '" +
				end_time + "';");
//		try {
//			SQLContainer.init();
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
		OutPut out = new OutPut();
		System.out.println("num\t: " + pool.getPool().size());
		
		UserBrandPair sb =  pool.getAPair();
		int s = 0;
		long begintime = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(16); 
		
		while (sb!= null) {
			exec.execute(new Solution2GetVectorRunnable(sb , SQLContainer.getTrainSqls().size() ,
					appContext , can , ++s));
			sb =  pool.getAPair();
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
		
		
		
		try {
			out.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(sb.getLabel());
		
		long endtinme=System.currentTimeMillis();
		 System.out.println("time elapsed : " + (endtinme - begintime)/1000 + "s");
	}
	
	public void generateTestSet (Calendar  can) {
		//valid 
		
		ParameterPool pool= (ParameterPool)appContext.getBean("parameterpool");
		
		String start_time = sdf.format(can.getTime());
		
		can.add( Calendar.MONTH, 1);
		String end_time = sdf.format(can.getTime());
		
		pool.init("select distinct user_id , brand_id from ali.user_data where visit_datetime >="
				+ " '" + start_time + "' and visit_datetime <= '" +
				end_time + "';");
		
		UserBrandPair sb =  pool.getAPair();
		int s = 0;
		OutPut out = new OutPut();
		sb =  pool.getAPair();
		ExecutorService exec1 = Executors.newFixedThreadPool(20); 
		while (sb!= null) {
			
//					exec1.execute(new ValidVectorRunnable(sb ,VECTOR_SIZE , appContext , bp , ++s , out));
			exec1.execute(new Solution2ValidVectorRunnable(sb ,SQLContainer.getTrainSqls().size() ,
					appContext , can , ++s , out));
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
	}
	
	public static void main(String[] args) {
		SQLContainer.setFilepath1("properties/solution2/train.sql");
		try {
			SQLContainer.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar  can =  Calendar.getInstance();
		can.set(2014, 3, 15);
		Write.setFilepath1("e:\\csv\\416\\train1.txt");
		
		
		System.out.println(sdf.format(can.getTime()));
		GenMiddleData gen = new GenMiddleData();
		gen.generate(can);
		System.out.println("first time;");
		
		Write.setFilepath1("e:\\csv\\416\\train2.txt");
		can.add(Calendar.MONTH, 1);
		gen.generate(can);
		System.out.println("second time;");
		
		Write.setFilepath1("e:\\csv\\416\\train3.txt");
		gen.generate(can);
		System.out.println("third time;");
		
		
		Write.setFilepath2("e:\\csv\\416\\testset.txt");
		gen.generateTestSet(can);
		
		System.out.println("program finished.");
		
	}

}
