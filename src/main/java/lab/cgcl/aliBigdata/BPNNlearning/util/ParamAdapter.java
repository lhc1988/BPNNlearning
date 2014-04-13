package lab.cgcl.aliBigdata.BPNNlearning.util;

import java.util.ArrayList;
import java.util.List;

import lab.cgcl.aliBigdata.BPNNlearning.domain.UserBrandPair;

public class ParamAdapter {
	
	/**
	 * 默认第一个参数是user_id  , 第二个参数是 brand_id
	 * @param pair
	 * @return
	 */
	public static List convertPair2List(UserBrandPair pair) {
		List list = new ArrayList () ;
		list.add(pair.getUser_id());
		list.add(pair.getBrand_id());
		return list;
	}
}
