package lab.cgcl.aliBigdata.BPNNlearning.getVector;

public class Get16 extends Get0{ 
	private final static int FUNC_ID = 0x01;
	
	public void initSql() {
		sql = "select count(*) as click_times_weekago1 from ali.user_data where user_id= ?  and brand_id = ?  and type = '0' and visit_datetime<'2014-07-15' and visit_datetime>'2014-07-07';";
	}
}
