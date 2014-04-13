package lab.cgcl.aliBigdata.BPNNlearning.getVector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;
import lab.cgcl.aliBigdata.BPNNlearning.util.ParamAdapter;

public class GetLabel extends GetInfoFromDB {

	public GetLabel()  {
		super(0);
		// TODO Auto-generated constructor stub
	}

	public Object getRawData(Object param) {
		String sql = "select count(*) as click_times  from ali.user_data where type = 1 and user_id = ? and brand_id = ? "
				+ "and visit_datetime > '2014-7-15';";
		Map result = null;
		try {
//			result = dao.retrieve(sql);
			result = dao.preparedRetrieve(sql, (List)param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void processData(Object object) {
		if ( Integer.parseInt(((Map)object).get("click_times").toString() ) > 0 ) {
			((Map)object).put("click_times", 1);
		}

	}

	public double getData(Object param) {
		List list = ParamAdapter.convertPair2List((UserBrandPair)param);
		Object result = getRawData(list);
		processData(result);
		Map map = (Map)result;
		Iterator it = map.keySet().iterator();
//		System.out.println(it.next().toString());
		return Double.parseDouble(map.get(it.next().toString()).toString());
	}

}
