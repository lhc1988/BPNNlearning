package lab.cgcl.aliBigdata.BPNNlearning;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;

public class OutPut {
//	private File file = new File("e:\\output.txt");
	
	private Map<String , String> user = new ConcurrentHashMap();
	
	public void process (UserBrandPair ubp) {
		if (user.containsKey(ubp.getUser_id())) {
			String src = user.get(ubp.getUser_id());
			src += " , " + ubp.getBrand_id();
			user.put(ubp.getUser_id(), src);
		}
		else {
			user.put(ubp.getUser_id(), ubp.getBrand_id());
		}
	}
	
	public void process (String user_id , String brand_id) {
		if (user.containsKey(user_id)) {
			String src = user.get(user_id);
			src += " , " + brand_id;
			user.put(user_id, src);
		}
		else {
			user.put(user_id, brand_id);
		}
	}
	
	public void write (String outpath) throws IOException {
		FileWriter writer=new FileWriter( outpath ,true);
		System.out.println("usersize:" + user.size());
		Iterator it = user.entrySet().iterator();
		while(it.hasNext()) {
			Entry entry = (Entry)it.next();
//			System.out.println(entry.getKey() + "\t" + entry.getValue() + "\n");
			writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
		}
		writer.close();
	}
	
	public void write () throws IOException {
		write("e:\\output.txt");
	}
	
	public static void main(String[] args) throws IOException {
		OutPut o = new OutPut();
		o.user = new HashMap () ;
		o.user.put("123", "1232131");
		o.user.put("1233", "1232131");
		o.user.put("1243", "1232131");
		o.write();
		
	}

}
