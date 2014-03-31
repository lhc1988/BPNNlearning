package lab.cgcl.aliBigdata.BPNNlearning.getVector;

import java.util.Map;

/**
 * 这是一个示例
 * 
 * @author Administrator
 *
 */
public class Get00 extends GetInfoFromDB {
	
	/**
	 * 标示用于第几个分量
	 */
	private final static int FUNC_ID = 0x00;

	public Get00() {
		super(FUNC_ID);
	}

	public Object getRawData(Object param) {
		String sql = "select ";
		Map result = null;
		try {
			result = dao.retrieve(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void processData(Object object) {
		// TODO Auto-generated method stub

	}

	public double getData() {
		Object result = getRawData(null);
		processData(result);
		return Double.parseDouble(result.toString());
	}


}
