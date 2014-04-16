package lab.cgcl.aliBigdata.BPNNlearning.solution1;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.getVector.GetInfoFromDB;
import lab.cgcl.aliBigdata.BPNNlearning.util.ParamAdapter;

public class Get extends GetInfoFromDB {
	
	/**
	 * 标示用于第几个分量
	 */
	private final static int FUNC_ID = 0x00;
	
	protected String sql = "";
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Get() {
		super(FUNC_ID);
	}

	public Object getRawData(Object param) {
		Map result = null;
//		long begintime = System.currentTimeMillis();
		try {
			result = dao.preparedRetrieve(sql, (List)param);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		 long endtinme=System.currentTimeMillis();
//		 System.out.println(endtinme - begintime);
		return result;
	}

	public void processData(Object object) {
	}

	public double getData(Object param) {
		Map Pmap = (Map)param;
		setSql(Pmap.get("sql").toString());
		
		
//		long begintime = System.currentTimeMillis();
		List list = ParamAdapter.convertPair2List((UserBrandPair) Pmap.get("ubp"));
		Object result = getRawData(list);
		processData(result);
		
		Map map = (Map)result;
		Iterator it = map.keySet().iterator();
//		long endtinme=System.currentTimeMillis();
//		 System.out.println("in getData :" + (endtinme - begintime));
		return Double.parseDouble(map.get(it.next().toString()).toString());
	}


}