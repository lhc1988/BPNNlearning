package lab.cgcl.aliBigdata.BPNNlearning.getVector;

/**
 * 处理向量每一位的输入参数的接口
 * @author Administrator
 *
 */
public interface IGetInfo {
	
	/**
	 * 从数据库中查询到原始数据
	 * @param param
	 * @return
	 */
	public Object getRawData(Object param);
	
	/**
	 * 处理数据
	 * @param object
	 */
	public void processData(Object object) ; 
	
	/**
	 * 取数据
	 * @return 该向量对应的值
	 */
	public double getData(Object param);
	

}
