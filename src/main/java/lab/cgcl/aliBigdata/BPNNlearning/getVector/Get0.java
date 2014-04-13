package lab.cgcl.aliBigdata.BPNNlearning.getVector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.util.ParamAdapter;

/**
 * 这是一个示例
 * 
 * @author Administrator
 *
 */
public class Get0 extends GetInfoFromDB {
	
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
	
	public void initSql() {
		sql = "select count(*) as click_times_total from ali.user_data where user_id= ?  and brand_id = ?  and type = '0' and visit_datetime<'2014-07-15';";
	}

	public Get0() {
		super(FUNC_ID);
		initSql();
	}

	public Object getRawData(Object param) {
//		Map par = (Map)param;
//		String user_id= par.get("user_id").toString();
//		String brand_id = par.get("brand_id").toString();
//		
//		String sql = "select count(*) as click_times  from ali.user_data where type = 0 and user_id = ? and brand_id = ? ;";
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
		// TODO Auto-generated method stub
//		Map map = (Map)object;
//		String aa = map.get("click_times").toString();
//		aa = aa.substring(0 , 4);
//		map.put("now", aa);
	}

	public double getData(Object param) {
//		long begintime = System.currentTimeMillis();
		List list = ParamAdapter.convertPair2List((UserBrandPair)param);
		Object result = getRawData(list);
		processData(result);
		
		Map map = (Map)result;
		Iterator it = map.keySet().iterator();
//		long endtinme=System.currentTimeMillis();
//		 System.out.println("in getData :" + (endtinme - begintime));
		return Double.parseDouble(map.get(it.next().toString()).toString());
//		return Double.parseDouble( ((Map)result).get("click_times").toString());
	}


}
