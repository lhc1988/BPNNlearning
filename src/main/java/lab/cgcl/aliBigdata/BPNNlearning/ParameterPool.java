package lab.cgcl.aliBigdata.BPNNlearning;

import java.util.ArrayList;
import java.util.List;

import lab.cgcl.aliBigdata.BPNNlearning.dao.WrapedDao;
import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;

/**
 * 存放用于查询的user_id和brand_Id
 * @author Administrator
 *
 */
public class ParameterPool {
	private static List<?> pool;
	
	private static int used = 0;
	
	private WrapedDao dao ;
	
	public List<?> getPool() {
		return pool;
	}

	public void setPool(List<?> pool) {
		ParameterPool.pool = pool;
	}

	public WrapedDao getDao() {
		return dao;
	}

	public void setDao(WrapedDao dao) {
		this.dao = dao;
	}

	public synchronized UserBrandPair getAPair () {
		return (UserBrandPair)pool.get(++used);
	}

	public void init() {
		String sql1 = "select distinct user_id , brand_id from ali.user_data;";
		try {
			pool = dao.list(sql1 ,  UserBrandPair.class);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}
