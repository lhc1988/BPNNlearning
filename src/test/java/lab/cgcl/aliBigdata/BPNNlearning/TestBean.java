package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestBean {
	public static void main( String[] args )
    {
        ApplicationContext appContext = new FileSystemXmlApplicationContext("properties/spring.xml");
        Test test = (Test)appContext.getBean("test");
        try {
			test.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
