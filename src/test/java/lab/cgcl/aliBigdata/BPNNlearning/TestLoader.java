package lab.cgcl.aliBigdata.BPNNlearning;

import lab.cgcl.aliBigdata.BPNNlearning.getVector.IGetInfo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 用于执行向量的所有分量
 * @author Administrator
 *
 */
public class TestLoader {
	
	public static void main(String[] args) {
		ApplicationContext appContext = new FileSystemXmlApplicationContext("properties/spring.xml");
		IGetInfo test = (IGetInfo)appContext.getBean("get00");
		double aa = test.getData(null);
		System.out.println(aa);
	}

}
