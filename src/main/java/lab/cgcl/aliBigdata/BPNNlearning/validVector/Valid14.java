package lab.cgcl.aliBigdata.BPNNlearning.validVector;

import lab.cgcl.aliBigdata.BPNNlearning.getVector.Get0;

public class Valid14 extends Get0{
	
	private final static int FUNC_ID = 0x80;
	
	public void initSql() {
		sql = "select count(*) as shoppingcart_times_july from ali.user_data where user_id= ?  and brand_id = ?  and type = '3' and visit_datetime<'2014-07-15' and visit_datetime>'2014-06-14';";
	}

}
