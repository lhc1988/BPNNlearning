package lab.cgcl.aliBigdata.BPNNlearning.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;


public class WrapedDao extends SimpleDao{
	
	public <T> ArrayList<T> list (String sql , Class<T> clazz) throws Exception{
		Connection con = dataSource.getConnection();
		Statement stmt =  con.createStatement();
		ArrayList<T> rlist = new ArrayList<T>();
		try {
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				//Map<String ,String > res = new HashMap<String , String>();
				T result = clazz.newInstance();
				for (int i = 1 ; i  <= rset.getMetaData().getColumnCount() ; i ++) {
					BeanUtils.setProperty(result, rset.getMetaData().getColumnName(i),
							rset.getString(i));
					//res.put(rset.getMetaData().getColumnName(i) , rset.getString(i) );
				}
				rlist.add(result);
			}
		}finally {
			con.close();
		}
		return rlist;
	}


}
