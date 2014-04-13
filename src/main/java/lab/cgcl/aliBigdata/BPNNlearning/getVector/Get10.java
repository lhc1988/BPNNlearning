package lab.cgcl.aliBigdata.BPNNlearning.getVector;

public class Get10 extends Get0{ 
	private final static int FUNC_ID = 0x01;
	
	public void initSql() {
		sql = "select count(*) as collect_times_may from ali.user_data where user_id= ?  and brand_id = ?  and type = '2' and visit_datetime<'2014-05-15' and visit_datetime>'2014-04-14';";
	}
}
