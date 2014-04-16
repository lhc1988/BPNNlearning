package lab.cgcl.aliBigdata.BPNNlearning;

import java.util.List;

import lab.cgcl.aliBigdata.BPNNlearning.dao.WrapedDao;
import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;

/**
 * 存放用于查询的user_id和brand_Id
 * @author Administrator
 *
 */
public class ParameterPool {
	private List<?> pool;
	
	private int used = 0;
	
	private WrapedDao dao ;
	
	public List<?> getPool() {
		return pool;
	}

	public void setPool(List<?> pool) {
		this.pool = pool;
	}

	public WrapedDao getDao() {
		return dao;
	}

	public void setDao(WrapedDao dao) {
		this.dao = dao;
	}

	public synchronized UserBrandPair getAPair () {
		if (used >= pool.size() )
			return null ;
		else
			return (UserBrandPair)pool.get(used++);
	}

	public void init() {
		String sql = "select distinct user_id , brand_id from ali.user_data;";
		init(sql);
	}
	
	public void init (String sql ) {
		try {
			System.out.println("init sql : " + sql);
			pool = dao.list(sql ,  UserBrandPair.class);
			used = 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void restart() {
		used = 0;
	}
	
	public void restart(String sql) {
		init(sql);
	}
	


}
