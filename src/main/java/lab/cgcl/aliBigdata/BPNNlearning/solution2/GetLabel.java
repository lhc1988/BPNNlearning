package lab.cgcl.aliBigdata.BPNNlearning.solution2;

import java.util.Map;

import lab.cgcl.aliBigdata.BPNNlearning.solution2.Get;

public class GetLabel extends Get{ 
	private final static int FUNC_ID = 0x01;
	
	public void initSql() {
		sql = "select count(*) as click_times from ali.user_data where user_id= ?  and brand_id = ?  and type = '0' and "
				+ "visit_datetime <= ? and visit_datetime >= date_sub(? , interval '1' month);";
	}
	
	public void processData(Object object) {
		if ( Integer.parseInt(((Map)object).get("click_times").toString() ) > 0 ) {
			((Map)object).put("click_times", 1);
		}

	}
}
